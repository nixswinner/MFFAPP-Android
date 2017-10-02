package com.tergech.nixon.mffapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragment_bookings extends Fragment {
    private ProgressDialog pDialog;
    private static final String url= "http://192.168.137.1/Api/MFFAPP/public/index.php/api/loginUser";
    ListView lv_orders,lv;
    ArrayList<HashMap<String, String>> booking_list;

    //Map mMap = new HashMap();
    HashMap<String,String> mMap = new HashMap<String,String >();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_bookings, parent, false);


      //show data from the database of the bookings


        fetch_order("8");


        return view ;
    }


    private void fetch_order( final String userID) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        String _id="/"+userID;
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url+_id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Register Response: " + response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);
                    // Getting JSON Array node
                    JSONArray contacts = jObj.getJSONArray("result");
                    String pass_id="";
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                         pass_id=c.getString("pass_id");
                        //food_name ="\n" +name;


                    }
                    Toast.makeText(getActivity(),"Passenger id "+pass_id,Toast.LENGTH_SHORT).show();




                } catch (JSONException e) {
                    e.printStackTrace();
                   /* Toast.makeText(getActivity(),
                            "Error Loggin Check your details ", Toast.LENGTH_LONG).show();*/
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID", userID);
                return params;
            }
        };
        // Adding request to request queue
        // requestQueue.add(strReq);
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq,cancel_req_tag);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}