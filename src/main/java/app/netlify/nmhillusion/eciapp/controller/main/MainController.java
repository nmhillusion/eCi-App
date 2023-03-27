package app.netlify.nmhillusion.eciapp.controller.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

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

    public MainController() throws Exception {
    }

    @FXML
    protected void onClickButton__WantedPeople() {
        getLogger(this).info("click on button WantedPeople");
    }


    public void onClickButton__PEP(ActionEvent actionEvent) {
        getLogger(this).info("click on button PEP");
    }
}
