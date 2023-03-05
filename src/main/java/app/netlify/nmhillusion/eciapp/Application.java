package app.netlify.nmhillusion.eciapp;

import app.netlify.nmhillusion.n2mix.helper.YamlReader;
import app.netlify.nmhillusion.n2mix.helper.log.LogHelper;
import app.netlify.nmhillusion.n2mix.type.function.ThrowableVoidNoInputFunction;
import app.netlify.nmhillusion.neon_di.NeonEngine;
import app.netlify.nmhillusion.neon_di.exception.NeonException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {
    private static final List<ThrowableVoidNoInputFunction> listenersWhenStop = new ArrayList<>();
    private static NeonEngine neonEngine;

    public static NeonEngine getBeanFactoryInstance() {
        return neonEngine;
    }

    public static void addListenerOnStop(ThrowableVoidNoInputFunction listener) {
        if (null != listener) {
            listenersWhenStop.add(listener);
        }
    }

    public static void main(String[] args) throws NeonException {
        neonEngine = new NeonEngine();
        neonEngine
                .run(Application.class);
        launch();
    }

    private String getConfig(String configKey) {
        try (final InputStream configStream = getClass().getClassLoader().getResourceAsStream("app-config/main.yml")) {
            final YamlReader yamlReader = new YamlReader(configStream);
            return yamlReader.getProperty(configKey);
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        final String appTitle = getConfig("title");
        stage.setTitle(appTitle);
        try (final InputStream appIcon = Application.class.getResourceAsStream("icons/app-icon.png")) {
            LogHelper.getLog(this).infoFormat("set icon for app -> %s", appIcon);
            if (null != appIcon) {
                stage.getIcons().add(new Image(appIcon));
            }
        }

        final FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainView.fxml"));
        final Scene scene = new Scene(fxmlLoader.load(), 420, 300);
        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        for (var listener : listenersWhenStop) {
            listener.apply();
        }
    }
}