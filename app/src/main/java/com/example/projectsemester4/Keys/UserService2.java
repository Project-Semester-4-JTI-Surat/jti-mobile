package com.example.projectsemester4.Keys;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface UserService2 {
    @GET("/api/mahasiswa/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
}
