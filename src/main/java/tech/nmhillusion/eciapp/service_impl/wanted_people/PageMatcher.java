package tech.nmhillusion.eciapp.service_impl.wanted_people;

import tech.nmhillusion.eciapp.model.PageInfoModel;
import tech.nmhillusion.eciapp.model.WantedPeopleEntity;
import tech.nmhillusion.n2mix.util.HtmlUtil;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.n2mix.validator.StringValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class PageMatcher {
    public List<WantedPeopleEntity> parseListWantedPeople(String pageContent) {
        List<WantedPeopleEntity> resultList = new ArrayList<>();

        final Pattern regex_ = Pattern.compile("<tr class=\"row\\d*\">(.*?)</tr>",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL
        );
        final Matcher matchColl_ = regex_.matcher(pageContent);

        while (matchColl_.find()) {
            final String group_ = matchColl_.group(1);
            resultList.add(parseWantedPeople(group_));
        }

        return resultList;
    }

    private WantedPeopleEntity parseWantedPeople(String wantedPeopleContent) {
        WantedPeopleEntity entity = new WantedPeopleEntity();

        final Pattern regex_ = Pattern.compile("<td *.*?>(.*?)</td>",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        final Matcher matchColl_ = regex_.matcher(wantedPeopleContent);

        final MatchResult matchResult = matchColl_.toMatchResult();

        int matchIdx_ = 0;
        while (matchColl_.find()) {
//            LogHelper.getLog(this).infoFormat("group: %s", matchColl_.group());

            if (1 == matchIdx_) {
                entity.setFullName(
                        parseValueFromMatchGroups(matchColl_.group(1))
                );
            }

            if (2 == matchIdx_) {
                entity.setBirthday(
                        parseValueFromMatchGroups(matchColl_.group(1))
                );
            }

            if (3 == matchIdx_) {
                entity.setLivePlace(
                        parseValueFromMatchGroups(matchColl_.group(1))
                );
            }

            if (4 == matchIdx_) {
                entity.setNameOfParents(
                        parseValueFromMatchGroups(matchColl_.group(1))
                );
            }

            if (5 == matchIdx_) {
                entity.setCrimeName(
                        parseValueFromMatchGroups(matchColl_.group(1))
                );
            }

            if (6 == matchIdx_) {
                entity.setDecisionDate(
                        parseValueFromMatchGroups(matchColl_.group(1))
                );
            }

            if (7 == matchIdx_) {
                entity.setDecisionOffice(
                        parseValueFromMatchGroups(matchColl_.group(1))
                );
            }

            matchIdx_ += 1;
        }

        return entity;
    }

    private String parseValueFromMatchGroups(String rawData) {
        return Arrays.stream(HtmlUtil.removeTagSyntax(rawData)
                        .split("\n"))
                .map(StringUtil::trimWithNull)
                .filter(Predicate.not(StringValidator::isBlank))
                .collect(Collectors.joining(";"));
    }

    // <td class="PagerInfoCell">Page 10 of 188</td>

    public PageInfoModel parsePagerInfoCell(String pageContent) {
        final PageInfoModel pageInfoModel = new PageInfoModel();

        final Pattern regex_ = Pattern.compile("<td class=['\"]PagerInfoCell['\"]>(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        final Matcher matched_ = regex_.matcher(pageContent);

        if (matched_.find()) {
            String pageInfo = matched_.group(1);

            final Pattern pageInfoRegex = Pattern.compile("Page (\\d+) of (\\d+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

            final Matcher pageInfoMatch = pageInfoRegex.matcher(pageInfo);

            if (pageInfoMatch.find()) {
                pageInfoModel.setCurrentPageNumber(pageInfoMatch.group(1));
                pageInfoModel.setTotalPages(pageInfoMatch.group(2));
            }
        }

        return pageInfoModel;
    }
}
