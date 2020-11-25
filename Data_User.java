package com.example.arogyademo;

public class Data_User {
    String userId,name,gender,date,blood_group,state,city;
    public Data_User()
    {}
    public Data_User(String userId, String name, String gender, String date, String blood_group, String state, String city) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.blood_group = blood_group;
        this.state = state;
        this.city = city;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getDate() {
        return date;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }
}
