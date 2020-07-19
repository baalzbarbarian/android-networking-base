package com.practice.androidnetworking_lv0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.practice.androidnetworking_lv0.level1.Lv1Activity;
import com.practice.androidnetworking_lv0.level2.Lv2Activity;
import com.practice.androidnetworking_lv0.level3.Lv3Activity;

public class MainActivity extends AppCompatActivity {

    private Button btnLab1, btnLab2, btnLab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        moveFunc();
    }

    private void initView(){
        btnLab1 = findViewById(R.id.btnLab1);
        btnLab2 = findViewById(R.id.btnLab2);
        btnLab3 = findViewById(R.id.btnLab3);
    }

    private void moveFunc(){
        final Intent lv1 = new Intent(this, Lv1Activity.class);
        btnLab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(lv1);
            }
        });

        final Intent lv2 = new Intent(this, Lv2Activity.class);
        btnLab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(lv2);
            }
        });

        final Intent lv3 = new Intent(this, Lv3Activity.class);
        btnLab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(lv3);
            }
        });
    }

}