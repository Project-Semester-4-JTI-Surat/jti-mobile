package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailSuratResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<DetailSuratRequest> data;

    public DetailSuratResponse(boolean success, String message, List<DetailSuratRequest> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

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

    public List<DetailSuratRequest> getData() {
        return data;
    }

    public void setData(List<DetailSuratRequest> data) {
        this.data = data;
    }
}
