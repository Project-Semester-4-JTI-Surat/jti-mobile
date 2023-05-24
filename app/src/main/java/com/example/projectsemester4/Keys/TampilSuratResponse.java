package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TampilSuratResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TampilSuratRequest> getData() {
        return data;
    }

    public void setData(List<TampilSuratRequest> data) {
        this.data = data;
    }

    @SerializedName("data")
    private List<TampilSuratRequest> data;
}
