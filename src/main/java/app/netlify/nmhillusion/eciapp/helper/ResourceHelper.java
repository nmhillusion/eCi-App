package app.netlify.nmhillusion.eciapp.helper;

import java.io.InputStream;
import java.net.URL;

/**
 * date: 2023-04-01
 * <p>
 * created-by: nmhillusion
 */

public class ResourceHelper {
    public static InputStream loadResourceStream(String resourcePath) {
        return ResourceHelper.class.getClassLoader().getResourceAsStream(resourcePath);
    }

    public static URL loadResourceUrl(String resourcePath) {
        return ResourceHelper.class.getClassLoader().getResource(resourcePath);
    }
}
