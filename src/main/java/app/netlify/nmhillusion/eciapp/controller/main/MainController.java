package app.netlify.nmhillusion.eciapp.controller.main;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.service.MainService;
import app.netlify.nmhillusion.eciapp.service.WantedPeopleService;
import app.netlify.nmhillusion.n2mix.validator.StringValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-02-25
 * <p>
 * created-by: nmhillusion
 */

public class MainController {
    private final MainService mainService;
    private final WantedPeopleService wantedPeopleService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public TextField txtOutDataPath;
    public Label lblExecuteStatus;
    public Label lblExecuteStatusDetail;
    public Button btnExecuteOutDataWantedPeople;
    private Image alertIcon;
    @FXML
    private Label appTitle;

    public MainController() throws Exception {
        final Optional<MainService> mainServiceOptional = Application.getBeanFactoryInstance().findFirstNeonByClass(MainService.class);
        if (mainServiceOptional.isEmpty()) {
            throw new Exception("Cannot find MainService");
        }

        final Optional<WantedPeopleService> wantedPeopleServiceOptional = Application.getBeanFactoryInstance().findFirstNeonByClass(WantedPeopleService.class);
        if (wantedPeopleServiceOptional.isEmpty()) {
            throw new Exception("Cannot find WantedPeopleService");
        }

        try (final InputStream alertIconStream = Application.class.getResourceAsStream("icons/app-icon.png")) {
            if (alertIconStream != null) {
                alertIcon = new Image(alertIconStream);
            }
        }

        mainService = mainServiceOptional.get();
        wantedPeopleService = wantedPeopleServiceOptional.get();

        Application.addListenerOnStop(executorService::shutdownNow);

        updateEnableOfExecuteButton(true);
    }

    @FXML
    protected void onClickButton__WantedPeople() {
        getLogger(this).info("click on button WantedPeople");
    }

    public void onClickButton__BrowserOutData(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Set place to put excel file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File(*.xlsx)", "*.xlsx"));
        final File chosenFile = fileChooser.showSaveDialog(null);

        getLogger(this).infoFormat("chosen file: ", chosenFile);

        if (null != chosenFile) {
            txtOutDataPath.setText(chosenFile.getAbsolutePath());
        }
    }

    public void onClickButton__ExecuteOutDataWantedPeople(ActionEvent actionEvent) {
        try {
            final String outputDataPath = txtOutDataPath.getText();
            if (StringValidator.isBlank(outputDataPath)) {
                throw new Exception("Must setup output data path");
            }

            executorService.submit(() -> {
                try {
                    updateEnableOfExecuteButton(false);
                    wantedPeopleService.service(outputDataPath, this::updateStatusModel);
                    showAlert(Alert.AlertType.INFORMATION, "Completed", ButtonType.OK);
                    updateEnableOfExecuteButton(true);
                } catch (Throwable ex) {
                    getLogger(this).error(ex);
                    showAlert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                }
            });
        } catch (Throwable ex) {
            getLogger(this).error(ex);
            showAlert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        }
    }

    private void updateEnableOfExecuteButton(boolean enable) {
        Platform.runLater(() -> {
            btnExecuteOutDataWantedPeople.setDisable(!enable);
        });
    }

    private void updateStatusModel(StatusModel statusModel) {
        Platform.runLater(() -> {
            lblExecuteStatus.setText(statusModel.getStatusName());
            lblExecuteStatusDetail.setText(statusModel.getStatusDetail());

            getLogger(this).infoFormat("update status: ", statusModel);
        });
    }

    private void showAlert(Alert.AlertType alertType, String message, ButtonType buttonTypes) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType, "", buttonTypes);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            stage.getIcons().add(this.alertIcon);
            alert.setHeaderText(message);
            final Optional<ButtonType> result_ = alert.showAndWait();
            getLogger(this).info("result of alert: " + result_);
        });
    }
}
