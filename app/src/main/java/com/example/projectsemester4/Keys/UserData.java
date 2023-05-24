package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("user")
    private LoginRequest user;

    public LoginRequest getUser() {
        return user;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token")
    private String token;
}
