package com.attendance.system.servlet;

import com.attendance.system.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types; // For setting NULL types
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RecordAttendanceServlet")
public class RecordAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");
        String date = request.getParameter("date");
        String clockInTime = request.getParameter("clockInTime");
        String clockOutTime = request.getParameter("clockOutTime");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            int employeeId = Integer.parseInt(employeeIdStr);

            conn = DBConnection.getConnection();
            String sql = "INSERT INTO EmployeeAttendance (employee_id, date, clock_in_time, clock_out_time) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            pstmt.setString(2, date);
            pstmt.setString(3, clockInTime);

            if (clockOutTime != null && !clockOutTime.isEmpty()) {
                pstmt.setString(4, clockOutTime);
            } else {
                pstmt.setNull(4, Types.TIME); // Set clock_out_time to NULL if not provided
            }

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                request.setAttribute("message", "Attendance recorded successfully!");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to record attendance.");
                request.setAttribute("messageType", "error");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Employee ID format. Please enter a valid number.");
            request.setAttribute("messageType", "error");
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle foreign key constraint violation (employeeId not found)
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) { // SQLState for integrity constraint violation
                request.setAttribute("message", "Error: Employee ID " + employeeIdStr + " does not exist.");
            } else {
                request.setAttribute("message", "Database error: " + e.getMessage());
            }
            request.setAttribute("messageType", "error");
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(pstmt, conn);
        }
        // Always forward back to the JSP page to display messages and the form
        request.getRequestDispatcher("recordAttendanceForm.jsp").forward(request, response); // UPDATED FORWARD
    }
}