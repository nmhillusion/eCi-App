package app.netlify.nmhillusion.eciapp.controller.pep;

import app.netlify.nmhillusion.eciapp.Application;
import app.netlify.nmhillusion.eciapp.controller.BaseController;
import app.netlify.nmhillusion.eciapp.service.PoliticsRulersService;
import app.netlify.nmhillusion.n2mix.helper.log.LogHelper;
import app.netlify.nmhillusion.neon_di.annotation.Neon;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Optional;

import static app.netlify.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * date: 2023-03-27
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class PepController extends BaseController {
    public Button btnExecuteOutDataPEP;
    public Label lblExecuteStatus;
    public Label lblExecuteStatusDetail;
    public TextField txtOutDataPath;
    private PoliticsRulersService politicsRulersService;

    public PepController() throws IOException {
    }

    @Override
    public Pane getMainPane() throws Exception {
        final Optional<PoliticsRulersService> politicsRulersServiceOptional = Application.getBeanFactoryInstance().findFirstNeonByClass(PoliticsRulersService.class);
        if (politicsRulersServiceOptional.isEmpty()) {
            throw new Exception("Cannot find politicsRulersServiceOptional");
        }
        politicsRulersService = politicsRulersServiceOptional.get();

        final FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("screens/pepScreen.fxml"));
        return fxmlLoader.load();
    }

    public void onClickButton__BrowserOutData(ActionEvent actionEvent) {
        /// TODO: 2023-03-30 impl
        getLogger(this).info("choose output folder");
    }

    public void onClickButton__ExecuteOutDataPEP(ActionEvent actionEvent) {
        /// TODO: 2023-03-30 impl
        getLogger(this).info("on click on PEP execute");
    }
}
