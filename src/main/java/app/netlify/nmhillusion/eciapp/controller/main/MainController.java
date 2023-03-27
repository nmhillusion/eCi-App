package app.netlify.nmhillusion.eciapp.controller.main;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.controller.wanted_people.WantedPeopleController;
import app.netlify.nmhillusion.neon_di.NeonEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.IOException;
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

    public MainController() throws Exception {
        final NeonEngine beanFactoryInstance = Application.getBeanFactoryInstance();
        final Optional<WantedPeopleController> wantedPeopleControllerOpt = beanFactoryInstance.findFirstNeonByClass(WantedPeopleController.class);

        wantedPeopleControllerOpt.ifPresent(peopleController -> wantedPeopleController = peopleController);
    }

    @FXML
    protected void onClickButton__WantedPeople() throws Exception {
        getLogger(this).info("click on button WantedPeople");

        bodyPane.getChildren().add(wantedPeopleController.getMainPane());
    }


    public void onClickButton__PEP(ActionEvent actionEvent) {
        getLogger(this).info("click on button PEP");
    }
}
