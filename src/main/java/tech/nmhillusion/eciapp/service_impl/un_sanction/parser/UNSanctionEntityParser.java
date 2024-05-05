package tech.nmhillusion.eciapp.service_impl.un_sanction.parser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.nmhillusion.eciapp.helper.XmlHelper;
import tech.nmhillusion.eciapp.model.un_sanction.EntitySanctionModel;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class UNSanctionEntityParser extends XmlNodeParser<EntitySanctionModel> {

    private String parseForField(String nodeName, NodeList childNodes, String delimiter) {
        return XmlHelper.joiningNodeList(
                XmlHelper.getNodeFromList(nodeName, childNodes)
                , delimiter
                , true
        );
    }

    @Override
    public EntitySanctionModel parse(Node xmlNode, String delimiter) throws Exception {
        final NodeList childNodes = xmlNode.getChildNodes();
        return new EntitySanctionModel()
                .setComments1(
                        parseForField("COMMENTS1", childNodes, delimiter)
                )
                .setDataid(
                        parseForField("DATAID", childNodes, delimiter)
                )
                .setEntity_address(
                        parseForField("ENTITY_ADDRESS", childNodes, delimiter)
                )
                .setEntity_alias(
                        parseForField("ENTITY_ALIAS", childNodes, delimiter)
                )
                .setFirst_name(
                        parseForField("FIRST_NAME", childNodes, delimiter)
                )
                .setLast_day_updated(
                        parseForField("LAST_DAY_UPDATED", childNodes, delimiter)
                )
                .setList_type(
                        parseForField("LIST_TYPE", childNodes, delimiter)
                )
                .setListed_on(
                        parseForField("LISTED_ON", childNodes, delimiter)
                )
                .setReference_number(
                        parseForField("REFERENCE_NUMBER", childNodes, delimiter)
                )
                .setUn_list_type(
                        parseForField("UN_LIST_TYPE", childNodes, delimiter)
                )
                .setVersionnum(
                        parseForField("VERSIONNUM", childNodes, delimiter)
                )
                ;
    }
}
