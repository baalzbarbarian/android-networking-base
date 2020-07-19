package com.practice.androidnetworking_lv0.level1.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.practice.androidnetworking_lv0.R;

import java.io.IOException;
import java.net.URL;

public class B1Fragment extends Fragment implements View.OnClickListener {

    Button btnb1;
    ImageView img;
    TextView mess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b1, container, false);

        btnb1 = view.findViewById(R.id.btnb1);
        img = view.findViewById(R.id.imgView);
        mess = view.findViewById(R.id.mess1);

        btnb1.setOnClickListener(this);

        return view;
    }

    private Bitmap loadImageFromNetWork(String link){
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(link);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    @Override
    public void onClick(View view) {
        final Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = loadImageFromNetWork("http://i64.tinypic.com/28vaq8k.png");
                img.post(new Runnable() {
                    @Override
                    public void run() {
                        mess.setText("Image Downloaded");
                        img.setImageBitmap(bitmap);
                    }
                });
            }
        });
        myThread.start();
    }
}