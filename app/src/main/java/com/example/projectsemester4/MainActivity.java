package com.example.projectsemester4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.projectsemester4.Keys.MyPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.projectsemester4.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import android.Manifest;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {
//    private static final int PERMISSION_REQUEST_CODE = 1;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabs;
    private MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar  toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager1);
        fabs = findViewById(R.id.fab);
        myPreferences = new MyPreferences(this);

        // Periksa izin penyimpanan saat pertama kali dibuka
////        checkStoragePermission();
//        String[] permissionsStorage = {Manifest.permission.READ_EXTERNAL_STORAGE};
//        int requestExternalStorage = 1;
//        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, permissionsStorage, requestExternalStorage);
//        }
        // Cek status login pada SharedPreferences

        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new Fragment0(), "Semua");
        vpAdapter.addFragment(new Fragment1(), "Menunggu");
        vpAdapter.addFragment(new Fragment2(), "Diproses");
        vpAdapter.addFragment(new Fragment3(), "Bisa Diambil");
        vpAdapter.addFragment(new Fragment4(), "Selesai");
        vpAdapter.addFragment(new Fragment5(), "Gagal");
        viewPager.setAdapter(vpAdapter);

        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TambahSurat.class);
                startActivity(intent);
            }
        });
    }
//    private void checkStoragePermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    },
//                    PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                // Izin diberikan oleh pengguna
//                Toast.makeText(this, "Izin penyimpanan diberikan", Toast.LENGTH_SHORT).show();
//            } else {
//                // Izin ditolak oleh pengguna
//                Toast.makeText(this, "Izin penyimpanan ditolak", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Logout user and redirect to login page


                myPreferences.clear();

                startActivity(new Intent(MainActivity.this, TampilanLogin.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss dialog and do nothing
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile){
            Intent intent = new Intent(MainActivity.this, TampilanUbahProfil.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_faq){
            Intent intent = new Intent(MainActivity.this, TampilanFaq.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_logout){
//            Menghapus Status login dan kembali ke Login Activity
            onBackPressed();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //    private void setSupportActionBar(Toolbar toolbar) {
//    }
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if (id == R.id.action_settings){
//            return true;
//        }else
//        return super.onOptionsItemSelected(item);
//    }
}