package app.netlify.nmhillusion.eciapp.service;

import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.n2mix.type.function.ThrowableVoidFunction;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */
public interface WantedPeopleService {
    void service(String outputDataPath, ThrowableVoidFunction<StatusModel> onUpdateProgress) throws Throwable;
}
