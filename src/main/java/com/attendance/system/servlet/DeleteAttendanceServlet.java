package com.attendance.system.servlet; // CORRECT PACKAGE

import com.attendance.system.util.DBConnection; // Corrected import
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteAttendanceServlet")
public class DeleteAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String attendanceIdStr = request.getParameter("attendanceId");

        Connection conn = null;
        PreparedStatement stmt = null;

        if (attendanceIdStr != null && !attendanceIdStr.isEmpty()) {
            try {
                int attendanceId = Integer.parseInt(attendanceIdStr);
                conn = DBConnection.getConnection();
                String sql = "DELETE FROM EmployeeAttendance WHERE attendance_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, attendanceId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Use session to pass message to next page after redirect
                    request.getSession().setAttribute("message", "Attendance record deleted successfully!");
                    request.getSession().setAttribute("messageType", "success");
                } else {
                    request.getSession().setAttribute("message", "Failed to delete attendance record or record not found.");
                    request.getSession().setAttribute("messageType", "error");
                }
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("message", "Invalid Attendance ID format for deletion.");
                request.getSession().setAttribute("messageType", "error");
                e.printStackTrace();
            } catch (SQLException e) {
                request.getSession().setAttribute("message", "Database error during deletion: " + e.getMessage());
                request.getSession().setAttribute("messageType", "error");
                e.printStackTrace();
            } finally {
                DBConnection.closeConnection(stmt, conn);
            }
        } else {
            request.getSession().setAttribute("message", "No Attendance ID provided for deletion.");
            request.getSession().setAttribute("messageType", "error");
        }
        // Redirect to ViewAttendanceServlet to display the updated list and message
        response.sendRedirect("ViewAttendanceServlet");
    }
}