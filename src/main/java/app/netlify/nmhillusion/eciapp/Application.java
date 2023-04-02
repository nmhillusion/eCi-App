package app.netlify.nmhillusion.eciapp;

import app.netlify.nmhillusion.eciapp.builder.FxmlLoadBuilder;
import app.netlify.nmhillusion.eciapp.helper.ResourceHelper;
import app.netlify.nmhillusion.n2mix.exception.InvalidArgument;
import app.netlify.nmhillusion.n2mix.helper.YamlReader;
import app.netlify.nmhillusion.n2mix.type.function.ThrowableVoidNoInputFunction;
import app.netlify.nmhillusion.neon_di.annotation.Neon;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

@Neon
public class Application extends javafx.application.Application {
    private static final List<ThrowableVoidNoInputFunction> listenersWhenStop = new ArrayList<>();

    public static void addListenerOnStop(ThrowableVoidNoInputFunction listener) {
        if (null != listener) {
            listenersWhenStop.add(listener);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String getConfig(String configKey) {
        try (final InputStream configStream = ResourceHelper.loadResourceStream("app-config/main.yml")) {
            final YamlReader yamlReader = new YamlReader(configStream);
            return yamlReader.getProperty(configKey);
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void start(Stage stage) throws IOException, InvalidArgument {
        final String appTitle = getConfig("title");
        final int appWidth = Integer.parseInt(getConfig("size.width"));
        final int appHeight = Integer.parseInt(getConfig("size.height"));

        stage.setTitle(appTitle);
        try (final InputStream appIcon = ResourceHelper.loadResourceStream("app-icons/app-icon.png")) {
            getLogger(this).infoFormat("set icon for app -> %s", appIcon);
            if (null != appIcon) {
                stage.getIcons().add(new Image(appIcon));
            }
        }

        final URL mainViewUrl = ResourceHelper.loadResourceUrl("app-screens/mainView.fxml");
        final Scene scene = new Scene(new FxmlLoadBuilder()
                .setFxmlFileURL(mainViewUrl)
                .build()
                , appWidth, appHeight);
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