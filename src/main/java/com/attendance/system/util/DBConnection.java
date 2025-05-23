package com.attendance.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
    // Corrected JDBC URL and Username based on your MySQL Connections screenshot
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/AttendanceDB"; // Using 127.0.0.1 for localhost
    private static final String JDBC_USERNAME = "root"; // As seen in your screenshot
    private static final String JDBC_PASSWORD = "Udaya#16"; // !!! YOU MUST CHANGE THIS to your actual MySQL root password !!!

    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the JDBC driver is loaded.
            // For MySQL Connector/J 8.0+, Class.forName is technically not strictly necessary
            // if using a servlet container that automatically registers drivers,
            // but it's good practice for older versions or clarity.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found. Please ensure mysql-connector-java.jar is in WEB-INF/lib.");
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    // --- Overloaded closeConnection methods ---

    // 1. Closes a single Connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 2. Closes a single Statement (can be a Statement or PreparedStatement)
    public static void closeConnection(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing Statement: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 3. Closes a single PreparedStatement (specific overload for PreparedStatement)
    public static void closeConnection(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing PreparedStatement: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 4. Closes a single ResultSet
    public static void closeConnection(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 5. Closes ResultSet, Statement, and Connection (common for SELECT operations)
    public static void closeConnection(ResultSet rs, Statement stmt, Connection conn) {
        closeConnection(rs);
        closeConnection(stmt);
        closeConnection(conn);
    }

    // 6. Closes ResultSet, PreparedStatement, and Connection (common for SELECT operations with PS)
    public static void closeConnection(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        closeConnection(rs);
        closeConnection(pstmt); // Use the specific PreparedStatement closer
        closeConnection(conn);
    }

    // 7. Closes Statement and Connection (common for INSERT/UPDATE/DELETE operations without ResultSet)
    public static void closeConnection(Statement stmt, Connection conn) {
        closeConnection(stmt);
        closeConnection(conn);
    }

    // 8. Closes PreparedStatement and Connection (common for INSERT/UPDATE/DELETE operations with PS)
    public static void closeConnection(PreparedStatement pstmt, Connection conn) {
        closeConnection(pstmt); // Use the specific PreparedStatement closer
        closeConnection(conn);
    }
}