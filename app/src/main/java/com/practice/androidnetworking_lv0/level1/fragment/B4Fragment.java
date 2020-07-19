package com.practice.androidnetworking_lv0.level1.fragment;

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

public class B4Fragment extends Fragment implements View.OnClickListener {

    EditText edt;
    Button btn;
    TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b4, container, false);

        edt = view.findViewById(R.id.edt);
        btn = view.findViewById(R.id.btn);
        txt = view.findViewById(R.id.txt);

        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(getActivity(), txt, edt);
                String sleepTime = edt.getText().toString();
                asyncTaskRunner.execute(sleepTime);
                break;
        }
    }
}

class AsyncTaskRunner extends AsyncTask<String, String, String>{

    private String resp;
    ProgressDialog dialog;
    TextView tvResult;
    EditText time;
    Context context;

    public AsyncTaskRunner(Context context, TextView tvResult, EditText time){
        this.tvResult = tvResult;
        this.context = context;
        this.time = time;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "ProgressDialog", "Wait for..." + time.getText().toString() + " seconds");
    }

    @Override
    protected String doInBackground(String... strings) {

        publishProgress("Sleeping...");
        try {
            int time = Integer.parseInt(strings[0]) * 1000;
            Thread.sleep(time);
            resp = "Slept for " + strings[0] + " seconds";
        }catch (Exception e) {
            e.printStackTrace();
            resp = e.getMessage();
        }

        return resp;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
        tvResult.setText(s);
    }
}