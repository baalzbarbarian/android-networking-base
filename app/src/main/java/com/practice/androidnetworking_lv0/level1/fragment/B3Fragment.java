package com.practice.androidnetworking_lv0.level1.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import java.io.InputStream;
import java.net.URL;

public class B3Fragment extends Fragment implements View.OnClickListener, Listener {

    TextView txt;
    Button btn;
    ImageView img;
    String IMAGE_URL = "http://i64.tinypic.com/28vaq8k.png";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b3, container, false);

        txt = view.findViewById(R.id.mess);
        img = view.findViewById(R.id.img);
        btn =view.findViewById(R.id.btn);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
        txt.setText("Image Downloaded");
    }

    @Override
    public void onError() {
        txt.setText("Error download image");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                new LoadImageTask(this,getActivity()).execute(IMAGE_URL);
                break;
        }
    }
}

//Khởi tạo interface
interface Listener {
    void onImageLoaded(Bitmap bitmap);
    void onError();
}

//Tạo class để load ảnh.
class LoadImageTask extends AsyncTask<String, Void, Bitmap>{

    private Listener mListener;
    private ProgressDialog progressDialog;

    public LoadImageTask(Listener listener, Context context) {
        mListener = listener;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Dowloading image...");
        progressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(bitmap != null){
            mListener.onImageLoaded(bitmap);
        }else {
            mListener.onError();
        }
    }
}