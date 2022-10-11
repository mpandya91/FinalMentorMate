package com.example.finalmentormate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.window.SplashScreen;

import com.example.finalmentormate.Fragments.chat;
import com.example.finalmentormate.Fragments.doubt;
import com.example.finalmentormate.Fragments.home;
import com.example.finalmentormate.Fragments.profile;
import com.example.finalmentormate.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout c;
    private BottomNavigationView navView;
    private Fragment f = null;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        navView = findViewById(R.id.nav_view);
        frame = findViewById(R.id.Framelayout);

        replaceFragment(new home());
        navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.tab_home:
                    replaceFragment(new home());
                    break;

                case R.id.tab_chat:
                    replaceFragment(new chat());
                    break;

                case R.id.tab_doubt:
                    replaceFragment(new doubt());
                    break;

                case R.id.tab_profile:
                    replaceFragment(new profile());
                    break;

            }

            return true;

        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Framelayout,fragment);
        fragmentTransaction.commit();
    }
}