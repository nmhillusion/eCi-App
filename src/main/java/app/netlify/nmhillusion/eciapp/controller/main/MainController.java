package app.netlify.nmhillusion.eciapp.controller.main;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.controller.pep.PepController;
import app.netlify.nmhillusion.eciapp.controller.wanted_people.WantedPeopleController;
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

    private WantedPeopleController wantedPeopleController;
    private PepController pepController;

    public MainController() throws Exception {
        final NeonEngine beanFactoryInstance = Application.getBeanFactoryInstance();
        final Optional<WantedPeopleController> wantedPeopleControllerOpt = beanFactoryInstance.findFirstNeonByClass(WantedPeopleController.class);
        final Optional<PepController> pepControllerOpt = beanFactoryInstance.findFirstNeonByClass(PepController.class);

        wantedPeopleControllerOpt.ifPresent(peopleController -> wantedPeopleController = peopleController);
        pepControllerOpt.ifPresent(pepController_ -> pepController = pepController_);
    }

    private void applyForScreen(Pane screen_) {
        final ObservableList<Node> paneChildren = bodyPane.getChildren();
        paneChildren.clear();
        paneChildren.add(screen_);
    }

    @FXML
    protected void onClickButton__WantedPeople() throws Exception {
        getLogger(this).info("click on button WantedPeople");

        appTitle.setText("Crawl Wanted People");
        applyForScreen(wantedPeopleController.getMainPane());
    }


    @FXML
    public void onClickButton__PEP(ActionEvent actionEvent) throws Exception {
        getLogger(this).info("click on button PEP");

        appTitle.setText("Crawl Politics Rulers");
        applyForScreen(pepController.getMainPane());
    }
}
