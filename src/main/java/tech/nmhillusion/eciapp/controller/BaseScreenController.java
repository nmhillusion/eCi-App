package tech.nmhillusion.eciapp.controller;

import tech.nmhillusion.eciapp.controller.main.MainController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-03-27
 * <p>
 * created-by: nmhillusion
 */

public abstract class BaseScreenController {
    private Image alertIcon;

    public BaseScreenController() throws IOException {
    }

    protected synchronized void showAlert(Alert.AlertType alertType, String message, ButtonType... buttonTypes) {
        if (null == alertIcon) {
            try (final InputStream alertIconStream = getClass().getClassLoader().getResourceAsStream("app-icons/app-icon.png")) {
                if (null != alertIconStream) {
                    alertIcon = new Image(alertIconStream);
                } else {
                    final URL alertIconResource = getClass().getClassLoader().getResource(".");
                    getLogger(this).error("from path: " + alertIconResource);
                    throw new IOException("alert icon stream is null");
                }
            } catch (IOException e) {
                getLogger(this).error("cannot load alert icon");
            }
        }

        Platform.runLater(() -> {
            Alert alert = new Alert(alertType, "", buttonTypes);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            stage.getIcons().add(this.alertIcon);
            alert.setHeaderText(message);
            final Optional<ButtonType> result_ = alert.showAndWait();
            getLogger(this).info("result of alert: " + result_);
        });
    }

    public abstract Pane getMainPane() throws Exception;

    public void onApplyPane(Pane appliedPane, MainController mainController) throws Exception {
    }

}
