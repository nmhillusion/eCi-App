package app.netlify.nmhillusion.eciapp;

import app.netlify.nmhillusion.neon_di.NeonEngine;
import app.netlify.nmhillusion.neon_di.exception.NeonException;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class StartApp {
    private static NeonEngine neonEngine;

    public static NeonEngine getBeanFactoryInstance() {
        return neonEngine;
    }

    public static void main(String[] args) {
        try {
            neonEngine = new NeonEngine();
            neonEngine
                    .run(Application.class);
            Application.main(args);
        } catch (NeonException e) {
            throw new RuntimeException(e);
        }
    }
}
