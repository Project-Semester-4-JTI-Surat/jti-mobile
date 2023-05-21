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
    private SearchView searchView;

    private Fragment0 fragment0;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment5 fragment5;
    private MainViewModel mainModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mainModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainModel.initDataList();


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager1);
        fabs = findViewById(R.id.fab);
        myPreferences = new MyPreferences(this);

        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TambahSurat.class);
                startActivity(intent);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragment0 = new Fragment0();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();

        adapter.addFragment(fragment0, "Semua");
        adapter.addFragment(fragment1, "Menunggu");
        adapter.addFragment(fragment2, "Diproses");
        adapter.addFragment(fragment3, "Bisa Diambil");
        adapter.addFragment(fragment4, "Selesai");
        adapter.addFragment(fragment5, "Gagal");

        viewPager.setAdapter(adapter);
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

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");

        // Add text change listener to perform search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainModel.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainModel.search(newText);
                return true;
            }
        });

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
            // Menghapus Status login dan kembali ke Login Activity
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
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
//}