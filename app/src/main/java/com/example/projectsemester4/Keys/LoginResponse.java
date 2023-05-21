package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private UserData data;

    @SerializedName("token")
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserData getData() {
        return data;
    }

    public String getToken() {
        return token;
    }
}

