package com.example.projectsemester4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.projectsemester4.Keys.MyPreferences;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    TextView appDesc;
    LottieAnimationView lottie;
    ProgressBar pb;
    int progressStatus = 0;
    private MyPreferences myPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //menghilangkan ActionBar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        pb = findViewById(R.id.progressBar);
//        appDesc = findViewById(R.id.textView1);
//        lottie = findViewById(R.id.AnimationView1);

        myPreferences = new MyPreferences(this);

//// Saat pengguna berhasil login
//        myPreferences.saveBoolean("isLoggedIn", true);
//
//// Saat pengguna keluar
//        myPreferences.saveBoolean("isLoggedIn", false);
//
//// Saat aplikasi dibuka kembali
//        boolean isLoggedIn = myPreferences.getBoolean("isLoggedIn", false);
//        if (isLoggedIn) {
//            // User masih login, lakukan tindakan yang sesuai
//            startActivity(new Intent(SplashScreen.this, MainActivity.class));
//            finish();
//        } else {
//            // User telah keluar atau belum pernah login, lakukan tindakan yang sesuai
//            startActivity(new Intent(SplashScreen.this, SplashScreen.class));
//            finish();
//        }
//        Membuat objek Handler untuk menangani pesan atau tugas yang dikirimkan ke thread UI.
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myPreferences.getLoggedInStatus()==true) {
                    // Jika pengguna sudah login, langsung beralih ke MainActivity
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    // Jika pengguna belum login, beralih ke TampilanLogin
                    Intent intent = new Intent(SplashScreen.this, TampilanLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000); //5000 L = 5 detik
        pb = findViewById(R.id.progressBar);

        // animasi progress bar
        pb.setProgress(0);
        final int totalProgressTime = 100;
        final Thread thread = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;
//                Melakukan loop animasi progress bar dari posisi awal hingga mencapai posisi akhir (100%).
                while (jumpTime < totalProgressTime) {
                    try {
//                        Mengatur waktu jeda 50 milidetik untuk memperhalus animasi.
                        sleep(30);
//                        Menambahkan nilai progress bar sebanyak 1 persen setiap kali loop dijalankan.
                        jumpTime += 1;
                        pb.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                }
            }
        };
        thread.start();
    }
}
