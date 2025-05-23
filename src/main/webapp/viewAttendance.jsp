<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.attendance.system.model.AttendanceRecord" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Attendance</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    .container {
        width: 80%;
        margin: 20px auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }
    h2 {
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    th, td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
        color: #333;
    }
    tr:nth-child(even) { /* Zebra striping for table rows */
        background-color: #f9f9f9;
    }
    .action-links a {
        margin-right: 10px;
        text-decoration: none;
        color: #007bff; /* Blue for edit link */
    }
    .action-links a:hover {
        text-decoration: underline;
    }
    .action-links form {
        display: inline; /* Keep delete button on the same line as edit link */
    }
    .delete-button {
        background-color: #dc3545; /* Red for delete button */
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
    }
    .delete-button:hover {
        background-color: #c82333;
    }
    .no-records {
        text-align: center;
        color: #666;
        margin-top: 30px;
    }
    .message {
        text-align: center;
        margin-top: 15px;
        font-weight: bold;
    }
    .success {
        color: green;
    }
    .error {
        color: red;
    }
</style>
</head>
<body>
    <%-- Include the reusable navigation menu --%>
    <jsp:include page="navigation.jspf" />

    <div class="container">
        <h2>Employee Attendance Records</h2>

        <%-- Display messages from Servlets (e.g., from delete operation, passed via request attribute) --%>
        <%
            String message = (String) request.getAttribute("message");
            String messageType = (String) request.getAttribute("messageType");
            if (message != null) {
        %>
            <p class="message <%= messageType %>"><%= message %></p>
        <%
            }
        %>

        <%
            // Retrieve the list of attendance records from the request attribute
            List<AttendanceRecord> attendanceRecords = (List<AttendanceRecord>) request.getAttribute("attendanceRecords");

            if (attendanceRecords != null && !attendanceRecords.isEmpty()) {
        %>
                <table>
                    <thead>
                        <tr>
                            <th>Attendance ID</th>
                            <th>Employee ID</th>
                            <th>Date</th>
                            <th>Clock In Time</th>
                            <th>Clock Out Time</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // Iterate over the attendanceRecords list using a Java for-each loop
                            for (AttendanceRecord record : attendanceRecords) {
                        %>
                            <tr>
                                <td><%= record.getAttendanceId() %></td>
                                <td><%= record.getEmployeeId() %></td>
                                <td><%= record.getDate() %></td>
                                <td><%= record.getClockInTime() %></td>
                                <td><%= (record.getClockOutTime() != null ? record.getClockOutTime() : "N/A") %></td>
                                <td class="action-links">
                                    <a href="UpdateAttendanceServlet?attendanceId=<%= record.getAttendanceId() %>">Edit</a>
                                    <form action="DeleteAttendanceServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');">
                                        <input type="hidden" name="attendanceId" value="<%= record.getAttendanceId() %>">
                                        <button type="submit" class="delete-button">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        <%
                            } // End of for-each loop
                        %>
                    </tbody>
                </table>
        
            } else {
        
                <p class="no-records">No attendance records found.</p>
        <%
            } // End of if-else for attendanceRecords
        %>
    </div>
</body>
</html>