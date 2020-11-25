package com.example.arogyademo;

public class Data_Emergency {
    String userId,location;
    public  Data_Emergency()
    {}
    public Data_Emergency(String userId, String location) {
        this.userId = userId;
        this.location = location;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public String getLocation() {
        return location;
    }
}
