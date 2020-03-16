package Criminal_Project;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static java.lang.System.*;

public class MyPreloader extends Preloader {

    private Stage PreloaderStage;
    private Scene scene;

    public void MyPreloader(){

    }

    public void init() throws Exception{
        Parent ro = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        scene = new Scene(ro);
    }

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        this.PreloaderStage = PrimaryStage;

        PreloaderStage.setScene(scene);
        PreloaderStage.initStyle(StageStyle.UNDECORATED);
        PreloaderStage.show();
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info){
        if(info instanceof ProgressNotification){
            SplashController.label.setText("Progress..." + ((ProgressNotification) info).getProgress()*100 + "%");
            System.out.println("Value @:" + ((ProgressNotification)info).getProgress());
            SplashController.statProgress.setProgress(((ProgressNotification)info).getProgress());
        }

    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info){
        StateChangeNotification.Type  type = info.getType();

        switch(type){
            case BEFORE_START:
                out.println("Before start");
                PreloaderStage.hide();
                break;

        }
    }

}
