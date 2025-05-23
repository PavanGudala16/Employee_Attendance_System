# Employee Attendance Management System

## Project Overview

The Employee Attendance Management System is a web-based application designed to streamline the process of recording and viewing employee attendance. Built using Java Servlets, JSP, and MySQL, it provides a simple and intuitive interface for employees to clock in/out and for administrators to view attendance records.

## Features

* **User Authentication:** Secure login for employees using Roll Number and Password.
* **Attendance Recording:** Employees can record their clock-in and clock-out times.
* **Attendance Viewing:** View a list of recorded attendance entries.
* **Session Management:** Maintains user session after successful login.
* **Basic Navigation:** Clear navigation between different sections of the application.

## Technologies Used

* **Backend:** Java (Servlets, JSP)
* **Web Server:** Apache Tomcat 9.0.105
* **Database:** MySQL
* **JDBC Driver:** MySQL Connector/J
* **Development IDE:** Eclipse IDE for Enterprise Java and Web Developers
* **Frontend:** HTML, CSS

## Prerequisites

Before setting up and running this project, ensure you have the following installed:

* **Java Development Kit (JDK):** Version 21 or higher (e.g., OpenJDK 21, Oracle JDK 21).
* **Apache Tomcat:** Version 9.0.105.
* **MySQL Server:** Version 8.0 or compatible.
* **MySQL Workbench** (or any other MySQL client) for database management.
* **Eclipse IDE:** Eclipse IDE for Enterprise Java and Web Developers (or a similar version that supports Dynamic Web Projects).
* **MySQL Connector/J:** The JDBC driver for MySQL. Ensure `mysql-connector-java-x.x.x.jar` is placed in your project's `WebContent/WEB-INF/lib` directory.

## Database Setup

1.  **Connect to MySQL:** Open MySQL Workbench and connect to your MySQL server (e.g., `root` user on `127.0.0.1:3306`).

2.  **Create Database:** Execute the following SQL query to create the `AttendanceDB` database:
    ```sql
    CREATE DATABASE IF NOT EXISTS AttendanceDB;
    ```

3.  **Create Tables:** Switch to the `AttendanceDB` and create the `Employee` and `EmployeeAttendance` tables:
    ```sql
    USE AttendanceDB;

    CREATE TABLE IF NOT EXISTS Employee (
        employee_id INT PRIMARY KEY AUTO_INCREMENT,
        roll_number VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        employee_name VARCHAR(100),
        INDEX(roll_number)
    );

    CREATE TABLE IF NOT EXISTS EmployeeAttendance (
        attendance_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        date DATE NOT NULL,
        clock_in_time TIME NOT NULL,
        clock_out_time TIME,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
    );
    ```

4.  **Insert Sample Data:** Populate the `Employee` table with a test user and sample attendance records:
    ```sql
    USE AttendanceDB;

    INSERT INTO Employee (roll_number, password, employee_name) VALUES ('123', 'Udaya#16', 'Pavan Udaya');
    INSERT INTO Employee (roll_number, password, employee_name) VALUES ('admin', 'adminpass', 'Administrator Account');

    INSERT INTO EmployeeAttendance (employee_id, date, clock_in_time, clock_out_time)
    VALUES (1, '2025-05-22', '09:00:00', '17:00:00');

    INSERT INTO EmployeeAttendance (employee_id, date, clock_in_time, clock_out_time)
    VALUES (1, '2025-05-23', '09:15:00', NULL);

    INSERT INTO EmployeeAttendance (employee_id, date, clock_in_time, clock_out_time)
    VALUES (2, '2025-05-22', '08:30:00', '16:45:00');
    ```

5.  **Configure `DBConnection.java`:**
    Open `src/main/java/com/attendance/system/util/DBConnection.java` and ensure the `JDBC_PASSWORD` matches your MySQL `root` user's password.
    ```java
    private static final String JDBC_PASSWORD = "Udaya#16"; // Make sure this matches your actual password
    ```

## Installation and Deployment

### Option 1: Deploy using Eclipse (Recommended for Development)

1.  **Import Project:** In Eclipse, go to `File > Import... > Existing Projects into Workspace` and select your `AttendanceManagement` project folder.
2.  **Configure Tomcat Server:**
    * In Eclipse, go to `Window > Show View > Servers`.
    * Right-click in the Servers view and select `New > Server`.
    * Choose `Apache > Tomcat v9.0 Server` and configure it to point to your Tomcat installation directory.
3.  **Add Project to Server:**
    * In the Servers view, right-click on your configured Tomcat server and select `Add and Remove...`.
    * Move `AttendanceManagement` from "Available" to "Configured".
    * Click `Finish`.
4.  **Run/Restart Server:** Right-click on the Tomcat server in the Servers view and select `Start` or `Restart`.

### Option 2: Manual WAR Deployment (for Production/Testing)

1.  **Export WAR File:**
    * In Eclipse, right-click on your `AttendanceManagement` project in the Project Explorer.
    * Select `Export... > Web > WAR file`.
    * Choose a destination for `AttendanceManagement.war` and click `Finish`.
2.  **Deploy WAR:**
    * Copy the generated `AttendanceManagement.war` file to the `webapps` directory of your Apache Tomcat installation (e.g., `C:\apache-tomcat-9.0.105\webapps`).
3.  **Start Tomcat:**
    * Navigate to your Tomcat's `bin` directory (e.g., `C:\apache-tomcat-9.0.105\bin`).
    * Run `startup.bat` (Windows) or `startup.sh` (Linux/macOS).

## Usage

1.  **Access the Application:** Once the server is running, open your web browser and navigate to:
    `http://localhost:8080/AttendanceManagement/login.jsp`
    (Replace `AttendanceManagement` if your project's context path is different).

2.  **Login:** Use the credentials from your `Employee` table (e.g., Roll Number: `123`, Password: `Udaya#16`).

3.  **Navigate:** Use the navigation bar to access `Home`, `Record Attendance`, and `View Attendance` pages.

4.  **Logout:** Click the `Logout` button to invalidate your session and return to the login page.

## Project Structure (Key Files)
AttendanceManagement/
├── src/main/java/
│   └── com/attendance/system/
│       ├── model/             (e.g., AttendanceRecord.java)
│       ├── servlet/           (LoginServlet.java, RecordAttendanceServlet.java, etc.)
│       └── util/              (DBConnection.java)
├── WebContent/
│   ├── WEB-INF/
│   │   ├── lib/               (mysql-connector-java.jar)
│   │   └── web.xml            (Deployment Descriptor)
│   ├── home.jsp
│   ├── login.jsp              (Login page)
│   ├── navigation.jspf       (Reusable navigation header)
│   ├── recordAttendanceForm.jsp (Form to record attendance)
│   ├── viewAttendance.jsp     (Page to display attendance - requires implementation)
│   └── style.css              (Optional: if you have a separate CSS file)
└── .gitignore                 (Important for Git)

## Contributing

Feel free to fork this repository, open issues, or submit pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Copyright (c) 2025 Udaya Venkata Pavan Gudala

## Author

Pavan Gudala
[Optional: Add your GitHub profile link or LinkedIn profile link]
