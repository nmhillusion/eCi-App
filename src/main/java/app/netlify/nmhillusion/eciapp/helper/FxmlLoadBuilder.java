package app.netlify.nmhillusion.eciapp.helper;

import app.netlify.nmhillusion.eciapp.StartApp;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.PropertyResourceBundle;

/**
 * date: 2023-04-01
 * <p>
 * created-by: nmhillusion
 */

public class FxmlLoadBuilder {
    private static final JavaFXBuilderFactory defaultBuilderFactory = new JavaFXBuilderFactory(StartApp.class.getClassLoader());
    private static final PropertyResourceBundle defaultResourceBundle;

    static {
        try {
            defaultResourceBundle = new PropertyResourceBundle(InputStream.nullInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL fxmlFileURL;
    private Object controller;

    public URL getFxmlFileURL() {
        return fxmlFileURL;
    }

    public FxmlLoadBuilder setFxmlFileURL(URL fxmlFileURL) {
        this.fxmlFileURL = fxmlFileURL;
        return this;
    }

    public Object getController() {
        return controller;
    }

    public FxmlLoadBuilder setController(Object controller) {
        this.controller = controller;
        return this;
    }

    public <T> T build() throws IOException {
        if (null == controller) {
            return FXMLLoader.load(
                    getFxmlFileURL(),
                    defaultResourceBundle,
                    defaultBuilderFactory,
                    classControllerToInstance -> {
                        final Optional<?> instanceOptional = StartApp.getBeanFactoryInstance()
                                .findFirstNeonByClass(classControllerToInstance);
                        return instanceOptional.orElse(null);
                    }
            );
        } else {
            final FXMLLoader fxmlLoader = new FXMLLoader(getFxmlFileURL());
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        }
    }
}