package app.netlify.nmhillusion.eciapp.helper;

import app.netlify.nmhillusion.neon_di.annotation.Neon;

import java.io.InputStream;

/**
 * date: 2023-04-01
 * <p>
 * created-by: nmhillusion
 */

public class ResourceHelper {
    public static InputStream loadResource(String resourcePath) {
        return ResourceHelper.class.getClassLoader().getResourceAsStream(resourcePath);
    }
}
