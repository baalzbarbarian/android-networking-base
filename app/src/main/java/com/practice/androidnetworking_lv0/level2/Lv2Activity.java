package com.practice.androidnetworking_lv0.level2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.practice.androidnetworking_lv0.R;
import com.practice.androidnetworking_lv0.level2.fragment.MainFragment;

public class Lv2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv2);

        loadFragment(new MainFragment());
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame2, fragment);
        transaction.commit();
    }
}