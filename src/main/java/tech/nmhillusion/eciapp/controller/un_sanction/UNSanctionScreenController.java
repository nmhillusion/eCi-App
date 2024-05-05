package tech.nmhillusion.eciapp.controller.un_sanction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.slf4j.event.Level;
import tech.nmhillusion.eciapp.Application;
import tech.nmhillusion.eciapp.builder.FxmlLoadBuilder;
import tech.nmhillusion.eciapp.builder.LogMessageBuilder;
import tech.nmhillusion.eciapp.controller.BaseScreenController;
import tech.nmhillusion.eciapp.controller.main.MainController;
import tech.nmhillusion.eciapp.helper.ResourceHelper;
import tech.nmhillusion.neon_di.annotation.Neon;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @FXML
    public void onClickButton__ExecuteOutDataUNSanction(ActionEvent actionEvent) {
        mainController.addLogToUI(
                new LogMessageBuilder()
                        .setLogLevel(Level.INFO)
                        .setMessage("Execute Out Data UNSanction ...")
                        .setContextClazz(getClass())
        );
    }

}
