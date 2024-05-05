package tech.nmhillusion.eciapp.service_impl.un_sanction;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tech.nmhillusion.eciapp.model.un_sanction.XmlAttributeModel;
import tech.nmhillusion.n2mix.helper.log.LogHelper;

import java.util.ArrayList;
import java.util.List;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class UNSanctionXmlHandler extends DefaultHandler {

    private List<XmlAttributeModel> convertAttributesToMap(Attributes attributes) {
        final int attributesLength = attributes.getLength();
        final List<XmlAttributeModel> attributesList = new ArrayList<>(attributesLength);

        LogHelper.getLogger(this).info("attributes length: " + attributesLength);
        for (int attrIdx = 0; attrIdx < attributesLength; attrIdx++) {
            final String attrName = attributes.getLocalName(attrIdx);
            final String qName = attributes.getQName(attrIdx);
            final String attrValue = attributes.getValue(qName);

            attributesList.add(
                    new XmlAttributeModel()
                            .setName(attrName)
                            .setQualifiedName(qName)
                            .setValue(attrValue)
            );
        }

        return attributesList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        getLogger(this).info("start element: uri=%s, localName=%s, qName=%s, attributes=%s"
                .formatted(uri, localName, qName, convertAttributesToMap(attributes))
        );
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        getLogger(this).info("end document");
    }
}
