import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.time.LocalDate;

import javax.swing.JOptionPane;

public class db_methods {
    static int createAccount(String name, int pass) {
        System.out.println("Account creation started");
        //LocalDate currentDate = LocalDate.now();
        InetAddress localhost;
        String ipAddress = "";
        try {
            localhost = InetAddress.getLocalHost();
            ipAddress = localhost.getHostAddress();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }

        Connection cone = null;
        try {
            cone = database_connection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate failure
        }

        int accid = -1; // Default value if account creation fails

        // First, check if an account already exists with the provided IP address and password
        String query1 = "SELECT accid FROM trans_acc WHERE ipadd = ? AND password = ?";
        PreparedStatement pstmt1 = null;
        ResultSet resultSet1 = null;

        try {
            pstmt1 = cone.prepareStatement(query1);
            pstmt1.setString(1, ipAddress);
            pstmt1.setInt(2, pass);
            resultSet1 = pstmt1.executeQuery();

            if (resultSet1.next()) {
                // Account already exists, retrieve the accid and return it
                accid = resultSet1.getInt("accid");
                System.out.println("Account already exists. accid: " + accid);
                JOptionPane.showMessageDialog(null,"Account Already Exists"+accid);
            } else {
                // Account doesn't exist, create a new account
                String query2 = "INSERT INTO trans_acc (accname, ipadd, password) VALUES ( ?, ?, ?)";
                PreparedStatement pstmt2 = cone.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt2.setString(1, name);
                pstmt2.setString(2, ipAddress);
                pstmt2.setInt(3, pass);

                int rowsAffected = pstmt2.executeUpdate();
                if (rowsAffected > 0) {
                    // Account created successfully, retrieve the accid
                    ResultSet generatedKeys = pstmt2.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        accid = generatedKeys.getInt(1);
                        System.out.println("Account created successfully. accid: " + accid);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet1 != null) resultSet1.close();
                if (pstmt1 != null) pstmt1.close();
                if (cone != null) cone.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return accid;
    }
    
    
    static String getIpadd(int id) {
    	Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
        	connection=database_connection.getConnection();
        	String query = "SELECT ipadd FROM trans_acc WHERE accid=?";
        	pstmt=connection.prepareStatement(query);
        	pstmt.setInt(1, id);
        	resultSet=pstmt.executeQuery();
        	if (resultSet.next()) {
        		return resultSet.getString("ipadd");
        	}
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return "";
    }
    
    
    static boolean login(int id, int pass) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = database_connection.getConnection();
            String query = "SELECT password FROM trans_acc WHERE accid=?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int passwordFromDB = resultSet.getInt("password");
                System.out.println(passwordFromDB);
                return passwordFromDB == pass; // Compare retrieved password with provided password
            } else {
                return false; // No account found with the provided accid
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error occurred during database operation
        } finally {
            // Close resources in finally block
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
