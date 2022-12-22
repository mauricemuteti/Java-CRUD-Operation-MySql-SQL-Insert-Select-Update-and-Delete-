/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Java CRUD Operation MySql (SQL Insert, Select, Update and Delete)
 * https://mauricemuteti.info/java-crud-operation-mysql-sql-insert-select-update-and-delete/
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        // Load the MySQL Connector/J driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL Connector/J driver: " + e);
            return;
        }

        // Establish a connection to the database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/testdb", "root", "");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e);
            return;
        }

        // Perform a simple CRUD operation
        try {
            // Create a new record
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)");
            stmt.setString(1, "John Smith");
            stmt.setString(2, "john@example.com");
            stmt.executeUpdate();

            // Read all records
            stmt = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("id: " + id + ", name: " + name + ", email: " + email);
            }

            // Update a record
            stmt = conn.prepareStatement("UPDATE users SET name = ? WHERE id = ?");
            stmt.setString(1, "Jane Smith");
            stmt.setInt(2, 1);
            stmt.executeUpdate();

            // Delete a record
            stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            stmt.setInt(1, 1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error performing CRUD operation: " + e);
        }

        // Close the connection
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e);
        }
    }
}
