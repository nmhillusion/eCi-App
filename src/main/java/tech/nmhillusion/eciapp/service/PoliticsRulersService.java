package tech.nmhillusion.eciapp.service;

import tech.nmhillusion.eciapp.model.StatusModel;
import tech.nmhillusion.n2mix.type.function.ThrowableVoidFunction;

/**
 * date: 2022-11-17
 * <p>
 * created-by: nmhillusion
 */
public interface PoliticsRulersService {
    void service(String outputDataPath, ThrowableVoidFunction<StatusModel> onUpdateProgress) throws Throwable;
}
