package com.example.projectsemester4.Keys;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SuratInsert {
    @POST("/api/mahasiswa/surat/insert")
    Call<SuratRequest> insertSurat(@Body SuratRequest suratRequest);
}
