<%@ page import="com.attendance.system.model.AttendanceRecord" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Attendance</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    .container {
        width: 60%;
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
    .form-group {
        margin-bottom: 15px;
    }
    label {
        display: block;
        margin-bottom: 5px;
        color: #555;
    }
    input[type="text"],
    input[type="date"],
    input[type="time"] {
        width: calc(100% - 22px);
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
    }
    button {
        width: 100%;
        padding: 10px;
        background-color: #007bff; /* Blue for update button */
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
    }
    button:hover {
        background-color: #0056b3;
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
        <h2>Update Employee Attendance Record</h2>
        <%
            // Retrieve the attendance record object from the request attribute
            AttendanceRecord attendanceRecord = (AttendanceRecord) request.getAttribute("attendanceRecord");

            if (attendanceRecord != null) {
        %>
            <form action="UpdateAttendanceServlet" method="post">
                <%-- Hidden field to pass the attendance ID for the update operation --%>
                <input type="hidden" name="attendanceId" value="<%= attendanceRecord.getAttendanceId() %>">

                <div class="form-group">
                    <label for="employeeId">Employee ID:</label>
                    <input type="text" id="employeeId" name="employeeId" value="<%= attendanceRecord.getEmployeeId() %>" required>
                </div>
                <div class="form-group">
                    <label for="date">Date:</label>
                    <input type="date" id="date" name="date" value="<%= attendanceRecord.getDate() %>" required>
                </div>
                <div class="form-group">
                    <label for="clockInTime">Clock In Time:</label>
                    <input type="time" id="clockInTime" name="clockInTime" value="<%= attendanceRecord.getClockInTime() %>" required>
                </div>
                <div class="form-group">
                    <label for="clockOutTime">Clock Out Time (optional):</label>
                    <input type="time" id="clockOutTime" name="clockOutTime" value="<%= (attendanceRecord.getClockOutTime() != null ? attendanceRecord.getClockOutTime() : "") %>">
                </div>
                <button type="submit">Update Attendance</button>
            </form>
        <%
            } else {
        %>
            <p class="error">Attendance record not found or invalid ID provided. Please navigate from "View Attendance" to edit.</p>
        <%
            } // End of if-else for attendanceRecord
        %>

        <%-- Display messages from UpdateAttendanceServlet --%>
        <%
            String message = (String) request.getAttribute("message");
            String messageType = (String) request.getAttribute("messageType");
            if (message != null) {
        %>
            <p class="message <%= messageType %>"><%= message %></p>
        <%
            }
        %>
    </div>
</body>
</html>