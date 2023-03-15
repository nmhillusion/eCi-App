package app.netlify.nmhillusion.eciapp.service_impl;

import app.netlify.nmhillusion.eciapp.model.PageInfoModel;
import app.netlify.nmhillusion.eciapp.model.WantedPeopleEntity;
import app.netlify.nmhillusion.n2mix.util.IOStreamUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

class PageMatcherTest {
    private static String pageContent = "";
    private final PageMatcher pageMatcher = new PageMatcher();

    @BeforeAll
    static void init() {
        try (final InputStream page1ContentStream = PageMatcherTest.class.getClassLoader().getResourceAsStream("data-test/wanted-people/page1.html")) {
            pageContent = IOStreamUtil.convertInputStreamToString(Objects.requireNonNull(page1ContentStream));

//            getLog(PageMatcherTest.class).infoFormat("pageContent test: %s", pageContent);
        } catch (Exception ex) {
            getLogger(PageMatcherTest.class).error(ex);
        }
    }

    @Test
    void parseListWantedPeople() {
        Assertions.assertDoesNotThrow(() -> {
            final List<WantedPeopleEntity> wantedPeopleEntities = pageMatcher.parseListWantedPeople(pageContent);
            getLogger(this).infoFormat("parsed list of wanted people: ", wantedPeopleEntities);
        });
    }

    @Test
    void parsePagerInfoCell() {
        Assertions.assertDoesNotThrow(() -> {
            final PageInfoModel pageInfoModel = pageMatcher.parsePagerInfoCell(pageContent);
            getLogger(this).infoFormat("parsed page info model: ", pageInfoModel);
        });
    }
}