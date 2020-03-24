package SeniorStaff;

import Staff.LoadData;
import Staff.Offence;
import Staff.Rank;
import Staff.Reset;
import Staff.VictimStatus;
import Staff.caseStatus;
import Staff.genderOption;
import Staff.lawSection;
import Staff.nationalityOption;
import Staff.officer;
import Staff.provinceOption;
import Staff.statusOption;
import com.jfoenix.controls.JFXTimePicker;
import dbUtill.dbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class SeniorStaffController implements Initializable, Runnable {

    @FXML
    private Label labelWelcome, labelRank, labelTime, labelDate, labelNavigation;

    @FXML
    private ImageView imageView, imageViewer;

    @FXML
    private Button btnDashboard, btnChangePassword, btnCriminals, btnAddCriminal, btnOfficer, btnReport, btnHelp, btnProfile;

    @FXML
    private Pane petionerPane, criminalDetailsPane, mainPane, dashBordPane, officerPane, reportPane, crininalsListPane, addCriminalPane, addcriminalMain;

    @FXML
    private TableView<LoadData> tableView;

    @FXML
    private TableColumn<LoadData, String> tableGender, tableStatus, tableCase, tableName, tableCriminal, tableAction;

    @FXML
    private FontAwesomeIconView refersh, search;

    @FXML
    private ComboBox<?> comboClasification;

    @FXML
    private TextField txtCriminalDetailVilage, txtCriminalDetailSublocation, txtSearch, txtCriminalNo, txtCaseNoDisplay, txtStatus, caseStatus, txtCriminalDetailLastName, txtCriminalDetailLastName1;

    @FXML
    private TextField txtCriminal, txtCriminalDetailFirstName, txtCriminalDetailFirstName1, txtCriminalDetailMiddleName, txtCriminalDetailMiddleName1;

    @FXML
    private TextField txtCriminalDetailLocation, txtCriminalDetailWard, txtCriminalDetailConstituency, txtCriminalDetailCounty1, txtCriminalDetailCounty, txtCriminalDetailGender, txtCriminalDetailStatu, txtCriminalDetailProvinc;

    @FXML
    private ComboBox<nationalityOption> comboboxCrinalDetailNationality;

    @FXML
    private ComboBox<statusOption> comboboxCriminalDetailStatus;
    @FXML
    private ComboBox<Rank> ComboBoxOfficerRank;

    @FXML
    private ComboBox<genderOption> comboboxCrinalDetailGender, comboboxCrinalDetailGender1;

    @FXML
    private DatePicker datePickerBirthDte, datePickerBirthDte1;

    @FXML
    private TextField txtCriminalDetailBornIn;

    @FXML
    private ComboBox<provinceOption> comboBoxCriminalDetailCityProvince;

    @FXML
    private TextField txtCriminalDetailPhone1, txtCriminalDetailEmail1, txtCriminalDetailID1, txtCriminalDetailBornIn1, txtCriminalDetailID, txtCriminalDetailEmail, txtCriminalDetailPhone, txtCriminalDetailPostallAddress;
    @FXML
    private TextField txtCriminalDetailZipCode1, txtCriminalDetailPostallAddress1, txtCriminalDetailVilage1, txtCriminalDetailSublocation1, txtCriminalDetailLocation1, txtCriminalDetailZipCode, txtCriminalDetailCity, txtCriminalDetailConstituency1, txtCriminalDetailWard1;

    @FXML
    private ComboBox<provinceOption> comboBoxCriminalDetailCityProvince1;

    @FXML
    private CheckBox FirstfingerRightCheckBox, RightThumbCheckBox, SecondfingerLeftCheckBox, smallfingerLeftCheckBox, ThirdfingerLeftCheckBox, ThirdfingerRightCheckBox, FirstfingerLeftCheckBox, SmallfingerRightCheckBox, SecondfingerRightCheckBox, LeftThumbCheckBox;

    @FXML
    private Button btnEnrollFingerPrint, btncriminaldetailsNext;

    @FXML
    private TextField txtPetitonerLastName;

    @FXML
    private TextField txtPetitonerfirstName;

    @FXML
    private TextField txtPtionerPostal;

    @FXML
    private TextField txtPetionerZipcode;

    @FXML
    private TextField txtPetionerCity;

    @FXML
    private TextField txtPetionerNationalID;

    @FXML
    private TextField txtPettionerID;

    @FXML
    private TextField txtPetionerPhone;

    @FXML
    private ComboBox<genderOption> comboboxPetionerGender;

    @FXML
    private TextField txtPetionerFIR;

    @FXML
    private TextField txtVictimLastName;

    @FXML
    private TextField txtVictimFirstName;

    @FXML
    private TextField txtVictimMddleName;

    @FXML
    private TextField txtVictimPostcode;

    @FXML
    private TextField txtVictimCounty;

    @FXML
    private TextField txtPetionerConstituency;

    @FXML
    private TextField txtVictimZipcode;

    @FXML
    private TextField txtVictimCity;

    @FXML
    private TextField txtPetionerLocation;

    @FXML
    private TextField txtPetitonerMiddleName;

    @FXML
    private Button btnPetionerFinish;

    @FXML
    private Button btnPertionerBack;

    @FXML
    private Button btnPertionerAdd;
    @FXML
    private Pane casePane;

    @FXML
    private TextField txtCaseNo;
    @FXML
    private TextField txtCriminalNo1;

    @FXML
    private ComboBox<VictimStatus> comboboxStatus;

    @FXML
    private ComboBox<caseStatus> comboboxStatus1;

    @FXML
    private ComboBox<Offence> ComboboxOffence;

    @FXML
    private ComboBox<lawSection> comboboxLawSection;

    @FXML
    private TextField txtCaseDetail;

    @FXML
    private ComboBox<officer> comboboxInvestigationOfficer;

    @FXML
    private TextField txtxFIRID;

    @FXML
    private DatePicker datealaodged;

    @FXML
    private JFXTimePicker timeLodged;

    @FXML
    private JFXTimePicker incedentTime;


    @FXML
    private DatePicker incidentDate;

    @FXML
    private TextField incedentPlace;

    @FXML
    private TextField txtCasePetioner;

    @FXML
    private TextArea TextAreaDetail;


    @FXML
    private TextField txtPetionerPhoneNo, txtOfficerEmail;

    @FXML
    private Button btnCaseNext;

    @FXML
    private Button btncriminaldetailsUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnExit;

    @FXML
    private ImageView viewImage;

    @FXML
    private FontAwesomeIconView userphoto;
    @FXML
    private FontAwesomeIconView Logout;
    @FXML
    private Label lbUpload, lbFingerDisplay;

    @FXML
    private Button btnUpload;

    @FXML
    private Pane paneNewPass;

    @FXML
    private Pane paneUpdate;

    @FXML
    private Button btnResetPass;

    @FXML
    private PasswordField txtNewPass;

    @FXML
    private PasswordField txtConfirmPass;

    @FXML
    private Pane paneVerify;

    @FXML
    private TextField txtPaseCode;

    @FXML
    private Button btnVerify;

    @FXML
    private Pane paneSend;

    @FXML
    private Pane changePanel;

    @FXML
    private TextField txtEmailPassword, txtOfficerRank, txtOfficerMiddleName, txtOfficerFirstName, txtOfficerLastName, txtOfficerId, officerSearch;

    @FXML
    private Button btnSend;

    @FXML
    private TableView<Reset> officerTable;

    @FXML
    private TableColumn<Reset, String> colmofficerId;

    @FXML
    private TableColumn<Reset, String> colmOfficerEmail;

    @FXML
    private TableColumn<Reset, String> colmOfficerRank;

    @FXML
    private TableColumn<Reset, String> colmOfficerName;

    @FXML
    private PasswordField txtCofficeronfirmPass, txtOfficerPass;

    @FXML
    private Button btnOfficerExit;
    @FXML
    private ComboBox<String> comboDisplayPorts;
    @FXML
    private Button btnOfficerUpdate;
    @FXML
    private ProgressIndicator rightThumbProgress, firstFingerProgress, secondFingerProgress, thirdFingerProgress, fouthFingerProgress;
    @FXML
    private ProgressIndicator leftThumbProgress, firstLeftFingerProgress, secondLeftFingerProgress, thirdLeftFingerProgress, fourthLeftFingerProgress;

    @FXML
    private Button btnOfficerAdd, btnRefreshScanner;


    PreparedStatement pst = null;
    private dbConnection con;
    private ObservableList<LoadData> data = FXCollections.observableArrayList();
    private ObservableList<Reset> off = FXCollections.observableArrayList();
    private final ObservableList<LoadData> data2 = FXCollections.observableArrayList();

    private File file;
    private FileChooser chooser;

    private String selectedPort = "NULL";
    private SerialPort arduinoPort = new SerialPort(selectedPort);
    private Thread portCloseHook = null;

    private String x;

    private static Thread arduinoThread = null;
    ObservableList<String> portListDisplay;


    Thread t = null;

    public void Controller() {
        arduinoThread = new Thread(this);
        arduinoThread.start();
    }

    // private FileInputStream fis;


    // private  String sq = "SELECT * FROM case";

    String criminalReg;


    public SeniorStaffController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        criminalReg = "";
        this.con = new dbConnection();

        btnOfficerAdd.setVisible(false);
        btnOfficerUpdate.setVisible(false);

        paneUpdate.setVisible(false);

        viewImage.setOnMouseClicked((MouseEvent e) -> {
            btnUpload.setVisible(true);
//            userphoto.setVisible(true);
            lbUpload.setVisible(true);
            viewImage.setVisible(false);


        });

        btnExit.setOnAction(event -> {
            paneUpdate.setVisible(false);
            crininalsListPane.setVisible(true);
        });
        btnOfficerExit.setOnAction(event -> {
            dashBordPane.setVisible(false);
            addCriminalPane.setVisible(false);
            officerPane.setVisible(false);
            crininalsListPane.setVisible(true);
            reportPane.setVisible(false);
            paneUpdate.setVisible(false);
            changePanel.setVisible(false);
            paneUpdate.setVisible(false);


        });

/*
        try{
            Connection conn = new dbConnection().getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);


            while(rs.next()){
                this.data.add(new LoadData(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }


        }catch (SQLException ex){
            System.err.println("Error"+ ex);
        }
*/
//        generateCriminalNumber();
        refreshAvailablePorts();
        LoadTableOfficer();
        LoadTable();
        showTime();
        showDate();
        HideProgressIndicator();

        tableView.setOnMouseClicked(e -> {
            Connection connection = null;

            paneUpdate.setVisible(true);
            crininalsListPane.setVisible(false);

            LoadData loader = new LoadData();

            String age = "SELECT * FROM age WHERE criminal_No = ?";

            String date = "SELECT * FROM residence WHERE criminal_No = ?";

            String Update = "SELECT * FROM criminal WHERE criminal_No = ?";


            try {
                connection = dbConnection.getConnection();

                PreparedStatement update = connection.prepareStatement(Update);
                PreparedStatement dateSave = connection.prepareStatement(date);
                PreparedStatement aged = connection.prepareStatement(age);


                update.setString(1, loader.getCriminalNO());
                dateSave.setString(1, loader.getCriminalNO());
                aged.setString(1, loader.getCriminalNO());
                ResultSet rs = update.executeQuery();
                ResultSet save = dateSave.executeQuery();
                ResultSet ag = aged.executeQuery();

                while (rs.next()) {
                    txtCriminalNo1.setText(rs.getString("criminal_No"));

                    txtCriminalDetailCity.setText(rs.getString("city"));

                    txtCriminalDetailEmail1.setText(rs.getString("email"));
                    txtCriminalDetailFirstName1.setText(rs.getString("first_name"));
                    txtCriminalDetailID1.setText(rs.getString("national_Id"));
                    txtCriminalDetailLastName1.setText(rs.getString("last_name"));

                    txtCriminalDetailMiddleName1.setText(rs.getString("middle_name"));
                    txtCriminalDetailPhone1.setText(rs.getString("phone_No"));
                    txtCriminalDetailPostallAddress1.setText(rs.getString("post_Code"));


                    txtCriminalDetailGender.setText(rs.getString("gender"));
                    txtCriminalDetailStatu.setText(rs.getString("status"));


                    txtCriminalDetailZipCode1.setText(rs.getString("zip_Code"));
                    // txtCriminalDetailNationality.setText(rs.getString("nationality"));

                }

                while (save.next()) {
                    txtCriminalDetailConstituency1.setText(save.getString("constituency"));
                    txtCriminalDetailCounty1.setText(save.getString("county"));
                    txtCriminalDetailLocation1.setText(save.getString("location"));
                    txtCriminalDetailSublocation1.setText(save.getString("sublocation"));
                    txtCriminalDetailWard1.setText(save.getString("ward"));
                }

                while (ag.next()) {
                    txtCriminalDetailBornIn1.setText(ag.getString("criminal_No"));
                    txtCriminalDetailProvinc.setText(ag.getString("criminal_No"));
                    ((TextField) datePickerBirthDte1.getEditor()).setText(ag.getString("birth_Date"));
                    txtCriminalDetailBornIn1.setText(ag.getString("birth_Place"));
                }

                update.close();
                dateSave.close();
                aged.close();
                rs.close();
                save.close();
                ag.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });


        //setting Items to the Comboboxes
        this.ComboBoxOfficerRank.setItems(FXCollections.observableArrayList(Rank.values()));

        this.comboBoxCriminalDetailCityProvince.setItems(FXCollections.observableArrayList(provinceOption.values()));
        this.comboboxCriminalDetailStatus.setItems(FXCollections.observableArrayList(statusOption.values()));
        this.comboboxCrinalDetailGender.setItems(FXCollections.observableArrayList(genderOption.values()));
        this.comboboxCrinalDetailNationality.setItems(FXCollections.observableArrayList(nationalityOption.values()));

        this.comboboxStatus.setItems(FXCollections.observableArrayList(VictimStatus.values()));
        this.comboboxStatus1.setItems(FXCollections.observableArrayList(Staff.caseStatus.values()));
        this.ComboboxOffence.setItems(FXCollections.observableArrayList(Offence.values()));
        this.comboboxLawSection.setItems(FXCollections.observableArrayList(lawSection.values()));
        this.comboboxStatus.setItems(FXCollections.observableArrayList(VictimStatus.values()));
        this.comboboxPetionerGender.setItems(FXCollections.observableArrayList(genderOption.values()));
        this.comboboxInvestigationOfficer.setItems(FXCollections.observableArrayList(officer.values()));


        txtCriminal.setText(txtCriminalDetailFirstName.getText() + " " + txtCriminalDetailLastName.getText());


        FilteredList<LoadData> filterdata = new FilteredList<>(data, e -> true);

        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterdata.setPredicate((Predicate<? super LoadData>) load -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowercaseFilter = newValue.toLowerCase();
                    if (load.getCriminalNO().contains(newValue)) {
                        return true;
                    } else if (load.getName().toLowerCase().contains(lowercaseFilter)) {
                        return true;
                    } else if (load.getStatus().toLowerCase().contains(lowercaseFilter)) {
                        return true;
                    } else if (load.getGender().toLowerCase().contains(lowercaseFilter)) {
                        return true;
                    }

                    return false;
                });


            });
            SortedList<LoadData> sortedData = new SortedList<>(filterdata);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);

        });
        FilteredList<Reset> officerData = new FilteredList<>(off, e -> true);

        officerSearch.setOnKeyReleased(e -> {
            officerSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                officerData.setPredicate((Predicate<? super Reset>) set -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowercaseFilter = newValue.toLowerCase();
                    if (set.getID().contains(newValue)) {
                        return true;
                    } else if (set.getRank().toLowerCase().contains(lowercaseFilter)) {
                        return true;
                    } else if (set.getEmail().contains(lowercaseFilter)) {
                        return true;
                    } else if (set.getName().toLowerCase().contains(lowercaseFilter)) {
                        return true;
                    }

                    return false;
                });


            });

            SortedList<Reset> sorted = new SortedList<>(officerData);
            sorted.comparatorProperty().bind(officerTable.comparatorProperty());
            officerTable.setItems(sorted);

        });


    }

    private void LoadTableOfficer() {
        String sql = "SELECT * FROM officer";

        try {
            Connection con = dbConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {

                Reset reset = new Reset();
                reset.setID(rs.getString("officer_Id"));
                reset.setEmail(rs.getString("email"));
                reset.setRank(rs.getString("rank"));
                reset.setName(rs.getString("last_name"));
                //reset.setStatus(rs.getString("status"));


                off.add(reset);

                this.colmofficerId.setCellValueFactory(new PropertyValueFactory<>("ID"));
                this.colmOfficerEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
                this.colmOfficerRank.setCellValueFactory(new PropertyValueFactory<>("Rank"));
                this.colmOfficerName.setCellValueFactory(new PropertyValueFactory<>("Name"));

                this.officerTable.setItems(this.off);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadTable() {
        //instantiate data

        //sql select string
        String sql = "SELECT * FROM criminal";
        try {
            Connection con = dbConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {

                LoadData load = new LoadData();
                load.setCriminalNO(rs.getString("criminal_No"));
                load.setCaseNO(rs.getString("phone_No"));
                load.setGender(rs.getString("gender"));
                load.setName(rs.getString("last_name"));
                load.setStatus(rs.getString("status"));


                data.add(load);
            }
            //set Property to the table columns
            //NB. use the same property that is in the Load class object
            // this.tableAction.setCellValueFactory(new PropertyValueFactory<>("action"));
            this.tableCriminal.setCellValueFactory(new PropertyValueFactory<>("criminalNO"));
            this.tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.tableGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            this.tableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


            //Creatin a cell factory to add imageView in Ever column

            Callback<TableColumn<LoadData, String>, TableCell<LoadData, String>> cellFactory = param -> {
                //make the cell containing the image view
                final TableCell<LoadData, String> cell = new TableCell<LoadData, String>() {
                    //override the updateItem Method
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        //ensure that cell is created only on a non empty rows
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            //now we create the image view

                            final Button editButton = new Button("Edit");
                            final Button deleteButton = new Button("Delete");
                            editButton.setOnAction(event -> {


//                                LoadData dload = getTableView().getItems().get(getIndex());
//                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                                alert.setContentText("you clicked"+dload.getName()+ "\n crimina id "+dload.getCriminalNO());
//                                alert.show();
                            });
                            setGraphic(editButton);
                            // setGraphic(deleteButton);
                            setText(null);


                            LoadData load = getTableView().getItems().get(getIndex());

                            String Photo = "SELECT * FROM criminal";

                            try {
                                Connection c = dbConnection.getConnection();
                                ResultSet r = c.createStatement().executeQuery("photo");

                                while (r.next()) {
                                    InputStream is = r.getBinaryStream("photo");
                                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                                    byte[] content = new byte[1024];
                                    int size = 0;

                                    while ((size = is.read(content)) != -1) {
                                        os.write(content, 0, size);

                                    }
                                    os.close();
                                    is.close();

                                    Image image = new Image("file: photo.jpg", 100, 150, true, true);
                                    ImageView view = new ImageView(image);

                                    setGraphic(view);
                                    setText(null);
                                }


                            } catch (SQLException e) {

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }


                };

                return cell;
            };


            this.tableAction.setCellFactory(cellFactory);

            //set or add items to the table
            this.tableView.setItems(this.data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Logout(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("../Criminal_Project/Login.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    final void showDate() {
        java.util.Date d = new Date();

        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        labelDate.setText(s.format(d));
    }

    private int minute;
    private int hour;
    private int second;

    final void showTime() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            second = LocalDateTime.now().getSecond();
            minute = LocalDateTime.now().getMinute();
            hour = LocalDateTime.now().getHour();
            labelTime.setText(hour + ":" + (minute) + ":" + second);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDashboard) {
            dashBordPane.setStyle("-fx-background-color: blue");
            dashBordPane.setVisible(true);
            addCriminalPane.setVisible(false);
            officerPane.setVisible(false);
            crininalsListPane.setVisible(false);
            reportPane.setVisible(false);
            paneUpdate.setVisible(false);
            changePanel.setVisible(false);

            //set panel name on the navigation label
            labelNavigation.setText("DashBoard");
        }
        if (actionEvent.getSource() == btnCriminals) {
            dashBordPane.setStyle("-fx-background-color: blue");
            dashBordPane.setVisible(false);
            addCriminalPane.setVisible(false);
            officerPane.setVisible(false);
            crininalsListPane.setVisible(true);
            reportPane.setVisible(false);
            changePanel.setVisible(false);
            paneUpdate.setVisible(false);

            labelNavigation.setText("Criminal List");
        }
        if (actionEvent.getSource() == btnAddCriminal) {

            generateCriminalNumber();
            dashBordPane.setVisible(false);
            addCriminalPane.setVisible(true);
            officerPane.setVisible(false);
            crininalsListPane.setVisible(false);
            reportPane.setVisible(false);

            labelNavigation.setText("Add Criminal");

            criminalDetailsPane.setVisible(true);
            petionerPane.setVisible(false);
            casePane.setVisible(false);
            changePanel.setVisible(false);
            paneUpdate.setVisible(false);

        }
        if (actionEvent.getSource() == btnOfficer) {
            officerPane.setStyle("-fx-background-color: gray");

            dashBordPane.setVisible(false);
            addCriminalPane.setVisible(false);
            officerPane.setVisible(true);
            crininalsListPane.setVisible(false);
            reportPane.setVisible(false);
            changePanel.setVisible(false);
            paneUpdate.setVisible(false);

            labelNavigation.setText("Officers");
        }
        if (actionEvent.getSource().equals(btnChangePassword)) {
            dashBordPane.setVisible(false);
            addCriminalPane.setVisible(false);
            officerPane.setVisible(false);
            crininalsListPane.setVisible(false);
            reportPane.setVisible(false);
            changePanel.setVisible(true);
            paneUpdate.setVisible(false);

            labelNavigation.setText("Change Password");
        }
    }

    @FXML
    public void chooseFile(ActionEvent event) throws MalformedURLException, FileNotFoundException {
        String imageFile;

        btncriminaldetailsUpdate.setVisible(false);

        FileInputStream fis;
        if (event.getSource() == btnUpload) {

            chooser = new FileChooser();
            //Pane pane = new Pane();
            chooser.setTitle("Select Image");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                    "Image files", "*.bmp", "*.png", "*.jpg", "*.gif"));// limit choosing options to image files

            file = chooser.showOpenDialog(viewImage.getScene().getWindow());
            if (file != null) {
                imageFile = file.toURI().toURL().toString();
                Image image = new Image(imageFile);
                viewImage.setImage(image);
                viewImage.setVisible(true);
                btnUpload.setVisible(false);
//                userphoto.setVisible(false);
                lbUpload.setVisible(false);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Please Upload an image to continue");
                alert.showAndWait();
            }

        }
        if (event.getSource() == btncriminaldetailsNext) {

            Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
            Matcher match = p.matcher(txtCriminalDetailEmail.getText());

            String sqlAge = "INSERT INTO age(criminal_No,birth_Date,birth_Place,province) VALUES(?,?,?,?)";

            String sqlResidence = "INSERT INTO residence(county,constituency,ward,location,sublocation,criminal_No) VALUES(?,?,?,?,?,?)";

            String sqlInsert = "INSERT INTO criminal(criminal_No,first_name,middle_name,last_name,nationality,post_Code," +
                    "zip_Code,city,gender,email,birth_Date,national_Id,phone_No,photo,status" +
                    ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sqlInsert7 = "INSERT INTO `criminal`(`criminal_No`, `first_name`, `middle_name`, `last_name`, `nationality`, `post_Code`, `zip_Code`, `city`, `gender`, `email`, `birth_Date`, `national_Id`, `phone_No`, `photo`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            if (txtCriminalNo.getText().isEmpty() || txtCriminalDetailFirstName.getText().isEmpty() || txtCriminalDetailMiddleName.getText().isEmpty() || txtCriminalDetailLastName.getText().isEmpty() || comboboxCrinalDetailNationality.getSelectionModel().isEmpty()
                    || txtCriminalDetailPostallAddress.getText().isEmpty() || txtCriminalDetailZipCode.getText().isEmpty() || txtCriminalDetailCity.getText().isEmpty()
                    || comboboxCrinalDetailGender.getSelectionModel().isEmpty() || txtCriminalDetailEmail.getText().isEmpty() || datePickerBirthDte.getEditor().getText().isEmpty() || txtCriminalDetailID.getText().isEmpty()
                    || txtCriminalDetailPhone.getText().isEmpty() || comboboxCriminalDetailStatus.getSelectionModel().isEmpty() || txtCriminalDetailCounty.getText().isEmpty() || txtCriminalDetailConstituency.getText().isEmpty() || txtCriminalDetailWard.getText().isEmpty()
                    || txtCriminalDetailLocation.getText().isEmpty() || txtCriminalDetailSublocation.getText().isEmpty() || txtCriminalDetailBornIn.getText().isEmpty() || comboBoxCriminalDetailCityProvince.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill all the Fields");
                alert.showAndWait();

            } else {
                if (viewImage.getImage() == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Please Upload the Image FIle");
                    alert.showAndWait();

                } else if (validateEmail()) {

                } else {

                    Pattern pater = Pattern.compile("[A-Z]+"); // Name fields validation Pattern
                    Matcher fname = pater.matcher(txtCriminalDetailFirstName.getText());
                    Matcher lname = pater.matcher(txtCriminalDetailLastName.getText());
                    Matcher mname = pater.matcher(txtCriminalDetailMiddleName.getText());

                    Matcher county = pater.matcher(txtCriminalDetailCounty.getText());
                    Matcher constituency = pater.matcher(txtCriminalDetailConstituency.getText());
                    Matcher ward = pater.matcher(txtCriminalDetailWard.getText());
                    Matcher location = pater.matcher(txtCriminalDetailLocation.getText());
                    Matcher subloction = pater.matcher(txtCriminalDetailSublocation.getText());
                    Matcher born = pater.matcher(txtCriminalDetailBornIn.getText());

                    Pattern PhoneValidation = Pattern.compile("[0][0-9]{9}"); //phone number validation pattern "(0|254)?[7][0-9]{9}"
                    Matcher phone = PhoneValidation.matcher(txtCriminalDetailPhone.getText());

                    Pattern idValidate = Pattern.compile("[0-9]{8}");
                    Matcher idMatcher = idValidate.matcher(txtCriminalDetailID.getText());

                    Pattern p6 = Pattern.compile("[0-9]+"); // number fields validation pattern
                    Matcher matcher = p6.matcher(txtCriminalDetailID.getText());
                    Matcher matcher2 = p6.matcher(txtCriminalDetailPostallAddress.getText());
                    Matcher matcher3 = p6.matcher(txtCriminalDetailZipCode.getText());


                    if (!(phone.find() && phone.group().equals(txtCriminalDetailPhone.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Invalid Phone Number");
                        alert.setTitle("BCAEACS");
                        alert.showAndWait();

                    } else if (!(idMatcher.find() && idMatcher.group().equals(txtCriminalDetailID.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("ID Number Should be 8 digits ");
                        alert.setTitle("BCAEACS");
                        alert.showAndWait();

                    } else if (!(matcher2.find() && matcher2.group().equals(txtCriminalDetailPostallAddress.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Wrong Postal Address");
                        alert.setTitle("BCAEACS");
                        alert.showAndWait();

                    } else if (!(matcher3.find() && matcher3.group().equals(txtCriminalDetailZipCode.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Zip Code Should be a number value");
                        alert.setTitle("BCAEACS");
                        alert.showAndWait();

                    } else if (!(fname.find() && fname.group().equals(txtCriminalDetailFirstName.getText()))
                            || !(lname.find() && lname.group().equals(txtCriminalDetailLastName.getText()))
                            || !(mname.find() && mname.group().equals(txtCriminalDetailMiddleName.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("BCAEACS");
                        alert.setContentText("Names should contain Letters and in Uppercase");
                        alert.showAndWait();
                    } else if (!(county.find() && county.group().equals(txtCriminalDetailCounty.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("BCAEACS");
                        alert.setContentText("County should contain Letters and in Uppercase");
                        alert.showAndWait();
                    } else if (!(constituency.find() && constituency.group().equals(txtCriminalDetailConstituency.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("BCAEACS");
                        alert.setContentText("Constituency should contain Letters and in Uppercase");
                        alert.showAndWait();
                    } else if (!(ward.find() && ward.group().equals(txtCriminalDetailWard.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("BCAEACS");
                        alert.setContentText("Ward should contain Letters and in Uppercase");
                        alert.showAndWait();
                    } else if (!(location.find() && location.group().equals(txtCriminalDetailLocation.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("BCAEACS");
                        alert.setContentText("Location should contain Letters and in Uppercase");
                        alert.showAndWait();
                    } else if (!(subloction.find() && subloction.group().equals(txtCriminalDetailSublocation.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("BCAEACS");
                        alert.setContentText("Subloction should contain Letters and in Uppercase");
                        alert.showAndWait();
                    } else if (!(born.find() && born.group().equals(txtCriminalDetailBornIn.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("BCAEACS");
                        alert.setContentText("Invalid Place of Birth\n should be in Uppercase");
                        alert.showAndWait();
                    } else {
                        try {
                            Connection conn = dbConnection.getConnection();

                            String sqlSearch = "SELECT criminal_No FROM criminal ORDER BY criminal_No DESC";

                            Statement st = conn.createStatement();
                            ResultSet rs = st.executeQuery(sqlSearch);

                            if (rs.next()) {
                                String rl;
                                rl = rs.getString("criminal_No");

                                if (rl.equals(txtCriminalNo.getText())) {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suspect Already Exist in the System");
                                    alert.setTitle("BCEACS");
                                    alert.showAndWait();

                                } else {

                                    PreparedStatement pst2 = conn.prepareStatement(sqlResidence);//Residence statement
                                    PreparedStatement pst3 = conn.prepareStatement(sqlAge);//Age preparedStement object
                                    pst = conn.prepareStatement(sqlInsert); // Criminal data PreparedStatement object

                                    //Saving data in criminal table in the database

                                    pst.setString(1, txtCriminalNo.getText());
                                    pst.setString(2, txtCriminalDetailFirstName.getText());
                                    pst.setString(3, txtCriminalDetailMiddleName.getText());
                                    pst.setString(4, txtCriminalDetailLastName.getText());
                                    pst.setString(5, comboboxCrinalDetailNationality.getSelectionModel().getSelectedItem().value());
                                    pst.setString(6, txtCriminalDetailPostallAddress.getText());
                                    pst.setString(7, txtCriminalDetailZipCode.getText());
                                    pst.setString(8, txtCriminalDetailCity.getText());
                                    pst.setString(9, comboboxCrinalDetailGender.getSelectionModel().getSelectedItem().value());
                                    pst.setString(10, txtCriminalDetailEmail.getText());
                                    pst.setString(11, datePickerBirthDte.getEditor().getText());
                                    pst.setString(12, txtCriminalDetailID.getText());
                                    pst.setString(13, txtCriminalDetailPhone.getText());

                                    fis = new FileInputStream(file);
                                    pst.setBinaryStream(14, fis, (int) file.length());
                                    pst.setString(15, comboboxCriminalDetailStatus.getSelectionModel().getSelectedItem().value());


                                    //Storing data in the Residence table in the database

                                    pst2.setString(1, txtCriminalDetailCounty.getText());
                                    pst2.setString(2, txtCriminalDetailConstituency.getText());
                                    pst2.setString(3, txtCriminalDetailWard.getText());
                                    pst2.setString(4, txtCriminalDetailLocation.getText());
                                    pst2.setString(5, txtCriminalDetailSublocation.getText());
                                    pst2.setString(6, txtCriminalNo.getText());


                                    //Storing data in the Age table in the database;

                                    pst3.setString(1, txtCriminalNo.getText());
                                    pst3.setString(2, datePickerBirthDte.getEditor().getText());
                                    pst3.setString(3, txtCriminalDetailBornIn.getText());
                                    pst3.setString(4, comboBoxCriminalDetailCityProvince.getSelectionModel().getSelectedItem().value());

                                    int save = pst.executeUpdate();
                                    if (save > 0) {
                                        criminalDetailsPane.setVisible(false);
                                        petionerPane.setVisible(false);
                                        casePane.setVisible(true);
                                    } else {
                                        Alert alerter = new Alert(Alert.AlertType.INFORMATION);
                                        alerter.setHeaderText("FAILED PLEASE TRY AGAIN");
                                        alerter.showAndWait();
                                    }
                                    pst2.executeUpdate();
                                    pst3.executeUpdate();

                                    pst.close();
                                    pst2.close();
                                    pst3.close();
                                    conn.close();

                                    txtCriminal.setText(txtCriminalDetailFirstName.getText() + " " + txtCriminalDetailLastName.getText());
                                    txtCriminal.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }


            }


        }
        if (event.getSource() == btnCaseNext) {

            if (txtVictimLastName.getText().isEmpty() || txtVictimFirstName.getText().isEmpty() || txtVictimMddleName.getText().isEmpty() || txtVictimPostcode.getText().isEmpty() || txtVictimZipcode.getText().isEmpty()
                    || txtVictimCity.getText().isEmpty() || txtVictimCounty.getText().isEmpty() || txtPetionerConstituency.getText().isEmpty()
                    || txtxFIRID.getText().isEmpty() || datealaodged.getEditor().getText().isEmpty() /*|| timeLodged.getEditor().getText().isEmpty() || incedentTime.getEditor().getText().isEmpty()*/
                    || incidentDate.getEditor().getText().isEmpty() || incedentPlace.getText().isEmpty() || txtPetionerPhoneNo.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill all the Fields");
                alert.showAndWait();

            } else {

                Pattern pater = Pattern.compile("[A-Za-z]+"); // Name fields validation Pattern
                Matcher pertionerFname = pater.matcher(txtVictimFirstName.getText());
                Matcher pertionerMname = pater.matcher(txtVictimMddleName.getText());
                Matcher pertionerLname = pater.matcher(txtVictimLastName.getText());

                Matcher petionercounty = pater.matcher(txtVictimCounty.getText());
                Matcher petionerconstituency = pater.matcher(txtPetionerConstituency.getText());

                Matcher petionerlocation = pater.matcher(txtPetionerLocation.getText());
                Matcher petionerCity = pater.matcher(txtVictimCity.getText());
                Matcher incidencePlace = pater.matcher(incedentPlace.getText());

                Pattern p6 = Pattern.compile("[0-9]+");
                Matcher petionerPostcode = p6.matcher(txtVictimPostcode.getText());
                Matcher petionerzipcode = p6.matcher(txtVictimZipcode.getText());

                Pattern petioner = Pattern.compile("[0-9]{8}");
                Matcher petionerId = petioner.matcher(txtPetionerNationalID.getText());

                Pattern PhoneValidation = Pattern.compile("[0][0-9]{9}"); //phone number validation pattern "(0|254)?[7][0-9]{9}"
                Matcher petphone = PhoneValidation.matcher(txtPetionerPhoneNo.getText());

                if (!(pertionerFname.find() && pertionerFname.group().equals(txtVictimFirstName.getText()))
                        || !(pertionerLname.find() && pertionerLname.group().equals(txtVictimLastName.getText()))
                        || !(pertionerMname.find() && pertionerMname.group().equals(txtVictimMddleName.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Names Must Conatin Letters only");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petionercounty.find() && petionercounty.group().equals(txtVictimCounty.getText()))
                        || !(petionerconstituency.find() && petionerconstituency.group().equals(txtPetionerConstituency.getText()))
                        || !(petionerlocation.find() && petionerlocation.group().equals(txtPetionerLocation.getText()))
                        || !(petionerCity.find() && petionerCity.group().equals(txtVictimCity.getText()))
                        || !(incidencePlace.find() && incidencePlace.group().equals(incedentPlace.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Ensure that fields contain letters only");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petionerId.find() && petionerId.group().equals(txtPetionerNationalID.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("National Id should not be less than \n or more than 8 digits");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petphone.find() && petphone.group().equals(txtPetionerPhoneNo.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Phone Number");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petionerPostcode.find() && petionerPostcode.group().equals(txtVictimPostcode.getText()))
                        || !(petionerzipcode.find() && petionerzipcode.group().equals(txtVictimZipcode.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("PostCode and ZipCode Fields Should Contain Numbers");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else {
                    try {

                        Connection conn = dbConnection.getConnection();

//                        String sqlpetioner = "INSERT INTO witness(national_Id,first_name,middle_name,last_name,phone,postaCode,zip_Code,city,County,Constituency,Location) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

                        String sqlpetioner = "INSERT INTO `witness`(`national_Id`, `first_name`, `middle_name`, `last_name`, `phone`, `postaCode`, `zip_Code`, `city`) VALUES (?,?,?,?,?,?,?,?)";
                        String sqlfir ="INSERT INTO `fir`(`FIR_Id`, `incedent_Date`, `incident_Time`, `incident_Place`, `time_Logged`, `date_Logged`, `detail`, `witness_Id`, `criminal_No`) VALUES (?,?,?,?,?,?,?,?,?)";
                        String sqlPetionerResidence  = "INSERT INTO `petitionerresidence`(`national_id`, `county`, `constituency`, `location`) VALUES (?,?,?,?)";



                        String sqlSearch = "SELECT FIR_Id FROM fir ORDER BY FIR_Id DESC";

                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(sqlSearch);

                        if (rs.next()) {
                            String rl;
                            rl = rs.getString("FIR_Id");

                            if (rl.equals(txtxFIRID.getText())) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("FIR Already Exist in the System");
                                alert.setTitle("BCEACS");
                                alert.showAndWait();
                            }else{
                                PreparedStatement pst2 = conn.prepareStatement(sqlpetioner);//Residence statement
                                PreparedStatement pst3 = conn.prepareStatement(sqlfir);//FIR preparedStement object
                                PreparedStatement pst4 = conn.prepareStatement(sqlPetionerResidence);

                                pst2.setString(1, txtPetionerNationalID.getText());
                                pst2.setString(2, txtVictimFirstName.getText());
                                pst2.setString(3, txtVictimMddleName.getText());
                                pst2.setString(4, txtVictimLastName.getText());
                                pst2.setString(5, txtPetionerPhoneNo.getText());
                                pst2.setString(6, txtVictimPostcode.getText());
                                pst2.setString(7, txtVictimZipcode.getText());
                                pst2.setString(8, txtVictimCity.getText());

                                //saving data in pettionerResidence table
                                pst4.setString(1, txtPetionerNationalID.getText());
                                pst4.setString(2, txtVictimCounty.getText());
                                pst4.setString(3, txtPetionerConstituency.getText());
                                pst4.setString(4, txtPetionerLocation.getText());

                                //Saving data in FIR  table in the database
                                pst3.setString(1, txtxFIRID.getText());
                                pst3.setString(2, incidentDate.getEditor().getText());
//                        pst3.setString(3, incedentTime.getEditor().getText());
                                pst3.setString(3, labelDate.getText());
                                pst3.setString(4, incedentPlace.getText());
//                        pst3.setString(5, timeLodged.getEditor().getText());
                                pst3.setString(5, labelTime.getText());
                                pst3.setString(6, datealaodged.getEditor().getText());
                                pst3.setString(7, TextAreaDetail.getText());
                                pst3.setString(8, txtPetionerNationalID.getText());
                                pst3.setString(9, txtCriminalNo.getText());

                                pst2.executeUpdate();
                                pst3.executeUpdate();
                                pst4.executeUpdate();


                                int save = pst3.executeUpdate();
                                if (save > 0) {
                                    criminalDetailsPane.setVisible(false);
                                    petionerPane.setVisible(false);
                                    casePane.setVisible(true);
                                } else {
                                    Alert alerter = new Alert(Alert.AlertType.INFORMATION);
                                    alerter.setHeaderText("FAILED PLEASE TRY AGAIN");
                                    alerter.showAndWait();
                                }

                                pst2.close();
                                pst3.close();
                                conn.close();

                            }
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        if (event.getSource() == btnPetionerFinish) {

            if (txtPettionerID.getText().isEmpty() || txtPetitonerfirstName.getText().isEmpty() || txtPetitonerMiddleName.getText().isEmpty() || txtPetitonerLastName.getText().isEmpty() || txtPtionerPostal.getText().isEmpty()
                    || txtPetionerZipcode.getText().isEmpty() || txtPetionerCity.getText().isEmpty() || comboboxPetionerGender.getSelectionModel().isEmpty() || txtPetionerPhone.getText().isEmpty() || comboboxStatus.getSelectionModel().isEmpty()
                    || comboboxLawSection.getSelectionModel().isEmpty() || comboboxStatus1.getSelectionModel().isEmpty() || ComboboxOffence.getSelectionModel().isEmpty() || txtCaseDetail.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill all the Fields");
                alert.showAndWait();


            } else {

                Pattern pater = Pattern.compile("[A-Za-z]+"); // Name fields validation Pattern
                Matcher pertionerFname = pater.matcher(txtPetitonerfirstName.getText());
                Matcher pertionerMname = pater.matcher(txtPetitonerMiddleName.getText());
                Matcher pertionerLname = pater.matcher(txtPetitonerLastName.getText());

                Matcher petionerCity = pater.matcher(txtPetionerCity.getText());
                Matcher caseDetail = pater.matcher(txtCaseDetail.getText());

                Pattern p6 = Pattern.compile("[0-9]+");
                Matcher petionerPostcode = p6.matcher(txtPtionerPostal.getText());
                Matcher petionerzipcode = p6.matcher(txtPetionerZipcode.getText());

                Pattern petioner = Pattern.compile("[0-9]{8}");
                Matcher petionerId = petioner.matcher(txtPettionerID.getText());

                Pattern PhoneValidation = Pattern.compile("[0][0-9]{9}"); //phone number validation pattern "(0|254)?[7][0-9]{9}"
                Matcher petphone = PhoneValidation.matcher(txtPetionerPhone.getText());

                if (!(pertionerFname.find() && pertionerFname.group().equals(txtPetitonerfirstName.getText()))
                        || !(pertionerLname.find() && pertionerLname.group().equals(txtPetitonerLastName.getText()))
                        || !(pertionerMname.find() && pertionerMname.group().equals(txtPetitonerMiddleName.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Names Must Conatin Letters only");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petionerCity.find() && petionerCity.group().equals(txtPetionerCity.getText()))
                        || !(caseDetail.find() && caseDetail.group().equals(txtCaseDetail.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Ensure that fields contain letters only");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petionerId.find() && petionerId.group().equals(txtPettionerID.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("National Id should not be less than \n or more than 8 digits");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petphone.find() && petphone.group().equals(txtPetionerPhone.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Phone Number");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petionerPostcode.find() && petionerPostcode.group().equals(txtPtionerPostal.getText()))
                        || !(petionerzipcode.find() && petionerzipcode.group().equals(txtPetionerZipcode.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("PostCode and ZipCode Fields Should Contain Numbers");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else {
                    try {

                        String sqlcase = "INSERT INTO case(case_Id,law_section,case_status,case_detail,officer_Id,FIR_Id)" +
                                " values(?,?,?,?,?,?)";

                        String sqlVictim = "INSERT INTO victim(national_Id,firstname,Middlename,lastname,post_Code,zip_Code,city,FIR_Id,status)" +
                                " values(?,?,?,?,?,?,?,?,?)";

                        Connection conn = dbConnection.getConnection();

                        PreparedStatement pst2 = conn.prepareStatement(sqlVictim);//Residence statement
                        PreparedStatement pst3 = conn.prepareStatement(sqlcase);//Age preparedStement object

                        pst2.setString(1, txtPettionerID.getText());
                        pst2.setString(2, txtPetitonerfirstName.getText());
                        pst2.setString(3, txtPetitonerMiddleName.getText());
                        pst2.setString(4, txtPetitonerLastName.getText());
                        pst2.setString(5, txtPtionerPostal.getText());
                        pst2.setString(6, txtPetionerZipcode.getText());
                        pst2.setString(7, txtPetionerCity.getText());
                        pst2.setString(8, txtPetionerFIR.getText());
                        pst2.setString(9, comboboxStatus.getSelectionModel().getSelectedItem().value());


                        //Saving data in Case table in the database
                        pst3.setString(1, txtCaseNo.getText());
                        pst3.setString(2, comboboxLawSection.getSelectionModel().getSelectedItem().value());
                        pst3.setString(3, comboboxStatus1.getSelectionModel().getSelectedItem().value());
                        pst3.setString(4, txtCaseDetail.getText());
                        pst3.setString(5, comboboxInvestigationOfficer.getSelectionModel().getSelectedItem().toString());
                        pst3.setString(6, txtPetionerFIR.getText());

                        pst2.executeUpdate();
                        int save = pst3.executeUpdate();

                        if (save > 0) {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Suspect Successfully Add");
                            alert.showAndWait();
                            criminalDetailsPane.setVisible(false);
                            petionerPane.setVisible(false);
                            casePane.setVisible(true);
                        } else {
                            Alert alerter = new Alert(Alert.AlertType.INFORMATION);
                            alerter.setHeaderText("FAILED PLEASE TRY AGAIN");
                            alerter.showAndWait();
                        }

                        pst2.close();
                        pst3.close();
                        conn.close();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }


            }

        }
        if (event.getSource() == btnPertionerBack) {
            criminalDetailsPane.setVisible(false);
            petionerPane.setVisible(false);
            casePane.setVisible(true);
        }

        if (event.getSource() == btnOfficerAdd) {

            Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
            Matcher matc = p.matcher(txtOfficerEmail.getText());

            if (txtOfficerLastName.getText().isEmpty() || txtOfficerFirstName.getText().isEmpty() || txtOfficerMiddleName.getText().isEmpty() || txtOfficerId.getText().isEmpty()
                    || txtOfficerRank.getText().isEmpty() || txtOfficerRank.getText().isEmpty() || ComboBoxOfficerRank.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill all the Fields");
                alert.showAndWait();
            } else if (!(matc.find() && matc.group().equals(txtOfficerEmail.getText()))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Invalid Email");
                alert.setTitle("BCAEACS");
                alert.showAndWait();
            } else {


                Pattern pater = Pattern.compile("[A-Za-z]+"); // Name fields validation Pattern
                Matcher pertionerFname = pater.matcher(txtOfficerFirstName.getText());
                Matcher pertionerMname = pater.matcher(txtOfficerMiddleName.getText());
                Matcher pertionerLname = pater.matcher(txtOfficerLastName.getText());

                Pattern petioner = Pattern.compile("[0-9]{8}");
                Matcher petionerId = petioner.matcher(txtOfficerId.getText());

                Pattern PhoneValidation = Pattern.compile("[0][0-9]{9}"); //phone number validation pattern "(0|254)?[7][0-9]{9}"
                Matcher petphone = PhoneValidation.matcher(txtOfficerRank.getText());

                if (!(pertionerFname.find() && pertionerFname.group().equals(txtOfficerFirstName.getText()))
                        || !(pertionerLname.find() && pertionerLname.group().equals(txtOfficerLastName.getText()))
                        || !(pertionerMname.find() && pertionerMname.group().equals(txtOfficerMiddleName.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Names Must Conatin Letters only");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petionerId.find() && petionerId.group().equals(txtOfficerId.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("National Id should not be less than \n or more than 8 digits");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(petphone.find() && petphone.group().equals(txtOfficerRank.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Phone Number");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else if (!(txtOfficerPass.getText().contentEquals(txtCofficeronfirmPass.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("The Two passwords do not match");
                    alert.setTitle("BCAEACS");
                    alert.showAndWait();

                } else {
                    try {

                        String sqlcase = "INSERT INTO officer(officer_Id,first_name,middle_name,last_name,email,rank,phone,passsword)" +
                                " values(?,?,?,?,?,?,?,?)";

                        Connection conn = dbConnection.getConnection();
                        PreparedStatement pst3 = conn.prepareStatement(sqlcase);//Age preparedStement object

                        //Saving data in Officer table in the database
                        pst3.setString(1, txtOfficerId.getText());
                        pst3.setString(2, txtOfficerFirstName.getText());
                        pst3.setString(3, txtOfficerMiddleName.getText());
                        pst3.setString(4, txtOfficerLastName.getText());
                        pst3.setString(5, txtOfficerEmail.getText());
                        pst3.setString(6, ComboBoxOfficerRank.getSelectionModel().getSelectedItem().toString());
                        pst3.setString(7, txtOfficerRank.getText());
                        pst3.setString(8, txtOfficerPass.getText());


                        int save = pst3.executeUpdate();

                        if (save > 0) {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Officer Successfully Add");
                            alert.showAndWait();
                            criminalDetailsPane.setVisible(false);
                            petionerPane.setVisible(false);
                            casePane.setVisible(true);
                        } else {
                            Alert alerter = new Alert(Alert.AlertType.INFORMATION);
                            alerter.setHeaderText("FAILED PLEASE TRY AGAIN");
                            alerter.showAndWait();
                        }


                        pst3.close();
                        conn.close();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

//Validating email field

    @FXML
    public boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");// email validation pattern
        Matcher match = p.matcher(txtCriminalDetailEmail.getText());


        if (!(match.find() && match.group().equals(txtCriminalDetailEmail.getText()))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid Email");
            alert.setTitle("BCAEACS");
            alert.showAndWait();
            return true;
        }

        return false;
    }

    @FXML
    public void PasswordChange(ActionEvent event) {
        int randomCode;
        Random rand = new Random();
        randomCode = rand.nextInt(999999);

        if (event.getSource() == btnSend) {
            try {
                String sql = "SELECT email FROM officer";
                Connection cn = dbConnection.getConnection();
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    String email = rs.getString("email");

                    if (!(email.equals(txtEmailPassword.getText()))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText(null);
                        alert.setContentText("Email does not exist in the System");
                        alert.showAndWait();

                    } else {
                        try {
                            String host = "smtp.gmail.com";
                            String user = "bensonowinoopondo@gmail.com";
                            String pass = "0790232329";
                            String to = txtEmailPassword.getText();
                            String subject = "Reseting Code";
                            String message = "Your reset code is" + randomCode;
                            boolean sessionDebug = false;
                            Properties pros = System.getProperties();
                            pros.put("mail.smtp.starttls.enable", "true");
                            pros.put("mail.smtp.host", "host");
                            pros.put("mail.smtp.port", "587");
                            pros.put("mail.smtp.auth", "true");
                            pros.put("mail.smtp.starttls.required", "true");
                            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//                            Session mailSession = Session.getDefaultInstance(pros, null);
//                            mailSession.setDebug(sessionDebug);
//                            Message msg = new MimeMessage(mailSession);
//                            msg.setFrom(new InternetAddress(user));
//                            InternetAddress[] address = {new InternetAddress(to)};
//                            msg.setRecipients(Message.RecipientType.TO, address);
//                            msg.setSubject(subject);
//                            msg.setText(message);
//                            Transport transport = mailSession.getTransport("smtp");
//                            transport.connect(host, user, pass);
//                            transport.sendMessage(msg, msg.getAllRecipients());
//                            transport.close();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("code has been sent to the email");
                            alert.showAndWait();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }
                    }
                }


            } catch (SQLException ex) {
                ex.printStackTrace();
            }


            if (event.getSource() == btnVerify) {
                if (Integer.valueOf(txtPaseCode.getText()) == randomCode) {
                    paneNewPass.setVisible(true);
                    paneSend.setVisible(false);
                    paneVerify.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Code do not much");
                }
            }

            if (event.getSource() == btnResetPass) {
                if (txtNewPass.getText().equals(txtConfirmPass.getText())) {
                    try {
                        String updateQuery = "UPDATE officer SET passsword=? WHERE email=" + txtEmailPassword.getText() + "";
                        Connection con = dbConnection.getConnection();
                        pst = con.prepareStatement(updateQuery);
                        pst.setString(1, txtNewPass.getText());
                        pst.executeUpdate();

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Password do not match");
                }
            }

        }
    }

    @FXML
    public void chooseImage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");

        //set to user's directory pr got to default directory if can't access
        String userDirectoryString = System.getProperty("user.home" + "\\pictures");
        File userDirectory = new File(userDirectoryString);
        if (!userDirectory.canRead()) {
            userDirectory = new File("c:/");

        }
        fileChooser.setInitialDirectory(userDirectory);
        File filePath = fileChooser.showOpenDialog(stage);
        try {
            BufferedImage bufferedImage = ImageIO.read(filePath);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageViewer.setImage(image);

        } catch (IOException e) {
            System.err.println(e.getMessage());

        }
    }

    @FXML
    public void PageScroller(ActionEvent event) {
        if (event.getSource() == btncriminaldetailsNext) {
            criminalDetailsPane.setVisible(false);
            petionerPane.setVisible(true);
            casePane.setVisible(false);
        }

        if (event.getSource() == btnPetionerFinish) {
            criminalDetailsPane.setVisible(false);
            petionerPane.setVisible(false);
            casePane.setVisible(true);
        }
        if (event.getSource() == btnPertionerBack) {
            criminalDetailsPane.setVisible(false);
            petionerPane.setVisible(false);
            casePane.setVisible(true);
        }

        if (event.getSource() == btnCaseNext) {

            dashBordPane.setVisible(false);
            addCriminalPane.setVisible(false);
            officerPane.setVisible(false);
            crininalsListPane.setVisible(true);
            reportPane.setVisible(false);

            labelNavigation.setText("Criminal List");
        }
        if (event.getSource() == btnBack) {
            criminalDetailsPane.setVisible(false);
            petionerPane.setVisible(true);
            casePane.setVisible(false);
        }
    }


    public void generateCriminalNumber() {

        try {
            Connection cn = dbConnection.getConnection();

//            String sql5 = "SELECT case_Id FROM case ORDER BY case_Id DESC LIMIT 1";
            String sql5 = "SELECT `case_Id` FROM `case` ORDER BY case_Id DESC LIMIT 1";

            String sql3 = "SELECT FIR_Id FROM fir ORDER BY FIR_Id DESC LIMIT 1";

            String sql2 = "SELECT criminal_No FROM criminal ORDER BY criminal_No DESC";

            Statement st = cn.createStatement();
            PreparedStatement pt = cn.prepareStatement(sql3);
            PreparedStatement pst = cn.prepareStatement(sql5);
            ResultSet ptr = pst.executeQuery();
            ResultSet rt = pt.executeQuery();
            ResultSet rs = st.executeQuery(sql2);

            String s = "FIR101";
            String txt = "SUS101";
            String caseno = "KCSD101";

            if (rs.next()) {
                String rl;
                rl = rs.getString("criminal_No");
                int t = rl.length();
                String j = rl.substring(0, 3);
                String crimNo = rl.substring(3, t);
                int n = Integer.parseInt(crimNo);
                n++;
                crimNo = Integer.toString(n);
                txtCriminalNo.setText(j + crimNo);
                txtCriminalNo.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

            } else {
                txtCriminalNo.setText(txt);
            }

            if (rt.next()) {
                String rd = rt.getString("FIR_Id");
                int d = rd.length();
                String fir = rd.substring(0, 3);
                String number = rd.substring(3, d);
                int n = Integer.parseInt(number);
                n++;
                number = Integer.toString(n);
                txtxFIRID.setText(fir + number);
                txtxFIRID.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

                txtPetionerFIR.setText(txtxFIRID.getText());

            } else {
                txtxFIRID.setText(s);
                txtPetionerFIR.setText(txtxFIRID.getText());
            }

            if (ptr.next()) {

                String caseNo = ptr.getString("case_Id");
                int caseLenght = caseNo.length();
                String Casef = caseNo.substring(0, 5);
                String CaseInt = caseNo.substring(5, caseLenght);
                int length = Integer.parseInt(CaseInt);
                length++;
                CaseInt = Integer.toString(length);
                txtCaseNo.setText(Casef + CaseInt);


            } else {
                txtCaseNo.setText(caseno);
            }

            String fir = "SELECT FIR_Id FROM fir WHERE criminal_No=? ORDER BY FIR_Id DESC LIMIT 1";
            PreparedStatement fird = cn.prepareStatement(fir);
            fird.setString(1, txtCriminalNo.getText());
            ResultSet fi = fird.executeQuery();

            if (fi.next()) {
                txtPetionerFIR.setText(fi.getString("FIR_Id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void clearFields(ActionEvent event) {
        if (event.getSource() == btnPertionerAdd) {
            generateCriminalNumber();

            txtPetionerCity.setText("");
            txtPetitonerLastName.setText("");
            txtPetitonerMiddleName.setText("");
            txtPetionerPhone.setText("");
            txtPetionerZipcode.setText("");
            txtPtionerPostal.setText("");
            txtPetitonerfirstName.setText("");
            comboboxPetionerGender.setValue(null);

        }


    }

    private void refreshAvailablePorts() {
        disconnectFromArduino();
        portListDisplay = FXCollections.observableArrayList();

//        portList.setValue(null);
        String[] portNames = SerialPortList.getPortNames();
        System.out.println("Available COM Ports:");
        for (String portName : portNames) {
            portListDisplay.add(portName);
            comboDisplayPorts.setItems(portListDisplay);
            System.out.println(portName);
        }
//        portList.setSelectionModel(portList.getValue());
//        portList.getItems().addAll(portListDisplay);

    }

    @FXML
    public boolean connectToArduino() throws SerialPortTimeoutException, SerialPortException, InterruptedException {

        byte sensorFound = -1;

        arduinoPort = new SerialPort(selectedPort);

        try {
            arduinoPort.openPort();    //Open serial port
            lbFingerDisplay.setText("Port open");
            arduinoPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);    //Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
            arduinoPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            sensorFound = arduinoPort.readBytes(1, 10000)[0];
        } catch (SerialPortException serialEx) {
            System.out.println(serialEx);
//			terminalText.append("Error opening port.\n");
            return false;
        } catch (SerialPortTimeoutException timeoutEx) {
            System.out.println(timeoutEx);
//			terminalText.append("Connection timeout. Is the Arduino connected to " + selectedPort + " and running properly?\n");
            return false;
        }

        lbFingerDisplay.setText("Connected");

        switch (sensorFound) {
            case '0':
                System.out.println("No fingerprint sensor found!\n");

                break;
            case '1':
                try {

                    System.out.println("Connected to Fingerprint sensor!\n");

                    arduinoPort.sendBreak(200);
                    arduinoPort.writeBytes("1001".getBytes());

                    byte fingerPrintCode = (byte) arduinoPort.getInputBufferBytesCount();
                    BooleanProperty running = new SimpleBooleanProperty(true);

                    if (fingerPrintCode > 0) {
                        Thread thread = new Thread();
                        thread.start();

                        if (thread.isAlive()) {
                            for (int i = 0; i < 5; i++) {
                                Readingrduino();
                                Thread.sleep(200);
                            }
                        } else {
                            System.out.println("ERROR FOUND WITH THREAD");
                            System.exit(1);
                        }
                    }

                } catch (SerialPortException serialEx) {
                    System.out.println("Failed to communicate.\n");
                }
                break;
            default:
                System.out.println("Unknown error!\n");
                break;
        }

        Runtime.getRuntime().addShutdownHook(portCloseHook = new Thread() {
            @Override
            public void run() {
                try {
                    arduinoPort.closePort();
                    System.out.println("Shutdown hook: Disconnected.\n");
                } catch (SerialPortException serialEx) {
                    System.out.println("Shutdown hook: Port not opened.\n");

                }
            }
        });
        return false;
    }

    private void disconnectFromArduino() {
        try {
            arduinoPort.writeBytes("1001".getBytes());
            arduinoPort.closePort();
            lbFingerDisplay.setText(" FingerPrint Scanner Disconnected");
            System.out.println("System Closed...!!!!!!");
        } catch (SerialPortException serialEx) {
            System.out.println("Port not opened.\n");
        }

        if (portCloseHook != null) {
            Runtime.getRuntime().removeShutdownHook(portCloseHook);
            portCloseHook = null;
        }
    }

    @FXML
    public void buttonAction(javafx.event.ActionEvent e) {
        if (e.getSource().equals(btnEnrollFingerPrint)) {

            BooleanProperty running = new SimpleBooleanProperty(true);
            if (arduinoPort.isOpened() && lbFingerDisplay.getText().equals("Connected")) {
                return;
            }
            selectedPort = comboDisplayPorts.getValue();

            Platform.runLater(() -> {
                try {
                    connectToArduino();
                } catch (SerialPortTimeoutException | SerialPortException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (e.getSource().equals(btnExit)) {
            Platform.runLater(() -> {
                disconnectFromArduino();
                System.exit(1);
                arduinoThread.interrupt();
            });
        } else if (e.getSource().equals(btnRefreshScanner)) {
            Platform.runLater(() -> {
//                lbReadingFinger.setText("Refreshing...");
                refreshAvailablePorts();

                try {
                    connectToArduino();
                } catch (SerialPortTimeoutException ex) {
//                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Connection Problem.\n");

                } catch (SerialPortException ex) {
                    Logger.getLogger(SeniorStaffController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SeniorStaffController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void Readingrduino() throws InterruptedException {
        arduinoThread = new Thread(() -> {
            Runnable updater = () -> {
                try {

                    if (arduinoPort.getInputBufferBytesCount() > 0) {   //check size of buffer
                        String x = arduinoPort.readString();
                        System.out.println(x);
                        if (x.contains("Type in the ID # you want to save this finger as...")) {
                            arduinoPort.writeBytes("1001".getBytes());
                            rightThumbProgress.setVisible(true);
                        }
                        if (x.contains("Image taken")) {
                            lbFingerDisplay.setText("Image taken");
                            Thread.sleep(1000);
                        }
                        if (x.contains("Image converted")) {
                            lbFingerDisplay.setText("Image converted");
                            Thread.sleep(1000);
                        }
                        if (x.contains("Image taken")) {
                            lbFingerDisplay.setText("Image taken");
                            Thread.sleep(1000);
                        }
                        if (x.contains("Remove finger")) {
                            lbFingerDisplay.setText("Remove finger");
                            Thread.sleep(2000);
                        }
                        if (x.contains("Place same finger again")) {
                            lbFingerDisplay.setText("Place same finger again");
                            Thread.sleep(2000);
                        }
                        if (x.contains("Prints matched!")) {
                            lbFingerDisplay.setText("Prints matched!");
                            Thread.sleep(2000);
                        }
                        if (x.contains("Stored!")) {
                            String text = "STORED SUCCESSFULY!!";
                            lbFingerDisplay.setText(text);
                            Thread.sleep(3000);
                        }
                    }
                } catch (SerialPortException | InterruptedException e) {
                    e.printStackTrace();
                }
            };

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(updater);
            }

        });

        arduinoThread.setDaemon(true);
        arduinoThread.start();

    }

    @Override
    public void run() {
        try {
            connectToArduino();
        } catch (SerialPortTimeoutException | InterruptedException | SerialPortException e) {
            e.printStackTrace();
        }
    }

    //set Progress indicators visisbility false in the during initiakization

    public void HideProgressIndicator() {
        rightThumbProgress.setVisible(false);
        firstFingerProgress.setVisible(false);
        secondFingerProgress.setVisible(false);
        thirdFingerProgress.setVisible(false);
        fouthFingerProgress.setVisible(false);

        leftThumbProgress.setVisible(false);
        firstLeftFingerProgress.setVisible(false);
        secondLeftFingerProgress.setVisible(false);
        thirdLeftFingerProgress.setVisible(false);
        fourthLeftFingerProgress.setVisible(false);
    }
}
