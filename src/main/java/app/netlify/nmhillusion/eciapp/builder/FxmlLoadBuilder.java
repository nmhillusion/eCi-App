package app.netlify.nmhillusion.eciapp.builder;

import app.netlify.nmhillusion.eciapp.StartApp;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import tech.nmhillusion.n2mix.exception.InvalidArgument;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

    public <T> T build() throws IOException, InvalidArgument {
        if (null == fxmlFileURL) {
            throw new InvalidArgument("Missing setup fxml file url");
        }

        if (null == controller) {
            return FXMLLoader.load(
                    getFxmlFileURL(),
                    defaultResourceBundle,
                    defaultBuilderFactory,
                    classControllerToInstance -> StartApp.getBeanFactoryInstance()
                            .makeSureObtainNeon(classControllerToInstance)
            );
        } else {
            final FXMLLoader fxmlLoader = new FXMLLoader(getFxmlFileURL());
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        }
    }
}
