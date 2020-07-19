package com.practice.androidnetworking_lv0.level3.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.androidnetworking_lv0.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class B3L3Fragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_b3_l3, container, false);;
        initViews();

        return view;
    }

    private void initViews(){
        recyclerView = view.findViewById(R.id.card_recyler_view3);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();

    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.learn2crack.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call,
                                   Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new
                        ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }
}

class AndroidVersion {
    private String ver;
    private String name;
    private String api;
    public String getVer() {
        return ver;
    }
    public String getName() {
        return name;
    }
    public String getApi() {
        return api;
    }

}

class JSONResponse {
    private AndroidVersion[] android;
    public AndroidVersion[] getAndroid() {
        return android;
    }
}

interface RequestInterface {
    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}

class DataAdapter extends
        RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private ArrayList<AndroidVersion> android;
    public DataAdapter(ArrayList<AndroidVersion> android) {
        this.android = android;
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent,
                        false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv_name.setText(android.get(i).getName());
        viewHolder.tv_version.setText(android.get(i).getVer());
        viewHolder.tv_api_level.setText(android.get(i).getApi());
    }
    @Override
    public int getItemCount() {
        return android.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_version,tv_api_level;
        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_version = (TextView)view.findViewById(R.id.tv_version);
            tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);
        }
    }
}