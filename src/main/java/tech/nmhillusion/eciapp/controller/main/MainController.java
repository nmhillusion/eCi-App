package tech.nmhillusion.eciapp.controller.main;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.slf4j.event.Level;
import tech.nmhillusion.eciapp.builder.LogMessageBuilder;
import tech.nmhillusion.eciapp.controller.BaseScreenController;
import tech.nmhillusion.eciapp.controller.pep.PepScreenController;
import tech.nmhillusion.eciapp.controller.un_sanction.UNSanctionScreenController;
import tech.nmhillusion.eciapp.controller.wanted_people.WantedPeopleScreenController;
import tech.nmhillusion.neon_di.annotation.Inject;
import tech.nmhillusion.neon_di.annotation.Neon;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-02-25
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class MainController {
    private static final int MAX_LOG_LINES = 20;
    @FXML
    private ListView<Label> logView;
    @FXML
    private StackPane bodyPane;
    @FXML
    private MenuButton mainMenuButtons;
    @FXML
    private Label appTitle;
    @Inject
    private WantedPeopleScreenController wantedPeopleController;
    @Inject
    private PepScreenController pepController;
    @Inject
    private UNSanctionScreenController unSanctionScreenController;


    private void applyForScreen(BaseScreenController screenController_) throws Exception {
        final ObservableList<Node> paneChildren = bodyPane.getChildren();
        paneChildren.clear();
        final Pane appliedPane = screenController_.getMainPane();
        paneChildren.add(appliedPane);

        screenController_.onApplyPane(appliedPane, this);
    }

    @FXML
    protected void onClickButton__WantedPeople() throws Exception {
        getLogger(this).info("click on button WantedPeople");
        addLogToUI(new LogMessageBuilder()
                .setLogLevel(Level.INFO)
                .setMessage("click on button WantedPeople")
                .setContextClazz(getClass())
        );
        appTitle.setText("Crawl Wanted People");
        applyForScreen(wantedPeopleController);
    }


    @FXML
    protected void onClickButton__PEP(ActionEvent actionEvent) throws Exception {
        getLogger(this).info("click on button PEP");
        addLogToUI(new LogMessageBuilder()
                .setLogLevel(Level.INFO)
                .setMessage("click on button PEP")
                .setContextClazz(getClass())
        );
        appTitle.setText("Crawl Politics Rulers");
        applyForScreen(pepController);
    }

    public void setEnableMainMenuButtons(boolean enable_) {
        mainMenuButtons.setDisable(!enable_);
    }

    public void addLogToUI(LogMessageBuilder logMessageBuilder) {
        while (MAX_LOG_LINES < logView.getItems().size()) {
            logView.getItems().remove(logView.getItems().size() - 1);
        }

        logView.getItems().add(0, logMessageBuilder.build());

        logView.setVisible(true);
    }

    public void onClickButton__UN_Sanction(ActionEvent actionEvent) throws Exception {
        getLogger(this).info("click on button UN Sanction");
        addLogToUI(new LogMessageBuilder()
                .setLogLevel(Level.INFO)
                .setMessage("open UN Sanction Screen")
                .setContextClazz(getClass())
        );

        appTitle.setText("Parse UN Sanction List");
        applyForScreen(unSanctionScreenController);
    }
}
