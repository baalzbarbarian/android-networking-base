package com.practice.androidnetworking_lv0.level2.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.practice.androidnetworking_lv0.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class L2B1Fragment extends Fragment implements View.OnClickListener {

    public static final String SERVER_NAME = "http://192.168.43.188:80/nguyenduchai_pd03241/student_get.php";
    private EditText edtName, edtScore;
    private Button btnSend;
    private TextView txtResult;
    private View view;
    String name, score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_b1_lv2, container, false);
        initView();

        return view;
    }

    private void initView(){
        edtName = view.findViewById(R.id.edtName);
        edtScore = view.findViewById(R.id.edtScore);
        btnSend = view.findViewById(R.id.btnSend);
        txtResult = view.findViewById(R.id.txtResult);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSend:
                name = edtName.getText().toString();
                score = edtScore.getText().toString();
                BackgroundTask_GET backgroundTask_get = new BackgroundTask_GET(txtResult, name, score, getActivity());
                backgroundTask_get.execute();
                break;
        }
    }
}

class BackgroundTask_GET extends AsyncTask<Void, Void, Void> {
    String url = L2B1Fragment.SERVER_NAME;
    TextView txtResult;
    String strName, strScore;
    String str;
    ProgressDialog dialog;
    Context context;

    public BackgroundTask_GET(TextView txtResult, String strName, String strScore, Context context) {
        this.txtResult = txtResult;
        this.strName = strName;
        this.strScore = strScore;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        url += "?ten=" + this.strName + "&diem" + this.strScore;
        try {
            URL url2 = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bfr.readLine()) != null){
                sb.append(line);
            }
            str = sb.toString();
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Sending..");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(dialog.isShowing()){
            dialog.dismiss();
        }

        txtResult.setText(str);
    }
}