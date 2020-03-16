package Criminal_Project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private ProgressBar ProgressBar;
    public static ProgressBar statProgress;

    @FXML
    private Label lbProgress;
    public static Label label;

    public void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label = lbProgress;
        statProgress = ProgressBar;
    }

}
