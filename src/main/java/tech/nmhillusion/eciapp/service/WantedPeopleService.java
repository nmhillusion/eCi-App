package tech.nmhillusion.eciapp.service;

import tech.nmhillusion.eciapp.model.StatusModel;
import tech.nmhillusion.n2mix.type.function.ThrowableVoidFunction;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */
public interface WantedPeopleService {
    void service(String outputDataPath, ThrowableVoidFunction<StatusModel> onUpdateProgress) throws Throwable;
}
