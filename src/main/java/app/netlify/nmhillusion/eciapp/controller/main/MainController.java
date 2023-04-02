package app.netlify.nmhillusion.eciapp.controller.main;

import app.netlify.nmhillusion.eciapp.controller.BaseScreenController;
import app.netlify.nmhillusion.eciapp.controller.pep.PepScreenController;
import app.netlify.nmhillusion.eciapp.controller.wanted_people.WantedPeopleScreenController;
import app.netlify.nmhillusion.eciapp.helper.ResourceHelper;
import app.netlify.nmhillusion.neon_di.annotation.Inject;
import app.netlify.nmhillusion.neon_di.annotation.Neon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-02-25
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class MainController implements Initializable {
    @FXML
    public StackPane bodyPane;
    @FXML
    private Label appTitle;

    @Inject
    private WantedPeopleScreenController wantedPeopleController;
    @Inject
    private PepScreenController pepController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (null != bodyPane) {
            final ImageView backgroundImage = new ImageView(
                    new Image(ResourceHelper.loadResourceStream("app-icons/app-background.png"),
                            0,
                            0,
                            true,
                            true
                    )
            );
            backgroundImage.setPreserveRatio(true);
            backgroundImage.setFitWidth(400);
            backgroundImage.fitWidthProperty().bind(((VBox) bodyPane.getParent()).widthProperty());
            bodyPane.getChildren()
                    .add(
                            backgroundImage
                    );
        }
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
