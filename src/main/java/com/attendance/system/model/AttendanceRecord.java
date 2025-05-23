package com.attendance.system.model; // CORRECT PACKAGE

import java.sql.Date;
import java.sql.Time;

public class AttendanceRecord {
    private int attendanceId;
    private int employeeId;
    private Date date;
    private Time clockInTime;
    private Time clockOutTime;

    public AttendanceRecord() {
    }

    public AttendanceRecord(int attendanceId, int employeeId, Date date, Time clockInTime, Time clockOutTime) {
        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.date = date;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(Time clockInTime) {
        this.clockInTime = clockInTime;
    }

    public Time getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(Time clockOutTime) {
        this.clockOutTime = clockOutTime;
    }
}