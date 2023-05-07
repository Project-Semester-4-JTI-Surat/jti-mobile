package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("nim")
    private String nim;
    @SerializedName("password")
    private String password;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
