package dbUtill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    private static final String driver ="com.mysql.jdbc.Driver";
    private static String url ="jdbc:mysql://localhost/criminal_and_emergency_alarm_control_system";
    private static String username="root";
    private static String PASSWORD="";

    public static Connection getConnection() throws SQLException {
        try{
            Class.forName(driver);
            return DriverManager.getConnection(url,username,PASSWORD);

        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
