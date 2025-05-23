<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home - Employee Attendance</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f4f4f4;
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
        color: #333;
        text-align: center;
        margin-bottom: 20px;
    }
</style>
</head>
<body>
    <%-- Include the reusable navigation menu --%>
    <jsp:include page="navigation.jspf" />

    <div class="container">
        <h2>Welcome to Employee Attendance Management System</h2>
        <p>Please use the navigation menu above to manage attendance records.</p>
        <%-- Display the logged-in user's roll number from the session --%>
        <p>Currently logged in as: <strong><%= session.getAttribute("loggedInUser") %></strong></p>
    </div>
</body>
</html>