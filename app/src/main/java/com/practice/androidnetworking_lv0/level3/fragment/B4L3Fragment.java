package com.practice.androidnetworking_lv0.level3.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.practice.androidnetworking_lv0.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class B4L3Fragment extends Fragment {
    private ListView listView;
    private View parentView;
    private ArrayList<Contact2> contactList;
    private MyContactAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b4_l3, container, false);

        parentView = view.findViewById(R.id.parentLayout);

        contactList = new ArrayList<>();

        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {
                Snackbar.make(parentView,
                        contactList.get(position).getName() + " => " +
                                contactList.get(position).getPhone().getHome(),
                        Snackbar.LENGTH_LONG).show();
            }
        });

        Toast toast = Toast.makeText(getActivity(),
                R.string.string_click_to_load, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        FloatingActionButton fab = (FloatingActionButton)
                view.findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((InternetConnection.checkConnection(getActivity()))){
                    final ProgressDialog dialog;
                    dialog = new ProgressDialog(getActivity());

                    dialog.setTitle(getString(R.string.string_getting_json_title));

                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();
                    //Creating an object of our api interface
                    ApiService api = RetroClient.getApiService();
                    // Calling JSON
                    Call<ContactList> call = api.getMyJSON();
                    call.enqueue(new Callback<ContactList>() {
                        @Override
                        public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                            //Dismiss Dialog
                            dialog.dismiss();
                            if(response.isSuccessful()) {
                                // Got Successfully
                                contactList = response.body().getContacts();
                                // Binding that List to Adapter
                                adapter = new
                                        MyContactAdapter(getActivity(), contactList);
                                listView.setAdapter(adapter);
                            } else {
                                Snackbar.make(parentView,
                                        R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ContactList> call, Throwable throwable) {
                            dialog.dismiss();
                        }
                    });
                }else {
                    Snackbar.make(parentView,
                            R.string.string_internet_connection_not_available,
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return view;

    }


}
    class Contact2 {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("profile_pic")
        @Expose
        private String profilePic;
        @SerializedName("phone")
        @Expose
        private Phone phone;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return The address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address The address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return The gender
         */
        public String getGender() {
            return gender;
        }

        /**
         * @param gender The gender
         */
        public void setGender(String gender) {
            this.gender = gender;
        }

        /**
         * @return The profilePic
         */
        public String getProfilePic() {
            return profilePic;
        }

        /**
         * @param profilePic The profile_pic
         */
        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        /**
         * @return The phone
         */
        public Phone getPhone() {
            return phone;
        }

        /**
         * @param phone The phone
         */
        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public class Phone {
            @SerializedName("mobile")
            @Expose
            private String mobile;
            @SerializedName("home")
            @Expose
            private String home;
            @SerializedName("office")
            @Expose
            private String office;

            /**
             * @return The mobile
             */
            public String getMobile() {
                return mobile;
            }

            /**
             * @param mobile The mobile
             */
            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            /**
             * @return The home
             */
            public String getHome() {
                return home;
            }

            /**
             * @param home The home
             */
            public void setHome(String home) {
                this.home = home;
            }

            /**
             * @return The office
             */
            public String getOffice() {
                return office;
            }

            /**
             * @param office The office
             */
            public void setOffice(String office) {
                this.office = office;
            }
        }
    }

    class ContactList {
        @SerializedName("contacts")
        @Expose
        private ArrayList<Contact2> contacts = new ArrayList<>();

        /**
         * @return The contacts
         */
        public ArrayList<Contact2> getContacts() {
            return contacts;
        }

        /**
         * @param contacts The contacts
         */
        public void setContacts(ArrayList<Contact2> contacts) {
            this.contacts = contacts;
        }
    }

    interface ApiService {
        /*
     Retrofit get annotation with our URL
     And our method that will return us the List of ContactList
     */
        @GET("/json_data.json")
        Call<ContactList> getMyJSON();
    }

    class RetroClient {
        private static final String ROOT_URL = "http://pratikbutani.x10.mx";

        /**
         * Get Retrofit Instance
         */
        private static Retrofit getRetrofitInstance() {
            return new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        /**
         * Get API Service
         *
         * @return API Service
         */
        public static ApiService getApiService() {
            return getRetrofitInstance().create(ApiService.class);
        }
    }

    class MyContactAdapter extends ArrayAdapter<Contact2> {
        List<Contact2> contactList;
        Context context;
        private LayoutInflater mInflater;

        // Constructors
        public MyContactAdapter(Context context, List<Contact2> objects) {
            super(context, 0, objects);
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
            contactList = objects;
        }

        @Override
        public Contact2 getItem(int position) {
            return contactList.get(position);
        }

        private static class ViewHolder {
            public final RelativeLayout rootView;
            public final ImageView imageView;
            public final TextView textViewName;
            public final TextView textViewEmail;

            private ViewHolder(RelativeLayout rootView, ImageView imageView,
                               TextView textViewName, TextView textViewEmail) {
                this.rootView = rootView;
                this.imageView = imageView;
                this.textViewName = textViewName;
                this.textViewEmail = textViewEmail;
            }

            public static ViewHolder create(RelativeLayout rootView) {
                ImageView imageView = (ImageView)
                        rootView.findViewById(R.id.imageView);
                TextView textViewName = (TextView)
                        rootView.findViewById(R.id.textViewName);
                TextView textViewEmail = (TextView)
                        rootView.findViewById(R.id.textViewEmail);
                return new ViewHolder(rootView, imageView, textViewName,
                        textViewEmail);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                View view = mInflater.inflate(R.layout.layout_row_view, parent,
                        false);
                vh = ViewHolder.create((RelativeLayout) view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            Contact2 item = getItem(position);
            vh.textViewName.setText(item.getName());
            vh.textViewEmail.setText(item.getEmail());

            Picasso.with(context).load(item.getProfilePic()).placeholder(R.drawable.ic_launcher_background).error(R.mipmap.ic_launcher).into(vh.imageView);
            return vh.rootView;
        }
    }

    class InternetConnection {
        public static boolean checkConnection(@NonNull Context context) {
            return ((ConnectivityManager) context.getSystemService
                    (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() !=
                    null;
        }
    }
