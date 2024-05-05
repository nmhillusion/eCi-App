package tech.nmhillusion.eciapp;

import tech.nmhillusion.eciapp.config.ConfigRegistry;
import tech.nmhillusion.n2mix.annotation.EnableN2mix;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.neon_di.NeonEngine;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

@EnableN2mix
public class StartApp {
    private final static NeonEngine neonEngine = new NeonEngine();

    public static NeonEngine getBeanFactoryInstance() {
        return neonEngine;
    }

    public static void main(String[] args) {
        try {
            ConfigRegistry.getInstance()
                    .applyConfigsFromRegistry();

            neonEngine
                    .run(Application.class);
            Application.main(args);
        } catch (Throwable ex) {
            LogHelper.getLogger(StartApp.class).error(ex);
            System.exit(-1);
        }
    }
}
