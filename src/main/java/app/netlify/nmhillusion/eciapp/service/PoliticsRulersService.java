package app.netlify.nmhillusion.eciapp.service;

import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.n2mix.type.function.ThrowableVoidFunction;

/**
 * date: 2022-11-17
 * <p>
 * created-by: nmhillusion
 */
public interface PoliticsRulersService {
    void service(String outputDataPath, ThrowableVoidFunction<StatusModel> onUpdateProgress) throws Throwable;
}
