package tech.nmhillusion.eciapp.service;

import tech.nmhillusion.eciapp.model.un_sanction.SanctionModel;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public interface UNSanctionService {
    SanctionModel readSanctionListFromFile(String filePath) throws Exception;
}
