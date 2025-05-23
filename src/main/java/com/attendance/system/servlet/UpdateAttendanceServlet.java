package com.attendance.system.servlet;

import com.attendance.system.model.AttendanceRecord;
import com.attendance.system.util.DBConnection;
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

@WebServlet("/UpdateAttendanceServlet")
public class UpdateAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles displaying the form with existing data
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String attendanceIdStr = request.getParameter("attendanceId");
        AttendanceRecord attendanceRecord = null; // Correctly declared and initialized
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (attendanceIdStr != null && !attendanceIdStr.isEmpty()) {
            try {
                int attendanceId = Integer.parseInt(attendanceIdStr);
                conn = DBConnection.getConnection();
                String sql = "SELECT attendance_id, employee_id, date, clock_in_time, clock_out_time FROM EmployeeAttendance WHERE attendance_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, attendanceId);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    attendanceRecord = new AttendanceRecord(); // Object is created
                    attendanceRecord.setAttendanceId(rs.getInt("attendance_id"));
                    // --- THESE LINES WERE CHANGED ---
                    attendanceRecord.setEmployeeId(rs.getInt("employee_id")); // Used attendanceRecord instead of record
                    attendanceRecord.setDate(rs.getDate("date"));
                    attendanceRecord.setClockInTime(rs.getTime("clock_in_time"));
                    attendanceRecord.setClockOutTime(rs.getTime("clock_out_time"));
                    // --- END CHANGED LINES ---
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid Attendance ID format.");
                request.setAttribute("messageType", "error");
                e.printStackTrace();
            } catch (SQLException e) {
                request.setAttribute("message", "Database error: " + e.getMessage());
                request.setAttribute("messageType", "error");
                e.printStackTrace();
            } finally {
                // Ensure to close ResultSet, PreparedStatement, and Connection
                DBConnection.closeConnection(rs, stmt, conn);
            }
        }
        request.setAttribute("attendanceRecord", attendanceRecord);
        request.getRequestDispatcher("updateAttendance.jsp").forward(request, response);
    }

    // Handles updating the record after form submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String attendanceIdStr = request.getParameter("attendanceId");
        String employeeIdStr = request.getParameter("employeeId");
        String date = request.getParameter("date");
        String clockInTime = request.getParameter("clockInTime");
        String clockOutTime = request.getParameter("clockOutTime");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            int attendanceId = Integer.parseInt(attendanceIdStr);
            int employeeId = Integer.parseInt(employeeIdStr);

            conn = DBConnection.getConnection();
            String sql = "UPDATE EmployeeAttendance SET employee_id = ?, date = ?, clock_in_time = ?, clock_out_time = ? WHERE attendance_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            stmt.setString(2, date);
            stmt.setString(3, clockInTime);

            if (clockOutTime != null && !clockOutTime.isEmpty()) {
                stmt.setString(4, clockOutTime);
            } else {
                stmt.setNull(4, java.sql.Types.TIME); // Set to NULL if clock out is empty
            }
            stmt.setInt(5, attendanceId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                request.setAttribute("message", "Attendance record updated successfully!");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to update attendance record. Record might not exist or Employee ID is invalid.");
                request.setAttribute("messageType", "error");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid ID format. Please enter valid numbers.");
            request.setAttribute("messageType", "error");
            e.printStackTrace();
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                request.setAttribute("message", "Error: Employee ID " + employeeIdStr + " does not exist. Please check the Employee table.");
            } else {
                request.setAttribute("message", "Database error: " + e.getMessage());
            }
            request.setAttribute("messageType", "error");
            e.printStackTrace();
        } finally {
            // Ensure to close PreparedStatement and Connection
            DBConnection.closeConnection(stmt, conn);
        }
        // After update, show the update form again with potential messages, pre-populating it if successful
        // We'll call doGet to re-display the form with messages and updated data
        doGet(request, response);
    }
}