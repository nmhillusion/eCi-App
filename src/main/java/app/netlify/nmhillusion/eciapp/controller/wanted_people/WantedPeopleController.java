package app.netlify.nmhillusion.eciapp.controller.wanted_people;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.controller.BaseController;
import app.netlify.nmhillusion.eciapp.model.StatusModel;
import app.netlify.nmhillusion.eciapp.service.WantedPeopleService;
import app.netlify.nmhillusion.n2mix.validator.StringValidator;
import app.netlify.nmhillusion.neon_di.annotation.Neon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-02-25
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class WantedPeopleController extends BaseController {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public TextField txtOutDataPath;
    public Label lblExecuteStatus;
    public Label lblExecuteStatusDetail;
    public Button btnExecuteOutDataWantedPeople;
    private WantedPeopleService wantedPeopleService;

    public WantedPeopleController() throws Exception {
        Application.addListenerOnStop(executorService::shutdownNow);
    }

    @Override
    public Pane getMainPane() throws Exception {
        final Optional<WantedPeopleService> wantedPeopleServiceOptional = Application.getBeanFactoryInstance().findFirstNeonByClass(WantedPeopleService.class);
        if (wantedPeopleServiceOptional.isEmpty()) {
            throw new Exception("Cannot find WantedPeopleService");
        }
        wantedPeopleService = wantedPeopleServiceOptional.get();

        final FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("screens/wantedPeopleScreen.fxml"));
        return fxmlLoader.load();
    }

    @FXML
    protected void onClickButton__WantedPeople() {
        updateEnableOfExecuteButton(true);
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
}
