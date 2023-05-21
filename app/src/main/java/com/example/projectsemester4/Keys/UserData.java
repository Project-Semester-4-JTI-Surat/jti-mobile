package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("user")
    private LoginRequest user;

    public LoginRequest getUser() {
        return user;
    }
}
