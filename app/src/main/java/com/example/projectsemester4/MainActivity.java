package com.example.projectsemester4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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