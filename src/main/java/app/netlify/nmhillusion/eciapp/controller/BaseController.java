package app.netlify.nmhillusion.eciapp.controller;

import app.netlify.nmhillusion.eciapp.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-03-27
 * <p>
 * created-by: nmhillusion
 */

public abstract class BaseController {
    private final Image alertIcon;

    public BaseController() throws IOException {
        try (final InputStream alertIconStream = Application.class.getResourceAsStream("icons/app-icon.png")) {
            if (null != alertIconStream) {
                alertIcon = new Image(alertIconStream);
            } else {
                throw new IOException("alert icon stream is null");
            }
        }
    }

    protected void showAlert(Alert.AlertType alertType, String message, ButtonType... buttonTypes) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType, "", buttonTypes);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            stage.getIcons().add(this.alertIcon);
            alert.setHeaderText(message);
            final Optional<ButtonType> result_ = alert.showAndWait();
            getLogger(this).info("result of alert: " + result_);
        });
    }

    protected abstract Pane getMainPane() throws Exception;
}
