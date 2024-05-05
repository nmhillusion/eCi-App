package tech.nmhillusion.eciapp.service_impl.un_sanction;

import tech.nmhillusion.eciapp.service.UNSanctionService;
import tech.nmhillusion.neon_di.annotation.Neon;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
@Neon
public class UNSanctionServiceImpl implements UNSanctionService {
    @Override
    public void readSanctionListFromFile(String filePath) throws Exception {
        getLogger(this).info(">> readSanctionListFromFile: %s".formatted(filePath));

        final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        final SAXParser saxParser = saxParserFactory.newSAXParser();

        try (final FileInputStream xmlInStream = new FileInputStream(filePath);
             final BufferedInputStream bufferedXmlInputStream = new BufferedInputStream(xmlInStream)) {

            getLogger(this).info("starting reading xml file %s ...".formatted(filePath));
            saxParser.parse(bufferedXmlInputStream, new UNSanctionXmlHandler());
        }
    }
}
