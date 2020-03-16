package Criminal_Project;

import com.sun.javafx.application.LauncherImpl;
import com.sun.javafx.css.Stylesheet;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Splash  extends Application {

    private final int COUNT_LIMIT=10;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void init() throws Exception{
        for(int i =1; i<= COUNT_LIMIT;i++){
            double progress =(double)i/10;
            System.out.println("Progress: " + progress);
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            Thread.sleep(200);

        }
    }
    public static void main(String[] args) {
        LauncherImpl.launchApplication(Splash.class,MyPreloader.class, args);
    }
}
