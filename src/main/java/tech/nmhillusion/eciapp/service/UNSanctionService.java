package tech.nmhillusion.eciapp.service;

import tech.nmhillusion.eciapp.model.un_sanction.SanctionModel;

import java.nio.file.Path;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public interface UNSanctionService {
    SanctionModel readSanctionListFromFile(String filePath) throws Exception;

    Path writeSanctionListToFile(SanctionModel sanctionModel, String excelFilePath) throws Exception;
}
