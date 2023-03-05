package app.netlify.nmhillusion.eciapp.service_impl;

import app.netlify.nmhillusion.eciapp.model.PageInfoModel;
import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.model.WantedPeopleEntity;
import app.netlify.nmhillusion.eciapp.service.WantedPeopleService;
import app.netlify.nmhillusion.n2mix.constant.OkHttpContentType;
import app.netlify.nmhillusion.n2mix.exception.InvalidArgument;
import app.netlify.nmhillusion.n2mix.exception.MissingDataException;
import app.netlify.nmhillusion.n2mix.helper.YamlReader;
import app.netlify.nmhillusion.n2mix.helper.http.HttpHelper;
import app.netlify.nmhillusion.n2mix.helper.http.RequestHttpBuilder;
import app.netlify.nmhillusion.n2mix.helper.office.excel.ExcelWriteHelper;
import app.netlify.nmhillusion.n2mix.helper.office.excel.model.ExcelDataConverterModel;
import app.netlify.nmhillusion.n2mix.type.ChainMap;
import app.netlify.nmhillusion.n2mix.type.function.ThrowableVoidFunction;
import app.netlify.nmhillusion.neon_di.annotation.Neon;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
            AtomicLong lastActionTimeInMillisRef = new AtomicLong(0L);
            for (int pageNumber = 1; pageNumber <= totalPages; ++pageNumber) {
                final List<WantedPeopleEntity> wantedPeopleEntitiesInPage = __executeCrawlDataFromPage(
                        lastActionTimeInMillisRef,
                        pageNumber,
                        totalPages,
                        onUpdateProgress);
                resultList.addAll(wantedPeopleEntitiesInPage);

                lastActionTimeInMillisRef.set(System.currentTimeMillis());
            }

            __executeExportResultToExcel(
                    resultList,
                    outputDataPath,
                    onUpdateProgress,
                    totalPages);
        }
    }

    private List<WantedPeopleEntity> __executeCrawlDataFromPage(AtomicLong lastActionTimeInMillisRef, int pageNumber, int totalPages, ThrowableVoidFunction<StatusModel> onUpdateProgress) throws Throwable {
        long timeForViewResult = 2_000L;
        onUpdateProgress.throwableVoidApply(new StatusModel()
                .setStatusName("%s/%s".formatted(pageNumber, totalPages))
                .setStatusDetail(">> loading page " + pageNumber)
        );

        while (System.currentTimeMillis() < lastActionTimeInMillisRef.get() + intervalTimeInMillis - timeForViewResult) ;

        final List<WantedPeopleEntity> wantedPeopleEntitiesInPage = crawlWantedPeopleOfPage(pageNumber);
        onUpdateProgress.throwableVoidApply(new StatusModel()
                .setStatusName("%s/%s".formatted(pageNumber, totalPages))
                .setStatusDetail("<< finished executing page " + pageNumber + "; size: " + wantedPeopleEntitiesInPage.size())
        );

        while (System.currentTimeMillis() < lastActionTimeInMillisRef.get() + intervalTimeInMillis) ;

        return wantedPeopleEntitiesInPage;
    }

    private void __executeExportResultToExcel(List<WantedPeopleEntity> resultList, String outputDataPath, ThrowableVoidFunction<StatusModel> onUpdateProgress, int totalPages) throws Throwable {
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

    private void exportListToExcel(List<WantedPeopleEntity> dataList, String outputDataPath) throws IOException, InvalidArgument, MissingDataException {
        final byte[] outputData = new ExcelWriteHelper()
                .addSheetData(new ExcelDataConverterModel<WantedPeopleEntity>()
                        .setSheetName("wanted_people")

                        .addColumnConverters("fullName", WantedPeopleEntity::getFullName)
                        .addColumnConverters("birthday", WantedPeopleEntity::getBirthday)
                        .addColumnConverters("livePlace", WantedPeopleEntity::getLivePlace)
                        .addColumnConverters("nameOfParents", WantedPeopleEntity::getNameOfParents)
                        .addColumnConverters("crimeName", WantedPeopleEntity::getCrimeName)
                        .addColumnConverters("decisionDate", WantedPeopleEntity::getDecisionDate)
                        .addColumnConverters("decisionOffice", WantedPeopleEntity::getDecisionOffice)

                        .setRawData(
                                dataList
                        )
                )
                .build();

        try (OutputStream outputStream = new FileOutputStream(outputDataPath)) {
            outputStream.write(outputData);
            outputStream.flush();
        }
    }
}
