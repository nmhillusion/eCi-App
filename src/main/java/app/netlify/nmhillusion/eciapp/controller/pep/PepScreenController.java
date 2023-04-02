package app.netlify.nmhillusion.eciapp.controller.pep;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.builder.LogMessageBuilder;
import app.netlify.nmhillusion.eciapp.controller.BaseScreenController;
import app.netlify.nmhillusion.eciapp.controller.main.MainController;
import app.netlify.nmhillusion.eciapp.builder.FxmlLoadBuilder;
import app.netlify.nmhillusion.eciapp.helper.ResourceHelper;
import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.service.PoliticsRulersService;
import app.netlify.nmhillusion.n2mix.validator.StringValidator;
import app.netlify.nmhillusion.neon_di.annotation.Inject;
import app.netlify.nmhillusion.neon_di.annotation.Neon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import org.slf4j.event.Level;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-03-27
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class PepScreenController extends BaseScreenController {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    @FXML
    public Button btnExecuteOutDataPEP;
    @FXML
    public Button btnBrowserOutputFolder;
    @FXML
    public Label lblExecuteStatus;
    @FXML
    public Label lblExecuteStatusDetail;
    @FXML
    public TextField txtOutDataPath;

    @Inject
    private PoliticsRulersService politicsRulersService;
    private MainController mainController;

    public PepScreenController() throws IOException {
        Application.addListenerOnStop(executorService::shutdownNow);
    }

    @Override
    public Pane getMainPane() throws Exception {
        getLogger(this).info("service: " + politicsRulersService);

        return new FxmlLoadBuilder()
                .setFxmlFileURL(ResourceHelper.loadResourceUrl("app-screens/pepScreen.fxml"))
                .build();
    }

    @Override
    public void onApplyPane(Pane appliedPane, MainController mainController) throws Exception {
        super.onApplyPane(appliedPane, mainController);

        this.mainController = mainController;
    }

    @FXML
    protected void onClickButton__BrowserOutData(ActionEvent actionEvent) {
        getLogger(this).info("choose output folder");

        final DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Set folder to save excel files");
        final File chosenFolder = fileChooser.showDialog(null);

        getLogger(this).infoFormat("chosen folder: ", chosenFolder);

        if (null != chosenFolder) {
            txtOutDataPath.setText(chosenFolder.getAbsolutePath());
        }
    }

    @FXML
    protected void onClickButton__ExecuteOutDataPEP(ActionEvent actionEvent) {
        getLogger(this).info("on click on PEP execute");

        try {
            final String outputDataPath = txtOutDataPath.getText();
            if (StringValidator.isBlank(outputDataPath)) {
                throw new Exception("Must setup output data path");
            }

            executorService.submit(() -> {
                try {
                    updateEnableOfExecuteButton(false);
                    politicsRulersService.service(outputDataPath, this::updateStatusModel);
                    showAlert(Alert.AlertType.INFORMATION, "Completed", ButtonType.OK);
                    updateEnableOfExecuteButton(true);
                } catch (Throwable ex) {
                    getLogger(this).error(ex);
                    mainController.addLogToUI(
                            new LogMessageBuilder()
                                    .setLogLevel(Level.ERROR)
                                    .setMessage(ex.getMessage())
                                    .setContextClazz(getClass())
                    );
                    showAlert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                }
            });
        } catch (Throwable ex) {
            getLogger(this).error(ex);
            mainController.addLogToUI(
                    new LogMessageBuilder()
                            .setLogLevel(Level.ERROR)
                            .setMessage(ex.getMessage())
                            .setContextClazz(getClass())
            );
            showAlert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        }
    }

    private void updateStatusModel(StatusModel statusModel) {
        Platform.runLater(() -> {
            lblExecuteStatus.setText(statusModel.getStatusName());
            lblExecuteStatusDetail.setText(statusModel.getStatusDetail());
            mainController.addLogToUI(
                    new LogMessageBuilder()
                            .setLogLevel(Level.INFO)
                            .setMessage(statusModel.getStatusDetail())
                            .setContextClazz(getClass())
            );

            getLogger(this).infoFormat("update status: ", statusModel);
        });
    }

    private void updateEnableOfExecuteButton(boolean enable_) {
        Platform.runLater(() -> {
            btnExecuteOutDataPEP.setDisable(!enable_);

            if (null != mainController) {
                mainController.setEnableMainMenuButtons(enable_);
            }
        });
    }
}
