package tech.nmhillusion.eciapp.service;

import tech.nmhillusion.eciapp.model.un_sanction.SanctionModel;
import tech.nmhillusion.n2mix.type.function.VoidFunction;

import java.nio.file.Path;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public interface UNSanctionService {
    SanctionModel readSanctionListFromFile(String filePath, VoidFunction<String> logDelegate) throws Exception;

    Path writeSanctionListToFile(SanctionModel sanctionModel, String excelFilePath) throws Exception;
}
