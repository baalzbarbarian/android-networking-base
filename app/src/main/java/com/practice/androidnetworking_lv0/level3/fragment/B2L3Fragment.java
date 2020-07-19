package com.practice.androidnetworking_lv0.level3.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.practice.androidnetworking_lv0.AppController;
import com.practice.androidnetworking_lv0.MainActivity;
import com.practice.androidnetworking_lv0.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class B2L3Fragment extends Fragment {

    // json object response url
    private String urlJsonObj =
            "http://192.168.43.188:80/nguyenduchai_pd03241/person_object.json";
    // json array response url
    private String urlJsonArry =
            "http://192.168.43.188:80/nguyenduchai_pd03241/person_array.json";
    private static String TAG = MainActivity.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;
    // progress dialog
    private ProgressDialog pDialog;
    private TextView txtResponse;
    // temprorary string to show the parsed response
    private String jsonResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b2_l3, container, false);

        btnMakeObjectRequest = view.findViewById(R.id.btnObjRequest);
        btnMakeArrayRequest = view.findViewById(R.id.btnArrRequest);
        txtResponse = view.findViewById(R.id.txtResponse);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeJsonObjectRequest();
            }
        });

        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeJsonArrayRequest();

            }
        });
        return view;
    }

    private void makeJsonArrayRequest() {
        showDialog();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, jsonArray.toString());
                        try {
                            // Parsing json array response
// loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject person = (JSONObject) jsonArray.get(i);
                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person.getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");
                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + email + "\n\n";
                                jsonResponse += "Home: " + home + "\n\n";
                                jsonResponse += "Mobile: " + mobile + "\n\n";
                            }
                            txtResponse.setText(jsonResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());

                Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    private void makeJsonObjectRequest() {
        showDialog();
        JsonObjectRequest jsonObjReq = new
                JsonObjectRequest(Request.Method.GET, urlJsonObj,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d(TAG, jsonObject.toString());
                try {
                    // Parsing json object response
// response will be a json object
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    JSONObject phone = jsonObject.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");
                    jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "Home: " + home + "\n\n";
                    jsonResponse += "Phone: " + mobile + "\n\n";
                    txtResponse.setText(jsonResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " +
                            e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                Toast.makeText(getActivity(), "Error: " +
                        volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }
    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }


}

