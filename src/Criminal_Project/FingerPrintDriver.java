package Criminal_Project;

import SeniorStaff.SeniorStaffController;
import dbUtill.dbConnection;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FingerPrintDriver implements Runnable {
//    LoginController finger = new LoginController();

    public  Label fingerlabel;
//    public ComboBox fingerListDispolay = finger.comboboxFingerPrint;

    private String selectedPort = "NULL";
    private SerialPort arduinoPort = new SerialPort(selectedPort);
    private Thread portCloseHook = null;

    private String x;

    private static Thread arduinoThread = null;
    ObservableList<String> portListDisplay;


    Thread t = null;

    public void refreshAvailablePorts(ComboBox fingerListDispolay) {
        disconnectFromArduino(fingerlabel);
        portListDisplay = FXCollections.observableArrayList();

//        portList.setValue(null);
        String[] portNames = SerialPortList.getPortNames();
        System.out.println("Available COM Ports:");
        for (String portName : portNames) {
            portListDisplay.add(portName);
            fingerListDispolay.setItems(portListDisplay);
            System.out.println(portName);
        }
//        portList.setSelectionModel(portList.getValue());
//        portList.getItems().addAll(portListDisplay);

    }


    @FXML
    public boolean connectToArduino(Label fingerlabel) throws SerialPortTimeoutException, SerialPortException, InterruptedException {

        byte sensorFound = -1;

        arduinoPort = new SerialPort(selectedPort);

        try {
            arduinoPort.openPort();    //Open serial port
            fingerlabel.setText("Port open");
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

        fingerlabel.setText("Connected");

        switch (sensorFound) {
            case '0':
                System.out.println("No fingerprint sensor found!\n");

                break;
            case '1':
                try {

                    System.out.println("Connected to Fingerprint sensor!\n");

                    arduinoPort.sendBreak(200);
                    arduinoPort.writeBytes("1002".getBytes());

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

    private void disconnectFromArduino(Label fingerlabel) {
        try {
            arduinoPort.writeBytes("1001".getBytes());
            arduinoPort.closePort();
            fingerlabel.setText(" FingerPrint Disconnected");
            System.out.println("System Closed...!!!!!!");
        } catch (SerialPortException serialEx) {
            System.out.println("Port not opened.\n");
        }

        if (portCloseHook != null) {
            Runtime.getRuntime().removeShutdownHook(portCloseHook);
            portCloseHook = null;
        }
    }

    public int fingerPrintCode(){
        byte fingerPrintId = 1;

        try {
            Connection officerconnection = dbConnection.getConnection();

            String officerSQL= "SELECT `finger_ID` FROM `officerfingerprint` ORDER BY finger_ID DESC LIMIT 1";
            PreparedStatement officerStatement = officerconnection.prepareStatement(officerSQL);
            ResultSet officerResult = officerStatement.executeQuery();

            if(officerResult.next()){
                int   finger = officerResult.getInt("finger_ID");
                String fingerId = String.valueOf(finger);
                fingerPrintId = Byte.parseByte(fingerId);
                fingerPrintId++;

                System.out.println(fingerPrintId);
            }
            else{
                fingerPrintId = 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fingerPrintId;
    }

    public void Readingrduino() throws InterruptedException {
        arduinoThread = new Thread(() -> {
            Runnable updater = () -> {
                try {

                    if (arduinoPort.getInputBufferBytesCount() > 0) {   //check size of buffer
                        String x = arduinoPort.readString();
                        int arduinoFingerCode = fingerPrintCode();
                        System.out.println(x);
                        if (x.contains("Type in the ID # you want to save this finger as...")) {
//                            arduinoPort.writeBytes("arduinoFingerCode".getBytes());
                            arduinoPort.writeByte((byte) arduinoFingerCode);
//
                        }


                    }
                } catch (SerialPortException e) {
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
            connectToArduino(fingerlabel);
        } catch (SerialPortTimeoutException | InterruptedException | SerialPortException e) {
            e.printStackTrace();
        }
    }

}
