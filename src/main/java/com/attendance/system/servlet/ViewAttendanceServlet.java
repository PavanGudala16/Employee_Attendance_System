package com.attendance.system.servlet; // CORRECT PACKAGE

import com.attendance.system.model.AttendanceRecord; // Corrected import
import com.attendance.system.util.DBConnection;     // Corrected import
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewAttendanceServlet")
public class ViewAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT attendance_id, employee_id, date, clock_in_time, clock_out_time FROM EmployeeAttendance ORDER BY date DESC, clock_in_time DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                AttendanceRecord record = new AttendanceRecord();
                record.setAttendanceId(rs.getInt("attendance_id"));
                record.setEmployeeId(rs.getInt("employee_id"));
                record.setDate(rs.getDate("date"));
                record.setClockInTime(rs.getTime("clock_in_time"));
                record.setClockOutTime(rs.getTime("clock_out_time"));
                attendanceRecords.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving attendance records: " + e.getMessage());
        } finally {
            DBConnection.closeConnection(rs, stmt, conn);
        }

        request.setAttribute("attendanceRecords", attendanceRecords);

        // Retrieve and clear any session-scoped messages (e.g., from a delete operation)
        String sessionMessage = (String) request.getSession().getAttribute("message");
        String sessionMessageType = (String) request.getSession().getAttribute("messageType");
        if (sessionMessage != null) {
            request.setAttribute("message", sessionMessage);
            request.setAttribute("messageType", sessionMessageType);
            request.getSession().removeAttribute("message"); // Clear once displayed
            request.getSession().removeAttribute("messageType"); // Clear once displayed
        }

        request.getRequestDispatcher("viewAttendance.jsp").forward(request, response);
    }
}