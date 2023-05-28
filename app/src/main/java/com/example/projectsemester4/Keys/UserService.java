package com.example.projectsemester4.Keys;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @POST("/api/mahasiswa/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("/api/mahasiswa/")
    Call<LoginResponse> getUserProfile(@Header("Authorization") String token);

    @GET("/api/data/prodi")
    Call<ProdiResponse> getDataProdi();

    @POST("/api/mahasiswa/update_akun")
    Call<UpdateResponse> userUpdate(@Header("Authorization") String token, @Body UpdateRequest updateRequest);

}
