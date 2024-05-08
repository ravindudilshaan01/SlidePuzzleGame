/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.puzzlegame1;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;



/**
 *
 * @author ASUS
 */
public class PuzzleGame1 {
     private static String url = "jdbc:mysql://localhost:3306/userlogin?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static  String un = "root";
    private static String pw = "";
    private Connection con;

    public PuzzleGame1() {
        con = getCon();
    }

    public static void main(String[] args) {
        PuzzleGame1 g = new PuzzleGame1();

        // Perform any necessary operations, e.g., insertData
       // login.insertData("John Doe", 1234);

        // Close the connection after performing operations
        g.closeCon();
        
    }
    public Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, un, pw);
            System.out.println("Connected to database successfully!");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error connecting to the database: " + ex.getMessage());
        }
        return con;
    }

    public void closeCon() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed.");
            } catch (SQLException ex) {
                System.out.println("Error closing the connection: " + ex.getMessage());
            }
        }
    }

    public void insertData(String UserName, String ID, int tim) {
        try {
            // Prepare SQL statement
            String sql = "INSERT INTO register (UserName, ID, time) VALUES (?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, UserName);
                statement.setString(2, ID);
                statement.setInt(3, tim);
                // Execute the statement
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Data inserted successfully!");
                    JOptionPane.showMessageDialog(null, "Login Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Login Failed", "Information", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error inserting data into the database: " + e.getMessage());
        }
    
}
    
    
    public void updatetime(String UserName, int tim) {
        try {
            // Prepare SQL statement
            String sql = "UPDATE register SET time = ? WHERE UserName = ?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, tim);
                statement.setString(2, UserName);
                // Execute the statement
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Data inserted successfully!");
                    JOptionPane.showMessageDialog(null, "Login Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Login Failed", "Information", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error inserting data into the database: " + e.getMessage());
        }
    
}
    
    public static boolean authenticateUser(String usn, String id) {
        String query = "SELECT * FROM register WHERE UserName = ? AND ID = ?";
        try (Connection connection = DriverManager.getConnection(url, un, pw);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usn);
            statement.setString(2, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If user exists, return true
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }
    
    public boolean checkusername(String usn) {
    String query = "SELECT * FROM register WHERE UserName = ?";
    try (Connection connection = DriverManager.getConnection(url, un, pw);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, usn);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next(); // If user exists, return true
    } catch (SQLException e) {
        System.err.println("Error authenticating user: " + e.getMessage());
        return false;
    }
}
    
    
    public int gettime(String username) {
    int time = 0 ;
    String query = "SELECT time FROM register WHERE UserName = '" + username + "'";
    
    try (Connection connection = DriverManager.getConnection(url, un, pw);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        
        if (resultSet.next()) {
            time = resultSet.getInt("time");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving balance: " + e.getMessage());
    }
    
    return time;
}

    public List<Entry<String, Integer>> getTop3Users() {
    List<Entry<String, Integer>> top3Users = new ArrayList<>();

    String query = "SELECT UserName, time FROM register ORDER BY time LIMIT 3";

    try (Connection connection = DriverManager.getConnection(url, un, pw);
         PreparedStatement statement = connection.prepareStatement(query)) {
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {  
            String userName = resultSet.getString("UserName");
            int time = resultSet.getInt("time");
            top3Users.add(new SimpleEntry<>(userName, time));
        }

            


    } catch (SQLException e) {
        System.err.println("Error retrieving top 3 users: " + e.getMessage());
    }

    return top3Users;
}

    
    
    
}

       