package app.netlify.nmhillusion.eciapp.controller.main;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.controller.BaseScreenController;
import app.netlify.nmhillusion.eciapp.controller.pep.PepScreenController;
import app.netlify.nmhillusion.eciapp.controller.wanted_people.WantedPeopleScreenController;
import app.netlify.nmhillusion.neon_di.NeonEngine;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Optional;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-02-25
 * <p>
 * created-by: nmhillusion
 */

public class MainController {
    public StackPane bodyPane;
    private Image alertIcon;
    @FXML
    private Label appTitle;

    private WantedPeopleScreenController wantedPeopleController;
    private PepScreenController pepController;

    public MainController() throws Exception {
        final NeonEngine beanFactoryInstance = Application.getBeanFactoryInstance();
        final Optional<WantedPeopleScreenController> wantedPeopleControllerOpt = beanFactoryInstance.findFirstNeonByClass(WantedPeopleScreenController.class);
        final Optional<PepScreenController> pepControllerOpt = beanFactoryInstance.findFirstNeonByClass(PepScreenController.class);

        wantedPeopleControllerOpt.ifPresent(peopleController -> wantedPeopleController = peopleController);
        pepControllerOpt.ifPresent(pepController_ -> pepController = pepController_);
    }

    private void applyForScreen(BaseScreenController screenController_) throws Exception {
        final ObservableList<Node> paneChildren = bodyPane.getChildren();
        paneChildren.clear();
        final Pane appliedPane = screenController_.getMainPane();
        paneChildren.add(appliedPane);

        screenController_.onApplyPane(appliedPane);
    }

    @FXML
    protected void onClickButton__WantedPeople() throws Exception {
        getLogger(this).info("click on button WantedPeople");

        appTitle.setText("Crawl Wanted People");
        applyForScreen(wantedPeopleController);
    }


    @FXML
    public void onClickButton__PEP(ActionEvent actionEvent) throws Exception {
        getLogger(this).info("click on button PEP");

        appTitle.setText("Crawl Politics Rulers");
        applyForScreen(pepController);
    }
}
