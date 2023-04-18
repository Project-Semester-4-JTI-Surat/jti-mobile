package com.example.projectsemester4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TampilanLogin extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    boolean passwordVisible;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_login);

        etEmail = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginButton);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE); // Initialize SharedPreferences

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(TampilanLogin.this, "Email / Password Required", Toast.LENGTH_LONG).show();
                } else {
                    //proceed to login
                    login();
                }

            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= etPassword.getRight() - etPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = etPassword.getSelectionEnd();
                        if (passwordVisible) {
                            // Set drawable image
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.toggle_password, 0);
                            // For hide password
                            etPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            // Set drawable image
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.toggle_password_on, 0);
                            // For show password
                            etPassword.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        etPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(etEmail.getText().toString());
        loginRequest.setPassword(etPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(TampilanLogin.this, "Login Successful", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();

                    // Save email to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", loginResponse.getEmail());
                    editor.apply();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(TampilanLogin.this, MainActivity.class).putExtra("data", loginResponse.getEmail()));
                        }
                    }, 700);

                } else {
                    Toast.makeText(TampilanLogin.this, "Login Failed", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(TampilanLogin.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                nims = nim.getText().toString().trim();
//                passwords = password.getText().toString().trim();
//                if (!nims.isEmpty() && !passwords.isEmpty()) {
//                    login(nims, passwords);
//                } else {
//                    Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
//                }
////                // Cek apakah inputan nim dan password kosong
////                if (!nimValue.isEmpty() && !passwordValue.isEmpty()) {
////                    // Buat objek OkHttpClient untuk melakukan HTTP request
////                    OkHttpClient client = new OkHttpClient();
////
////                    // Membuat request body untuk mengirim data login ke API
////                    FormBody formBody = new FormBody.Builder()
////                            .add("nim", nimValue)
////                            .add("password", passwordValue)
////                            .build();
////
////                    // Membuat request POST ke API login
////                    Request request = new Request.Builder()
////                            .url("http://jti-surat.my.id/api/mahasiswa/") // Ganti dengan alamat URL API login Anda
////                            .post(formBody)
////                            .build();
////
////                    // Mengirim request ke API
////                    client.newCall(request).enqueue(new Callback() {
////                        @Override
////                        public void onFailure(Call call, IOException e) {
////                            // Tampilkan pesan error ketika request gagal
//////                            Toast.makeText(TampilanLogin.this, "Request Gagal", Toast.LENGTH_SHORT).show();
////                            e.printStackTrace();
////                        }
////
////                        @Override
////                        public void onResponse(Call call, Response response) throws IOException {
////                            String responseBody = response.body().string();
////                            try {
////                                JSONObject jsonObject = new JSONObject(responseBody);
////                                boolean success = jsonObject.getBoolean("success");
////                                if (success) {
////                                    // Login berhasil
////                                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                                    editor.putString("nim", nimValue);
////                                    editor.putString("password", passwordValue);
////                                    editor.apply();
////                                    // Lakukan navigasi ke halaman berikutnya setelah login berhasil
////                                    Toast.makeText(TampilanLogin.this, "Login successful!", Toast.LENGTH_SHORT).show();
////                                    Intent intent = new Intent(TampilanLogin.this, MainActivity.class);
////                                    startActivity(intent);
////                                } else {
////                                    Toast.makeText(TampilanLogin.this, "Invalid NIM or password", Toast.LENGTH_SHORT).show();
////                                }
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    });
////                } else {
////                    // Tampilkan pesan error inputan kosong
////                    Toast.makeText(TampilanLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
////                }
//            }
//        });


//    private void login(final String nim, final String password) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://127.0.0.1:8000/api/mahasiswa/login",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d(TAG, "Login Response: " + response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            boolean success = jsonObject.getBoolean("success");
//                            String message = jsonObject.getString("message");
//                            if (success) {
//                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                                // Jika login berhasil, bisa dilanjutkan ke halaman berikutnya
//                                Intent intent = new Intent(context, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(context, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "Login Error: " + error.getMessage());
//                        Toast.makeText(context, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("nim", nim);
//                params.put("password", password);
//                return params;
//            }
//        };
//
//        // Menambahkan request ke queue
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }


