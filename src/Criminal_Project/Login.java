package Criminal_Project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {
LoginController log = new LoginController();
    public void start(Stage LoginStage) throws Exception{
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        LoginStage.setScene(scene);
        LoginStage.setTitle("Criminal and Emergency Alarm Control System");
        LoginStage.initStyle(StageStyle.UNDECORATED);
//        log.closeImage.setStyle("-fx-background-color:  #002346");
        LoginStage.show();

    }

    public static void main(String args[]){
        launch(args);
    }
}
