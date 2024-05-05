package tech.nmhillusion.eciapp.controller.un_sanction;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.slf4j.event.Level;
import tech.nmhillusion.eciapp.Application;
import tech.nmhillusion.eciapp.builder.FxmlLoadBuilder;
import tech.nmhillusion.eciapp.builder.LogMessageBuilder;
import tech.nmhillusion.eciapp.controller.BaseScreenController;
import tech.nmhillusion.eciapp.controller.main.MainController;
import tech.nmhillusion.eciapp.helper.ResourceHelper;
import tech.nmhillusion.eciapp.model.un_sanction.SanctionModel;
import tech.nmhillusion.eciapp.service.UNSanctionService;
import tech.nmhillusion.n2mix.validator.StringValidator;
import tech.nmhillusion.neon_di.annotation.Inject;
import tech.nmhillusion.neon_di.annotation.Neon;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */

@Neon
public class UNSanctionScreenController extends BaseScreenController {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @FXML
    public Button btnExecuteOutDataUNSanction;

    @FXML
    public TextField txtOutDataPath;
    @FXML
    public TextField txtInDataPath;

    @FXML
    public Label lblExecuteStatus;
    @FXML
    public Label lblExecuteStatusDetail;

    @Inject
    private UNSanctionService unSanctionService;

    private MainController mainController;


    public UNSanctionScreenController() throws IOException {
        Application.addListenerOnStop(executorService::shutdownNow);
    }

    @Override
    public void onApplyPane(Pane appliedPane, MainController mainController) throws Exception {
        super.onApplyPane(appliedPane, mainController);

        this.mainController = mainController;
    }

    @Override
    public Pane getMainPane() throws Exception {
        return new FxmlLoadBuilder()
                .setFxmlFileURL(ResourceHelper.loadResourceUrl("app-screens/unSanctionScreen.fxml"))
                .build();
    }

    @FXML
    public void onClickButton__BrowserInData(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose xml file of UN Sanction List");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File(*.xml)", "*.xml"));
        final File chosenFile = fileChooser.showOpenDialog(null);

        getLogger(this).infoFormat("chosen file: ", chosenFile);

        if (null != chosenFile) {
            txtInDataPath.setText(chosenFile.getAbsolutePath());
        }
    }

    @FXML
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

    private void validateForInputField(String data, String errorMessage) {
        if (StringValidator.isBlank(data)) {
            logMessageToUI(errorMessage, Level.ERROR);
            this.showAlert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void updateEnableOfExecuteButton(boolean enable_) {
        Platform.runLater(() -> {
            btnExecuteOutDataUNSanction.setDisable(!enable_);

            if (null != mainController) {
                mainController.setEnableMainMenuButtons(enable_);
            }
        });
    }

    private void logMessageToUI(String message, Level logLevel) {
        Platform.runLater(() -> {
            mainController.addLogToUI(
                    new LogMessageBuilder()
                            .setLogLevel(logLevel)
                            .setMessage(message)
                            .setContextClazz(getClass())
            );
        });
    }

    @FXML
    public void onClickButton__ExecuteOutDataUNSanction(ActionEvent actionEvent) {
        updateEnableOfExecuteButton(false);
        try {
            validateForInputField(txtInDataPath.getText(), "Please choose xml file of UN Sanction List");
            validateForInputField(txtOutDataPath.getText(), "Please set place to put excel file");
        } catch (Exception ex) {
            updateEnableOfExecuteButton(true);
            throw ex;
        }

        final Future<Path> completedParseUnSanctionFilePath = executorService.submit(() -> {
            try {
                logMessageToUI("Execute Out Data UNSanction ...", Level.INFO);

                logMessageToUI("Start parse UN Sanction List ...", Level.INFO);
                final SanctionModel sanctionModel = unSanctionService.readSanctionListFromFile(
                        txtInDataPath.getText()
                        , msg -> logMessageToUI(msg, Level.INFO)
                );
                logMessageToUI("Completed parse UN Sanction List", Level.INFO);

                logMessageToUI("Start write excel file ...", Level.INFO);
                final Path savedPath = unSanctionService.writeSanctionListToFile(sanctionModel, txtOutDataPath.getText());
                logMessageToUI("Completed write excel file: %s".formatted(savedPath), Level.INFO);

                return savedPath;
            } catch (Exception ex) {
                getLogger(this).error(ex);
                logMessageToUI(ex.getMessage(), Level.ERROR);
                throw ex;
            } finally {
                updateEnableOfExecuteButton(true);
            }
        });

        try {
            final Path savedPath = completedParseUnSanctionFilePath.get();

            showAlert(Alert.AlertType.INFORMATION,
                    "Completed. Do you want to open folder?",
                    (result) -> {
                        if (ButtonType.OK.equals(result) && Desktop.isDesktopSupported()) {
                            try {
                                Desktop.getDesktop()
                                        .open(
                                                savedPath.getParent().toFile()
                                        );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, ButtonType.OK, ButtonType.CANCEL);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
