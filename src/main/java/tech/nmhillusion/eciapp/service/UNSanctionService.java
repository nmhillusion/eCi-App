package tech.nmhillusion.eciapp.service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public interface UNSanctionService {
    void readSanctionListFromFile(String filePath) throws Exception;
}
