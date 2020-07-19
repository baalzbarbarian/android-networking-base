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

public class L2B4Fragment extends Fragment implements View.OnClickListener {

    public static final String SERVER_NAME = "http://192.168.43.188:80/nguyenduchai_pd03241/gpt_POST.php";
    private EditText edtA, edtB, edtC;
    private Button btnTT;
    private TextView txtResult;
    String strA, strB, strC;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_l2_b4, container, false);
        edtA = view.findViewById(R.id.edtA);
        edtB = view.findViewById(R.id.edtB);
        edtC = view.findViewById(R.id.edtC);
        txtResult = view.findViewById(R.id.txtResult);
        btnTT = view.findViewById(R.id.btnTT);
        btnTT.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        strA = edtA.getText().toString();
        strB = edtB.getText().toString();
        strC = edtC.getText().toString();
        BackgroundTask_POST4 backgroundTask_post4 = new BackgroundTask_POST4(getActivity(), txtResult);
        backgroundTask_post4.execute(strA, strB, strC);
    }
}

class BackgroundTask_POST4 extends AsyncTask<String, Void, Void> {

    String dd = L2B4Fragment.SERVER_NAME;
    Context context;
    TextView txtKq;
    String strKq;
    ProgressDialog dialog;

    public BackgroundTask_POST4(Context context, TextView txtKq) {
        this.context = context;
        this.txtKq = txtKq;
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

    @Override
    protected Void doInBackground(String... params) {

        try {
            URL url = new URL(dd);
            String param = "a=" + URLEncoder.encode(params[0].toString(), "utf-8") +
                    "&b=" + URLEncoder.encode(params[1].toString(), "utf-8") +
                    "&c=" + URLEncoder.encode(params[2].toString(), "utf-8");

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
            strKq = sb.toString();
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
        txtKq.setText(strKq);
    }
}