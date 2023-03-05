package app.netlify.nmhillusion.eciapp.service_impl;

import app.netlify.nmhillusion.eciapp.model.PageInfoModel;
import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.model.WantedPeopleEntity;
import app.netlify.nmhillusion.eciapp.service.WantedPeopleService;
import app.netlify.nmhillusion.n2mix.constant.OkHttpContentType;
import app.netlify.nmhillusion.n2mix.helper.YamlReader;
import app.netlify.nmhillusion.n2mix.helper.http.HttpHelper;
import app.netlify.nmhillusion.n2mix.helper.http.RequestHttpBuilder;
import app.netlify.nmhillusion.n2mix.helper.office.ExcelWriteHelper;
import app.netlify.nmhillusion.n2mix.helper.office.excel.ExcelDataModel;
import app.netlify.nmhillusion.n2mix.type.ChainList;
import app.netlify.nmhillusion.n2mix.type.ChainMap;
import app.netlify.nmhillusion.n2mix.type.function.ThrowableVoidFunction;
import app.netlify.nmhillusion.neon_di.annotation.Neon;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLog;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class WantedPeopleServiceImpl implements WantedPeopleService {

    private final HttpHelper httpUtil = new HttpHelper();

    private final PageMatcher pageMatcher = new PageMatcher();

    private String BASE_URL = "";
    private long intervalTimeInMillis = 1_000;


    public WantedPeopleServiceImpl() {
        try (final InputStream serviceConfigStream = getClass().getClassLoader().getResourceAsStream("service-config/wanted-people.yml")) {
            YamlReader yamlReader = new YamlReader(serviceConfigStream);
            BASE_URL = yamlReader.getProperty("crawl-page.url");
            intervalTimeInMillis = Long.parseLong(yamlReader.getProperty("crawl-service.interval-time-in-millis"));

            getLog(this).infoFormat("BASE_URL: ", BASE_URL);
            getLog(this).infoFormat("intervalTimeInMillis: ", intervalTimeInMillis);
        } catch (Exception ex) {
            getLog(this).error(ex);
        }
    }

    @Override
    public void service(String outputDataPath, ThrowableVoidFunction<StatusModel> onUpdateProgress) throws Throwable {
        final PageInfoModel pageInfoOfCrawlService = getPageInfoOfCrawlService();
        getLog(this).infoFormat("pageInfo: %s", pageInfoOfCrawlService);

        final int totalPages = Integer.parseInt(pageInfoOfCrawlService.getTotalPages());
        final List<WantedPeopleEntity> resultList = new LinkedList<>();
        if (0 < totalPages) {
            long lastActionTimeInMillis = 0;
            for (int pageNumber = 1; pageNumber <= totalPages; ++pageNumber) {
                onUpdateProgress.throwableVoidApply(new StatusModel()
                        .setStatusName("%s/%s".formatted(pageNumber, totalPages))
                        .setStatusDetail("start loading page " + pageNumber)
                );
                
                while (System.currentTimeMillis() < lastActionTimeInMillis + intervalTimeInMillis) ;
                lastActionTimeInMillis = System.currentTimeMillis();

                final List<WantedPeopleEntity> wantedPeopleEntitiesInPage = crawlWantedPeopleOfPage(pageNumber);
                onUpdateProgress.throwableVoidApply(new StatusModel()
                        .setStatusName("%s/%s".formatted(pageNumber, totalPages))
                        .setStatusDetail("finished page " + pageNumber)
                );

                resultList.addAll(wantedPeopleEntitiesInPage);
            }

            if (!resultList.isEmpty()) {
                exportListToExcel(resultList, outputDataPath);
                onUpdateProgress.throwableVoidApply(new StatusModel()
                        .setStatusName("%s/%s".formatted(totalPages, totalPages))
                        .setStatusDetail("completed export data to " + outputDataPath)
                );
            } else {
                onUpdateProgress.throwableVoidApply(new StatusModel()
                        .setStatusName("%s/%s".formatted(totalPages, totalPages))
                        .setStatusDetail("Error: No data to export")
                );
            }
        }
    }

    private PageInfoModel getPageInfoOfCrawlService() throws Exception {
        final String pageContent = fetchPageContentOfPage(1);

        return pageMatcher.parsePagerInfoCell(pageContent);
    }

    private String fetchPageContentOfPage(int pageNumber) throws Exception {
        final byte[] responseData = httpUtil.post(new RequestHttpBuilder()
                .setUrl(BASE_URL)
                .setBody(new ChainMap<String, Object>()
                        .chainPut("__EVENTTARGET", "dnn$ctr1088$MainForm$pager1")
                        .chainPut("__EVENTARGUMENT", String.valueOf(pageNumber)), OkHttpContentType.FORM_DATA)
        );

        final String responseDataInString = new String(responseData);
//        getLog(this).debugFormat("Response data of wanted people: ", responseDataInString);

        return responseDataInString;
    }

    private List<WantedPeopleEntity> crawlWantedPeopleOfPage(int pageNumber) throws Exception {
        getLog(this).infoFormat(">> crawlWantedPeopleOfPage(int pageNumber = %s)", pageNumber);
        final String pageContent = fetchPageContentOfPage(pageNumber);

        final List<WantedPeopleEntity> wantedPeopleEntitiesInPage = pageMatcher.parseListWantedPeople(pageContent);

        getLog(this).infoFormat("<< crawlWantedPeopleOfPage(int pageNumber = %s): size: %s", pageNumber, wantedPeopleEntitiesInPage.size());

        return wantedPeopleEntitiesInPage;
    }

    private void exportListToExcel(List<WantedPeopleEntity> dataList, String outputDataPath) throws IOException {
        final byte[] outputData = new ExcelWriteHelper()
                .addSheetData(new ExcelDataModel()
                        .setSheetName("wanted_people")
                        .setHeaders(new ChainList<List<String>>()
                                .chainAdd(
                                        Arrays.asList(
                                                "fullName",
                                                "birthday",
                                                "livePlace",
                                                "nameOfParents",
                                                "crimeName",
                                                "decisionDate",
                                                "decisionOffice"
                                        )

                                )
                        )
                        .setBodyData(
                                dataList.stream()
                                        .map(WantedPeopleEntity::dataToList)
                                        .collect(Collectors.toList())
                        )
                )
                .build();

        try (OutputStream outputStream = new FileOutputStream(outputDataPath)) {
            outputStream.write(outputData);
            outputStream.flush();
        }
    }
}
