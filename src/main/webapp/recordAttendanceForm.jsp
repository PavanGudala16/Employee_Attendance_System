<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Record Attendance</title>
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
        width: calc(100% - 22px); /* Adjust for padding and border */
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
    }
    button {
        width: 100%;
        padding: 10px;
        background-color: #28a745; /* Green for record/add */
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
    }
    button:hover {
        background-color: #218838;
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

    <jsp:include page="navigation.jspf" /> <%-- This directive now works because the file is .jsp --%>

    <div class="container">
        <h2>Record Employee Attendance</h2>
        <form action="RecordAttendanceServlet" method="post">
            <div class="form-group">
                <label for="employeeId">Employee ID:</label>
                <input type="text" id="employeeId" name="employeeId" required>
            </div>
            <div class="form-group">
                <label for="date">Date:</label>
                <input type="date" id="date" name="date" required>
            </div>
            <div class="form-group">
                <label for="clockInTime">Clock In Time:</label>
                <input type="time" id="clockInTime" name="clockInTime" required>
            </div>
            <div class="form-group">
                <label for="clockOutTime">Clock Out Time (optional):</label>
                <input type="time" id="clockOutTime" name="clockOutTime">
            </div>
            <button type="submit">Record Attendance</button>
        </form>

        <%-- Add message display here if RecordAttendanceServlet sets messages --%>
        <%
            String message = (String) request.getAttribute("message");
            String messageType = (String) request.getAttribute("messageType");
            if (message != null) {
        %>
            <p class="message <%= (messageType != null ? messageType : "") %>"><%= message %></p>
        <%
            }
        %>

    </div>
</body>
</html>