package app.netlify.nmhillusion.eciapp;

import app.netlify.nmhillusion.n2mix.exception.GeneralException;
import app.netlify.nmhillusion.n2mix.helper.log.LogHelper;
import app.netlify.nmhillusion.neon_di.NeonEngine;
import app.netlify.nmhillusion.neon_di.exception.NeonException;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class StartApp extends javafx.application.Application {
    private static NeonEngine neonEngine;

    public static NeonEngine getBeanFactoryInstance() {
        return neonEngine;
    }

    public static void main(String[] args) {
        try {
            neonEngine = new NeonEngine();
            neonEngine
                    .run(Application.class);
            Application.launch(args);
        } catch (NeonException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Optional<Application> applicationOptional = getBeanFactoryInstance().findFirstNeonByClass(Application.class);
        if (applicationOptional.isEmpty()) {
            throw new GeneralException("Cannot determine application instance");
        }

        LogHelper.getLogger(this).info(">> start running app from StartApp");
        applicationOptional.get().start(primaryStage);
    }
}
