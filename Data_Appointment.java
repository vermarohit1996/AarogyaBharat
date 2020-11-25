package com.example.arogyademo;

public class Data_Appointment {
    private String userId;
    private String name;
    private String gender;
    private String phone;
    private String speciality;
    private String date;
    private String timeslot;
    private String message;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getDate() {
        return date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
