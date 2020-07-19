package com.practice.androidnetworking_lv0.level3.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.practice.androidnetworking_lv0.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class B1L3Fragment extends Fragment {
    private ListView lvContact;
    GetContact getContact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b1_l3, container, false);
        lvContact = view.findViewById(R.id.listView3);

        getContact = new GetContact(getActivity(), lvContact);
        getContact.execute();
        return view;
    }



}

class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public  HttpHandler(){

    }

    public String makeServiceCall(String reqUrl) {
        String res = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            res = convertStreamToString(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return res;
    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while ((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

class Contact {
    String id;
    String name, email, address, gender,mobile,home,office;
    public Contact(){
    }
    public Contact(String id, String name, String email, String address,
                   String gender, String mobile, String home, String office) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.mobile = mobile;
        this.home = home;
        this.office = office;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getHome() {
        return home;
    }
    public void setHome(String home) {
        this.home = home;
    }
    public String getOffice() {
        return office;
    }
    public void setOffice(String office) {
        this.office = office;
    }

}

class ContactAdapter extends BaseAdapter {

    Context context;
    ArrayList<Contact> contactList;
    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }
    @Override
    public int getCount() {
        return contactList.size();
    }
    @Override
    public Object getItem(int i) {
        return contactList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    public static class ViewHolder {
        TextView tvName, tvEmail, tvMobile;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, null);
            viewHolder.tvName = (TextView) view.findViewById(R.id.name);
            viewHolder.tvEmail = (TextView) view.findViewById(R.id.email);
            viewHolder.tvMobile = (TextView) view.findViewById(R.id.mobile);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Contact contact = contactList.get(i);
        viewHolder.tvName.setText(contact.getName());
        viewHolder.tvEmail.setText(contact.getEmail());
        viewHolder.tvMobile.setText(contact.getMobile());
        return view;
    }

}

class GetContact extends AsyncTask<Void, Void, Void> {
    private String TAG = B1L3Fragment.class.getSimpleName();
    public static String url = "http://192.168.43.188:80/nguyenduchai_pd03241/index.php";
    ArrayList<Contact> contactList;
    private ProgressDialog pDialog;
    private ListView lv;
    Context context;
    ContactAdapter adapter;
    public GetContact(Context context, ListView lv) {
        this.lv = lv;
        this.context = context;
        contactList = new ArrayList<>();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceCall(url);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                // Getting JSON Array node
                JSONArray contacts = jsonObject.getJSONArray("contacts");
                // looping through all Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");
                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");
                    Contact contact = new Contact();
                    contact.setId(id);
                    contact.setName(name);
                    contact.setEmail(email);
                    contact.setMobile(mobile);
                    contactList.add(contact);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        adapter = new ContactAdapter(context, contactList);
        lv.setAdapter(adapter);
    }
}