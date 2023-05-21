package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class Prodi {
    @SerializedName("id")
    private int id;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("note")
    private String note;

    public int getId() {
        return id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getNote() {
        return note;
    }
}

