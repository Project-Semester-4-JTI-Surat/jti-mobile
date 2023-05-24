package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DosenResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Dosen> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Dosen> getData() {
        return data;
    }
}
