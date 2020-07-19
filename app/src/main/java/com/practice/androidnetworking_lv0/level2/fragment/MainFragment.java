package com.practice.androidnetworking_lv0.level2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.practice.androidnetworking_lv0.R;


public class MainFragment extends Fragment {

    private Button btn1, btn2, btn3, btn4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);

        moveFunc();

        return view;
    }

    private void moveFunc(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new L2B1Fragment());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new L2B2Fragment());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new L2B3Fragment());
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new L2B4Fragment());
            }
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame2, fragment);
        transaction.commit();
    }

}