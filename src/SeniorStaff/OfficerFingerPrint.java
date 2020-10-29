package SeniorStaff;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

public class OfficerFingerPrint implements Initializable, Runnable{
    @FXML
    private ComboBox<String> comboboxOfficerFinger;

    @FXML
    private Label lbFingerDisplay;

    @FXML
    private Button btnEnrollFingerPrint,btnExit,btnRefreshScanner;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshAvailablePorts();
    }
    private void refreshAvailablePorts() {
        disconnectFromArduino();
        portListDisplay = FXCollections.observableArrayList();

//        portList.setValue(null);
        String[] portNames = SerialPortList.getPortNames();
        System.out.println("Available COM Ports:");
        for (String portName : portNames) {
            portListDisplay.add(portName);
            comboboxOfficerFinger.setItems(portListDisplay);
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
            selectedPort = comboboxOfficerFinger.getValue();

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
//                System.exit(1);
                ((javafx.scene.Node)e.getSource()).getScene().getWindow().hide();
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
//                            rightThumbProgress.setVisible(true);
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

}
