package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class UpdateRequest {
    @SerializedName("password")
    private String password;

    public UpdateRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
