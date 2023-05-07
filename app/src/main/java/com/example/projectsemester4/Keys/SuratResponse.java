package com.example.projectsemester4.Keys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuratResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("kode_surat")
    @Expose
    private String kodeSurat;
    @SerializedName("nama_mhs")
    @Expose
    private String namaMhs;
    @SerializedName("nim_mhs")
    @Expose
    private String nimMhs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeSurat() {
        return kodeSurat;
    }

    public void setKodeSurat(String kodeSurat) {
        this.kodeSurat = kodeSurat;
    }

    public String getNamaMhs() {
        return namaMhs;
    }

    public void setNamaMhs(String namaMhs) {
        this.namaMhs = namaMhs;
    }

    public String getNimMhs() {
        return nimMhs;
    }

    public void setNimMhs(String nimMhs) {
        this.nimMhs = nimMhs;
    }
}

