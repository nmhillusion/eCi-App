package app.netlify.nmhillusion.eciapp;

import app.netlify.nmhillusion.neon_di.exception.NeonException;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class StartApp {
    public static void main(String[] args) {
        try {
            Application.main(args);
        } catch (NeonException e) {
            throw new RuntimeException(e);
        }
    }
}
