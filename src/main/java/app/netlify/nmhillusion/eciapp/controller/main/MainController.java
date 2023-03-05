package app.netlify.nmhillusion.eciapp.controller.main;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.service.MainService;
import app.netlify.nmhillusion.eciapp.service.WantedPeopleService;
import app.netlify.nmhillusion.n2mix.helper.log.LogHelper;
import app.netlify.nmhillusion.n2mix.validator.StringValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLog;

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

        mainService = mainServiceOptional.get();
        wantedPeopleService = wantedPeopleServiceOptional.get();

        Application.addListenerOnStop(executorService::shutdownNow);

        updateEnableOfExecuteButton(true);
    }

    @FXML
    protected void onClickButton__WantedPeople() {
        getLog(this).info("click on button WantedPeople");
    }

    public void onClickButton__BrowserOutData(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Set place to put excel file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File(*.xlsx)", "*.xlsx"));
        final File chosenFile = fileChooser.showSaveDialog(null);

        getLog(this).infoFormat("chosen file: ", chosenFile);

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
                    LogHelper.getLog(this).error(ex);
                    showAlert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                }
            });
        } catch (Throwable ex) {
            getLog(this).error(ex);
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
        });
    }

    private void showAlert(Alert.AlertType alertType, String message, ButtonType buttonTypes) {
        Alert alert = new Alert(alertType, "", buttonTypes);
        alert.setHeaderText(message);
        final Optional<ButtonType> result_ = alert.showAndWait();
        getLog(this).info("result of alert: " + result_);
    }
}
