package app.netlify.nmhillusion.eciapp.controller.wanted_people;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.builder.LogMessageBuilder;
import app.netlify.nmhillusion.eciapp.controller.BaseScreenController;
import app.netlify.nmhillusion.eciapp.controller.main.MainController;
import app.netlify.nmhillusion.eciapp.helper.FxmlLoadBuilder;
import app.netlify.nmhillusion.eciapp.helper.ResourceHelper;
import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.service.WantedPeopleService;
import app.netlify.nmhillusion.n2mix.validator.StringValidator;
import app.netlify.nmhillusion.neon_di.annotation.Inject;
import app.netlify.nmhillusion.neon_di.annotation.Neon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.slf4j.event.Level;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-02-25
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class WantedPeopleScreenController extends BaseScreenController {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @FXML
    public TextField txtOutDataPath;
    @FXML
    public Label lblExecuteStatus;
    @FXML
    public Label lblExecuteStatusDetail;
    @FXML
    public Button btnExecuteOutDataWantedPeople;
    @Inject
    private WantedPeopleService wantedPeopleService;
    private MainController mainController;

    public WantedPeopleScreenController() throws Exception {
        Application.addListenerOnStop(executorService::shutdownNow);
    }

    @Override
    public Pane getMainPane() throws Exception {
        return new FxmlLoadBuilder()
                .setFxmlFileURL(ResourceHelper.loadResourceUrl("app-screens/wantedPeopleScreen.fxml"))
                .build();
    }

    @Override
    public void onApplyPane(Pane appliedPane, MainController mainController) throws Exception {
        super.onApplyPane(appliedPane, mainController);

        this.mainController = mainController;
    }

    @FXML
    protected void onClickButton__BrowserOutData(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Set place to put excel file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File(*.xlsx)", "*.xlsx"));
        final File chosenFile = fileChooser.showSaveDialog(null);

        getLogger(this).infoFormat("chosen file: ", chosenFile);

        if (null != chosenFile) {
            txtOutDataPath.setText(chosenFile.getAbsolutePath());
        }
    }

    @FXML
    protected void onClickButton__ExecuteOutDataWantedPeople(ActionEvent actionEvent) {
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

    private void updateEnableOfExecuteButton(boolean enable_) {
        Platform.runLater(() -> {
            btnExecuteOutDataWantedPeople.setDisable(!enable_);

            if (null != mainController) {
                mainController.setEnableMainMenuButtons(enable_);
            }
        });
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
}
