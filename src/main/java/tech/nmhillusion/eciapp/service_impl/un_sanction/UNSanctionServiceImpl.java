package tech.nmhillusion.eciapp.service_impl.un_sanction;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.nmhillusion.eciapp.model.un_sanction.EntitySanctionModel;
import tech.nmhillusion.eciapp.model.un_sanction.IndividualSanctionModel;
import tech.nmhillusion.eciapp.model.un_sanction.SanctionModel;
import tech.nmhillusion.eciapp.service.UNSanctionService;
import tech.nmhillusion.eciapp.service_impl.un_sanction.parser.UNSanctionEntityParser;
import tech.nmhillusion.eciapp.service_impl.un_sanction.parser.UNSanctionIndividualParser;
import tech.nmhillusion.eciapp.service_impl.un_sanction.parser.XmlNodeParser;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.neon_di.annotation.Neon;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
@Neon
public class UNSanctionServiceImpl implements UNSanctionService {
    private static final String DELIMITER = "|";
    private final UNSanctionIndividualParser unSanctionIndividualParser = new UNSanctionIndividualParser();
    private final UNSanctionEntityParser unSanctionEntityParser = new UNSanctionEntityParser();

    @Override
    public SanctionModel readSanctionListFromFile(String filePath) throws Exception {
        getLogger(this).info(">> readSanctionListFromFile: %s".formatted(filePath));

        final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        try (final FileInputStream xmlInStream = new FileInputStream(filePath);
             final BufferedInputStream bufferedXmlInputStream = new BufferedInputStream(xmlInStream)) {

            getLogger(this).info("starting reading xml file %s ...".formatted(filePath));
            Document doc = builder.parse(bufferedXmlInputStream);
            doc.getDocumentElement().normalize();

            final NodeList individualList = doc.getElementsByTagName("INDIVIDUAL");
            LogHelper.getLogger(this).info("found %s individuals".formatted(individualList.getLength()));

            final NodeList entityList = doc.getElementsByTagName("ENTITY");
            LogHelper.getLogger(this).info("found %s entities".formatted(entityList.getLength()));

            final List<IndividualSanctionModel> individualSanctionList = parseNodeList(individualList, unSanctionIndividualParser);
            final List<EntitySanctionModel> entitySanctionList = parseNodeList(individualList, unSanctionEntityParser);

            return new SanctionModel()
                    .setEntitySanctionModelList(entitySanctionList)
                    .setIndividualSanctionModelList(individualSanctionList)
                    ;
        }
    }

    private <T> List<T> parseNodeList(NodeList itemNodeList, XmlNodeParser<T> parser_) throws Exception {
        final List<T> resultList = new ArrayList<>();

        final int nodeListLength = itemNodeList.getLength();
        for (int nodeIdx = 0; nodeIdx < nodeListLength; ++nodeIdx) {
            final Node node_ = itemNodeList.item(nodeIdx);

            final T item_ = parser_.parse(node_, DELIMITER);
            LogHelper.getLogger(this).info("item %s".formatted(item_));

            resultList.add(item_);
        }

        return resultList;
    }
}
