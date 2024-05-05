package app.netlify.nmhillusion.eciapp.service_impl.politics_ruler;

import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.model.politics_ruler.IndexEntity;
import app.netlify.nmhillusion.eciapp.model.politics_ruler.PoliticianEntity;
import app.netlify.nmhillusion.eciapp.service.PoliticsRulersService;
import app.netlify.nmhillusion.neon_di.annotation.Neon;
import tech.nmhillusion.n2mix.exception.MissingDataException;
import tech.nmhillusion.n2mix.helper.YamlReader;
import tech.nmhillusion.n2mix.helper.firebase.FirebaseWrapper;
import tech.nmhillusion.n2mix.helper.http.HttpHelper;
import tech.nmhillusion.n2mix.helper.http.RequestHttpBuilder;
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.BasicExcelDataModel;
import tech.nmhillusion.n2mix.type.ChainMap;
import tech.nmhillusion.n2mix.type.function.ThrowableVoidFunction;
import tech.nmhillusion.n2mix.util.IOStreamUtil;
import tech.nmhillusion.n2mix.util.RegexUtil;
import tech.nmhillusion.n2mix.util.StringUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2022-11-17
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class PoliticsRulersServiceImpl implements PoliticsRulersService {

    private static final String MAIN_RULERS_PAGE_URL = "https://rulers.org/";
    private static final int MIN_INTERVAL__TICK__TIME_IN_MILLIS = 10_000;
    private static final Charset RULERS_CHARSET = StandardCharsets.ISO_8859_1;

    private final HttpHelper httpHelper = new HttpHelper();
    private final MatchParser matchParser = new MatchParser();

    private final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
    private final DateTimeFormatter exportDataDateTimeFormatter;
    private boolean isTestingData = false;
    private boolean isTestingOnePage = false;
    private YamlReader yamlReader;

    public PoliticsRulersServiceImpl() {
        this.exportDataDateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(getConfig("export.excel.date-format"))
                .toFormatter();

        this.isTestingData = Boolean.parseBoolean(getConfig("testing.data"));
        this.isTestingOnePage = Boolean.parseBoolean(getConfig("testing.onlyFetchOnePage"));
    }

    private synchronized String getConfig(String key) {
        try {
            if (null == yamlReader) {
                try (InputStream is = getClass().getClassLoader().getResourceAsStream("service-config/politics-rulers.yml")) {
                    yamlReader = new YamlReader(is);
                }
            }

            return yamlReader.getProperty(key, String.class, null);
        } catch (Exception ex) {
            getLogger(this).error(ex);
            return "";
        }
    }

    @Override
    public void service(String outputDataPath, ThrowableVoidFunction<StatusModel> onUpdateProgress) throws Throwable {
        getLogger(this).info("Running for fetching politicians from Rulers");
        onUpdateProgress.throwableVoidApply(new StatusModel()
                .setStatusName("starting")
                .setStatusDetail("start crawling...")
        );

        final List<IndexEntity> indexLinks = parseHomePage();
        getLogger(this).info("parser index links: " + indexLinks);
        final int indexLinksSize = indexLinks.size();
        onUpdateProgress.throwableVoidApply(new StatusModel()
                .setStatusName("loaded homepage")
                .setStatusDetail("obtains homepage with total indexes: " + indexLinksSize)
        );

        if (!indexLinks.isEmpty()) {
            for (int linkItemIdx = 0; linkItemIdx < indexLinksSize; ++linkItemIdx) {
                final IndexEntity indexLinkItem = indexLinks.get(linkItemIdx);

                final long startTime = System.currentTimeMillis();
                onUpdateProgress.throwableVoidApply(new StatusModel()
                        .setStatusName("loading - " + indexLinkItem.getTitle()
                                + " (%d/%d)".formatted(linkItemIdx + 1, indexLinksSize)
                        )
                        .setStatusDetail(">> start loading: " + indexLinkItem.getTitle() + " : " + indexLinkItem.getHref()
                                + " (%d/%d)".formatted(linkItemIdx + 1, indexLinksSize)
                        )
                );

                final List<PoliticianEntity> politicianEntities = fetchCharacterPage(indexLinkItem);
                final int politicianListSize = politicianEntities.size();
                getLogger(this).info("politician list -> " + politicianListSize);
                onUpdateProgress.throwableVoidApply(new StatusModel()
                        .setStatusName("finished - " + indexLinkItem.getTitle()
                                + " (%d/%d)".formatted(linkItemIdx + 1, indexLinksSize)
                        )
                        .setStatusDetail("<< end loading: " + indexLinkItem.getTitle() + " : " + indexLinkItem.getHref() + " -> size: " + politicianListSize + "; waiting for next step..."
                                + " (%d/%d)".formatted(linkItemIdx + 1, indexLinksSize)
                        )
                );

                __saveToExcelFile(outputDataPath,
                        new ChainMap<String, List<PoliticianEntity>>()
                                .chainPut(indexLinkItem.getTitle(), politicianEntities)
                );

                if (isTestingOnePage) {
                    break; /// Mark: TESTING
                }

                System.gc();
                while (MIN_INTERVAL__TICK__TIME_IN_MILLIS > System.currentTimeMillis() - startTime) ;
            }
        }

        onUpdateProgress.throwableVoidApply(new StatusModel()
                .setStatusName("finished")
                .setStatusDetail("finished crawling.")
        );
    }

    private void __saveToExcelFile(String outputDataPath, Map<String, List<PoliticianEntity>> politicianData) throws IOException {
        final Map<String, byte[]> excelData = exportToExcel(politicianData);

        for (String chainName : excelData.keySet()) {
            try (OutputStream os = new FileOutputStream(Paths.get(outputDataPath, chainName + ".pep.xlsx").toFile())) {
                os.write(excelData.get(chainName));
                os.flush();
            }
        }
    }


    private String formatDateWhenExportToExcel(LocalDate dataDate) {
        try {
            if (null == dataDate) {
                return StringUtil.EMPTY;
            }
            return StringUtil.trimWithNull(
                    exportDataDateTimeFormatter.format(dataDate)
            );
        } catch (Exception ex) {
            getLogger(this).error(ex);
            return StringUtil.EMPTY;
        }
    }

    private List<String> buildExcelDataFromPolitician(PoliticianEntity politician) {
        return Arrays.asList(
                politician.getOriginalParagraph(),
                politician.getFullName(),
                politician.getPrimaryName(),
                politician.getSecondaryName(),
                formatDateWhenExportToExcel(politician.getDateOfBirth()),
                politician.getPlaceOfBirth(),
                formatDateWhenExportToExcel(politician.getDateOfDeath()),
                politician.getPlaceOfDeath(),
                politician.getPosition(),
                politician.getNote()
        );
    }

    private Map<String, byte[]> exportToExcel(Map<String, List<PoliticianEntity>> politicianData) throws IOException {
        final Map<String, byte[]> exportData = new HashMap<>();
        politicianData.forEach((key, data) -> {
            try {
                final byte[] itemExcelData = new ExcelWriteHelper()
                        .addSheetData(new BasicExcelDataModel()
                                .setSheetName(key)
                                .setHeaders(Collections.singletonList(Arrays.asList("origin", "fullName", "primaryName", "secondaryName", "dateOfBirth", "placeOfBirth", "dateOfDeath", "placeOfDeath", "role", "note")))
                                .setBodyData(data.stream().map(this::buildExcelDataFromPolitician).collect(Collectors.toList()))
                        ).build();

                exportData.put(key, itemExcelData);
            } catch (Exception ex) {
                getLogger(this).error(ex);
                try {
                    exportData.put(key, new ExcelWriteHelper().addSheetData(new BasicExcelDataModel()
                                    .setSheetName(key)
                                    .setHeaders(List.of(List.of("Error")))
                                    .setBodyData(List.of(Collections.singletonList(ex.getMessage())))
                            ).build()
                    );
                } catch (IOException | MissingDataException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return exportData;
    }

    private List<IndexEntity> parseHomePage() throws Exception {
        final List<IndexEntity> indexLinks = new ArrayList<>();

        /// Mark: TESTING (start)
        String pageContent = "";
        if (!isTestingData) {
            pageContent = new String(httpHelper.get(
                    new RequestHttpBuilder()
                            .setUrl(MAIN_RULERS_PAGE_URL)
            ), RULERS_CHARSET);
        } else {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-data/politics-rulers/home-page.html")) {
                getLogger(this).debug("loaded stream --> " + inputStream);
                if (inputStream != null) {
                    pageContent = IOStreamUtil.convertInputStreamToString(inputStream);
                } else {
                    getLogger(this).error("homePage stream is empty");
                }
            }
        }
        /// Mark: TESTING (end)

        getLogger(this).info("pageContent: " + pageContent);

        final List<List<String>> parsedList = RegexUtil.parse(pageContent, "<a\\s+href=['\"](index\\w\\d*.html)['\"]>([\\w-]+)</a>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

        parsedList.forEach(parsed -> {
            indexLinks.add(new IndexEntity()
                    .setHref(StringUtil.trimWithNull(parsed.get(1)))
                    .setTitle(StringUtil.trimWithNull(parsed.get(2)))
            );
        });

        return indexLinks;
    }

    private String getCharacterPageUrl(String characterPagePartLink) {
        final int lastIndexOfSlash = MAIN_RULERS_PAGE_URL.lastIndexOf("/");
        String baseLink = MAIN_RULERS_PAGE_URL;
        if (-1 < lastIndexOfSlash) {
            baseLink = MAIN_RULERS_PAGE_URL.substring(0, lastIndexOfSlash);
        }

        return (baseLink + "/" + characterPagePartLink).replace("//", "/");
    }

    private List<PoliticianEntity> fetchCharacterPage(IndexEntity indexEntity) throws Exception {
        getLogger(this).info("do parseCharacterPage --> " + indexEntity);
        getLogger(this).debug("[" + indexEntity.getTitle() + "] page content of character: " + indexEntity.getHref());

        /// Mark: TESTING (start)
        String pageContent = "";
        if (!isTestingData) {
            pageContent = new String(httpHelper.get(
                    new RequestHttpBuilder()
                            .setUrl(getCharacterPageUrl(indexEntity.getHref()))
            ), RULERS_CHARSET);
        } else {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-data/politics-rulers/character-page-content.html")) {
                if (inputStream != null) {
                    pageContent = IOStreamUtil.convertInputStreamToString(inputStream);
                } else {
                    getLogger(this).error("homePage stream is empty");
                }
            }
        }
        /// Mark: TESTING (end)

        return matchParser.parseCharacterPage(pageContent);
    }
}
