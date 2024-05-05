package tech.nmhillusion.eciapp;

import tech.nmhillusion.neon_di.NeonEngine;
import tech.nmhillusion.neon_di.exception.NeonException;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class StartApp {
    private final static NeonEngine neonEngine = new NeonEngine();

    public static NeonEngine getBeanFactoryInstance() {
        return neonEngine;
    }

    public static void main(String[] args) {
        try {
            neonEngine
                    .run(Application.class);
            Application.main(args);
        } catch (NeonException e) {
            throw new RuntimeException(e);
        }
    }
}
