package tech.nmhillusion.eciapp.service_impl.un_sanction.parser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.nmhillusion.eciapp.helper.XmlHelper;
import tech.nmhillusion.eciapp.model.un_sanction.IndividualSanctionModel;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class UNSanctionIndividualParser extends XmlNodeParser<IndividualSanctionModel> {

    private String parseForField(String nodeName, NodeList childNodes, String delimiter) {
        return XmlHelper.joiningNodeList(
                XmlHelper.getNodeFromList(nodeName, childNodes)
                , delimiter
                , true
        );
    }

    @Override
    public IndividualSanctionModel parse(Node xmlNode, String delimiter) throws Exception {
        final NodeList childNodes = xmlNode.getChildNodes();
        return new IndividualSanctionModel()
                .setComments1(
                        parseForField(
                                "COMMENTS1", childNodes, delimiter
                        )
                )
                .setDataid(
                        parseForField(
                                "DATAID", childNodes, delimiter
                        )
                )
                .setDesignation(
                        parseForField(
                                "DESIGNATION", childNodes, delimiter
                        )
                )
                .setFirst_name(
                        parseForField(
                                "FIRST_NAME", childNodes, delimiter
                        )
                )
                .setIndividual_address(
                        parseForField(
                                "INDIVIDUAL_ADDRESS", childNodes, delimiter
                        )
                )
                .setIndividual_alias(
                        parseForField(
                                "INDIVIDUAL_ALIAS", childNodes, delimiter
                        )
                )
                .setIndividual_date_of_birth(
                        parseForField(
                                "INDIVIDUAL_DATE_OF_BIRTH", childNodes, delimiter
                        )
                )
                .setIndividual_place_of_birth(
                        parseForField(
                                "INDIVIDUAL_PLACE_OF_BIRTH", childNodes, delimiter
                        )
                )
                .setLast_day_updated(
                        parseForField(
                                "LAST_DAY_UPDATED", childNodes, delimiter
                        )
                )
                .setList_type(
                        parseForField(
                                "LIST_TYPE", childNodes, delimiter
                        )
                )
                .setListed_on(
                        parseForField(
                                "LISTED_ON", childNodes, delimiter
                        )
                )
                .setName_original_script(
                        parseForField(
                                "NAME_ORIGINAL_SCRIPT", childNodes, delimiter
                        )
                )
                .setNationality(
                        parseForField(
                                "NATIONALITY", childNodes, delimiter
                        )
                )
                .setReference_number(
                        parseForField(
                                "REFERENCE_NUMBER", childNodes, delimiter
                        )
                )
                .setSecond_name(
                        parseForField(
                                "SECOND_NAME", childNodes, delimiter
                        )
                )
                .setThird_name(
                        parseForField(
                                "THIRD_NAME", childNodes, delimiter
                        )
                )
                .setTitle(
                        parseForField(
                                "TITLE", childNodes, delimiter
                        )
                )
                .setUn_list_type(
                        parseForField(
                                "UN_LIST_TYPE", childNodes, delimiter
                        )
                )
                .setVersionnum(
                        parseForField(
                                "VERSIONNUM", childNodes, delimiter
                        )
                )
                ;
    }
}
