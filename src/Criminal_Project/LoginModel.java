package Criminal_Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtill.dbConnection;

import javax.swing.*;

public class LoginModel {
    Connection connection; //create connection object

    public LoginModel() {
        try {
            connection = dbConnection.getConnection();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        // check if database is connected
        if (connection == null){
            System.exit(1);
        }
    }

    //database connection method

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }
    //confirm login method

    public boolean isLogin(String username, String Pass, String opt, String validateLoginId,String validateLoginPass) throws Exception{

        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM officer WHERE officer_Id = ? AND passsword = ? AND rank = ?";

        try {
            pr = this.connection.prepareStatement(sql);

            pr.setString(1, username);
            pr.setString(2, Pass);
            pr.setString(3,opt);

            rs = pr.executeQuery();

            boolean bool;
            if (rs.next()){
                String validate = rs.getString("officer_Id");
                String validatePassword = rs.getString("passsword");
                if (validate.equals(validateLoginId)&& validatePassword.equals(validateLoginPass)){
                    return true;
                }
                else{
                    return false;
                }

            }
            return false;

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
            return false;
        }
        //clossing the database connections
        finally
        {
            {
                pr.close();
                rs.close();
            }
        }

    }
}
