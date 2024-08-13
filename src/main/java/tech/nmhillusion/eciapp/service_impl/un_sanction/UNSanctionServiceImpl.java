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
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.ExcelDataConverterModel;
import tech.nmhillusion.n2mix.type.function.VoidFunction;
import tech.nmhillusion.neon_di.annotation.Neon;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
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
    public SanctionModel readSanctionListFromFile(String filePath, VoidFunction<String> logDelegate) throws Exception {
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

            final List<IndividualSanctionModel> individualSanctionList = parseNodeList(individualList, unSanctionIndividualParser, logDelegate);
            final List<EntitySanctionModel> entitySanctionList = parseNodeList(entityList, unSanctionEntityParser, logDelegate);

            return new SanctionModel()
                    .setEntitySanctionModelList(entitySanctionList)
                    .setIndividualSanctionModelList(individualSanctionList)
                    ;
        }
    }

    private <T> List<T> parseNodeList(NodeList itemNodeList, XmlNodeParser<T> parser_, VoidFunction<String> logDelegate) throws Exception {
        final List<T> resultList = new ArrayList<>();

        final int nodeListLength = itemNodeList.getLength();
        for (int nodeIdx = 0; nodeIdx < nodeListLength; ++nodeIdx) {
            final Node node_ = itemNodeList.item(nodeIdx);

            final T item_ = parser_.parse(node_, DELIMITER);
            LogHelper.getLogger(this).info("item %s".formatted(item_));
            logDelegate.apply("item %s".formatted(item_));

            resultList.add(item_);
        }

        return resultList;
    }

    @Override
    public Path writeSanctionListToFile(SanctionModel sanctionModel, String excelFilePath) throws Exception {
        final byte[] exportData_ = new ExcelWriteHelper()
                .addSheetData(
                        new ExcelDataConverterModel<IndividualSanctionModel>()
                                .addColumnConverters("DATAID", IndividualSanctionModel::getDataid)
                                .addColumnConverters("DESIGNATION", IndividualSanctionModel::getDesignation)
                                .addColumnConverters("FIRST_NAME", IndividualSanctionModel::getFirst_name)
                                .addColumnConverters("INDIVIDUAL_ADDRESS", IndividualSanctionModel::getIndividual_address)
                                .addColumnConverters("INDIVIDUAL_ALIAS", IndividualSanctionModel::getIndividual_alias)
                                .addColumnConverters("INDIVIDUAL_DATE_OF_BIRTH", IndividualSanctionModel::getIndividual_date_of_birth)
                                .addColumnConverters("INDIVIDUAL_PLACE_OF_BIRTH", IndividualSanctionModel::getIndividual_place_of_birth)
                                .addColumnConverters("LAST_DAY_UPDATED", IndividualSanctionModel::getLast_day_updated)
                                .addColumnConverters("COMMENTS1", IndividualSanctionModel::getComments1)
                                .addColumnConverters("SECOND_NAME", IndividualSanctionModel::getSecond_name)
                                .addColumnConverters("LIST_TYPE", IndividualSanctionModel::getList_type)
                                .addColumnConverters("LISTED_ON", IndividualSanctionModel::getListed_on)
                                .addColumnConverters("NAME_ORIGINAL_SCRIPT", IndividualSanctionModel::getName_original_script)
                                .addColumnConverters("NATIONALITY", IndividualSanctionModel::getNationality)
                                .addColumnConverters("REFERENCE_NUMBER", IndividualSanctionModel::getReference_number)
                                .addColumnConverters("TITLE", IndividualSanctionModel::getTitle)
                                .addColumnConverters("UN_LIST_TYPE", IndividualSanctionModel::getUn_list_type)
                                .addColumnConverters("VERSIONNUM", IndividualSanctionModel::getVersionnum)
                                .setRawData(sanctionModel.getIndividualSanctionModelList())
                                .setSheetName("Individuals")

                )
                .addSheetData(
                        new ExcelDataConverterModel<EntitySanctionModel>()
                                .addColumnConverters("COMMENTS1", EntitySanctionModel::getComments1)
                                .addColumnConverters("DATAID", EntitySanctionModel::getDataid)
                                .addColumnConverters("ENTITY_ADDRESS", EntitySanctionModel::getEntity_address)
                                .addColumnConverters("ENTITY_ALIAS", EntitySanctionModel::getEntity_alias)
                                .addColumnConverters("FIRST_NAME", EntitySanctionModel::getFirst_name)
                                .addColumnConverters("LAST_DAY_UPDATED", EntitySanctionModel::getLast_day_updated)
                                .addColumnConverters("LIST_TYPE", EntitySanctionModel::getList_type)
                                .addColumnConverters("LISTED_ON", EntitySanctionModel::getListed_on)
                                .addColumnConverters("REFERENCE_NUMBER", EntitySanctionModel::getReference_number)
                                .addColumnConverters("UN_LIST_TYPE", EntitySanctionModel::getUn_list_type)
                                .addColumnConverters("VERSIONNUM", EntitySanctionModel::getVersionnum)
                                .setRawData(sanctionModel.getEntitySanctionModelList())
                                .setSheetName("Entities")
                )
                .build();

        final Path outputPath = Path.of(excelFilePath);

        try (final FileOutputStream fileOutputStream_ = new FileOutputStream(excelFilePath);
             final BufferedOutputStream bufferedOutputStream_ = new BufferedOutputStream(fileOutputStream_)) {
            bufferedOutputStream_.write(exportData_);
            bufferedOutputStream_.flush();
        }

        return outputPath;
    }
}
