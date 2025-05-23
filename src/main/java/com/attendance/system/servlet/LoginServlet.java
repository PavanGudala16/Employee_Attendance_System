package com.attendance.system.servlet;

import com.attendance.system.util.DBConnection; // Import DBConnection
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // This is the roll_number
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isAuthenticated = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT roll_number FROM Employee WHERE roll_number = ? AND password = ?"; // Assuming Employee table has roll_number and password
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password); // In a real app, you'd hash passwords!
            rs = pstmt.executeQuery();

            if (rs.next()) { // If a row is returned, user is authenticated
                isAuthenticated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // In a real app, log this error securely and provide a generic message to the user
            request.setAttribute("errorMessage", "A database error occurred. Please try again later.");
        } finally {
            DBConnection.closeConnection(rs, pstmt, conn); // Close resources
        }


        if (isAuthenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", username); // Store roll_number in session
            response.sendRedirect("home.jsp"); // Redirect to home page on success
        } else {
            request.setAttribute("errorMessage", "Invalid Roll Number or Password.");
            request.getRequestDispatcher("login.jsp").forward(request, response); // Forward back to login.jsp with error
        }
    }
}