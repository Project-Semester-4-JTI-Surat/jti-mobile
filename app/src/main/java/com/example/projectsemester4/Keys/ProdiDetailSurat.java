package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class ProdiDetailSurat {
    @SerializedName("id")
    private int id;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("note")
    private String note;

    public ProdiDetailSurat(int id, String keterangan, String note) {
        this.id = id;
        this.keterangan = keterangan;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
