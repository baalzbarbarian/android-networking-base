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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class L2B2Fragment extends Fragment implements View.OnClickListener {

    public static final String SERVER_NAME = "http://192.168.43.188:80/nguyenduchai_pd03241/rectangle_POST.php";
    private EditText edtCd, edtCr;
    private Button btnTT;
    private TextView txtResult;
    String strCd, strCr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_l2_b2, container, false);
        edtCd = view.findViewById(R.id.edtCd);
        edtCr = view.findViewById(R.id.edtCr);
        btnTT = view.findViewById(R.id.btnTT);
        txtResult = view.findViewById(R.id.txtResult);
        btnTT.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTT:
                strCr = edtCr.getText().toString();
                strCd = edtCd.getText().toString();
                BackgroundTask_POST backgroundTaskPost = new BackgroundTask_POST(getActivity(), strCr, strCd, txtResult);
                backgroundTaskPost.execute();
                break;
        }
    }
}

class BackgroundTask_POST extends AsyncTask<Void, Void, Void>{
    String dd = L2B2Fragment.SERVER_NAME;
    Context context;
    String strCr, strCd;
    TextView txtResult;
    ProgressDialog dialog;
    String strResult;

    public BackgroundTask_POST(Context context, String strCr, String strCd, TextView txtResult) {
        this.context = context;
        this.strCr = strCr;
        this.strCd = strCd;
        this.txtResult = txtResult;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL(dd);
            String param = "cr=" + URLEncoder.encode(strCr, "utf-8") + "&cd="
                    + URLEncoder.encode(strCd, "utf-8");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(param.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
            printWriter.print(param);
            printWriter.close();

            String line = "";
            BufferedReader bfr = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while ((line = bfr.readLine()) != null){
                sb.append(line);
            }
            strResult = sb.toString();
            urlConnection.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        txtResult.setText(strResult);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Caculating...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}