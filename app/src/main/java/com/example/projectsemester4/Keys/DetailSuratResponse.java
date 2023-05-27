package com.example.projectsemester4.Keys;

import java.util.List;

public class DetailSuratResponse {
    private String mataKuliah;
    private List<AnggotaDetailSurat> anggotaDetailSurat;

    public DetailSuratResponse(String mataKuliah, List<AnggotaDetailSurat> anggotaDetailSurat) {
        this.mataKuliah = mataKuliah;
        this.anggotaDetailSurat = anggotaDetailSurat;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public List<AnggotaDetailSurat> getAnggotaDetailSurat() {
        return anggotaDetailSurat;
    }

    public void setAnggotaDetailSurat(List<AnggotaDetailSurat> anggotaDetailSurat) {
        this.anggotaDetailSurat = anggotaDetailSurat;
    }
}
