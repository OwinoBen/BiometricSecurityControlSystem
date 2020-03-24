package Criminal_Project;

import SeniorStaff.SeniorStaffController;
import Staff.StaffController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

// instance of loginModel to allow use in controller

    LoginModel model = new LoginModel();

    @FXML
    private TextField txtUsername;

    public static TextField username;

    @FXML
    private PasswordField txtPassword;
    public static PasswordField password;

    @FXML
    private Button btnLogin;

    @FXML
    private ComboBox<option> combobox;
    public static ComboBox<option> comboBoxLogin;
    @FXML
    private Label status;
    @FXML
    private Label loginStatus;



    public void LoginController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBoxLogin=combobox;
        password=txtPassword;
        username=txtUsername;
        /* call isdatabase connected from LoginModel to check if database is connected */

        if (model.isDatabaseConnected()){
            this.status.setText("Connected");
        }
        else {
            this.status.setText("Not Connected");
        }
        /* setting items in the combobox */

        this.combobox.setItems(FXCollections.observableArrayList(option.values()));

    }

    //login methods

    @FXML
    public void Login(){
        try{
            /* calling the isLogin method from the LoginModel class and setting the values from the scene form */
            if (this.combobox.getSelectionModel().isEmpty()){
                this.loginStatus.setText("Please Select the Rank");
                this.loginStatus.setTextFill(Color.RED);
            }
            else {


                if (model.isLogin(this.txtUsername.getText(), this.txtPassword.getText(), ((option) this.combobox.getValue()).toString(),this.txtUsername.getText(),this.txtPassword.getText())) {



                    /* using the login button ID to getScene and window */
                    Stage stage = (Stage) this.btnLogin.getScene().getWindow();
                    stage.close();
                    switch ((this.combobox.getValue()).toString()) {
                        case "officer":
                            staff();
                            break;
                        case "sergent":
                            seniorStaff();
                            break;

                    }
                } else {
                    this.loginStatus.setText("Invalid Username or Password");
                    this.loginStatus.setTextFill(Color.RED);
                }
            }

        }catch (Exception localException){
        localException.printStackTrace();
        }

    }

  public void seniorStaff(){
        try {
            Stage seniorStaff = new Stage();
            FXMLLoader seniorLoader = new FXMLLoader();
            Pane root = (Pane) seniorLoader.load(getClass().getResource("../SeniorStaff/SeniorStaffFXML.fxml"));
            /* create object of staff controller */
            SeniorStaffController SeniorStaffController = (SeniorStaffController)seniorLoader.getController();

            /* setting scene */

            Scene scene = new Scene(root);

            seniorStaff.setScene(scene);
            seniorStaff.setTitle("Dashboard");
            seniorStaff.getIcons().add(new Image("file:icon.jpg"));
            seniorStaff.setResizable(false);

            seniorStaff.show();


        }catch(IOException e){
            e.printStackTrace();
        }

    }
   public void staff(){
        try {
            Stage staffStage = new Stage();
          FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("../Staff/SeniorStaffFXML.fxml"));
            /* create object of staff controller */
            StaffController StaffController = loader.getController();

            /* creating new Scene */
            Scene scene = new Scene(root);
           // Image image = new Image("law.png");
           // staffStage.getIcons().add(image);
            staffStage.setScene(scene);
            staffStage.setTitle("Biometric Security Control System");
            staffStage.setResizable(false);
            staffStage.show();

        }catch(IOException ex){
            ex.printStackTrace();

        }

    }

}
