import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class database_connection {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6703044";
    private static final String USER = "sql6703044";
    private static final String PASSWORD = "TfXNtViVgQ";

    // Method to establish a database connection
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "JDBC problem");
            throw new SQLException("Failed to establish a database connection.");
        }
        return connection;
    }

    // Test the database connection
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
            	Statement statement = connection.createStatement();
            	@SuppressWarnings("unused")
                int rowsInserted = statement.executeUpdate("INSERT INTO trans_acc (accid,accname, password) VALUES (null,'sai', 123)");
                    
                System.out.println("Database connection established successfully.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }
}
