package com.tergech.nixon.mffapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class fragment_add_service extends Fragment {
    //api address to push data
    private final String  URL_FOR_POST="";
    private Button btnAddservice;
    private Button btn_time;
    private Button btn_date;
    private EditText edt_cost,edt_pass_capacity,edt_time,edt_date;
    private TextView txtroute;
    private String Cost,Pass_Capacity,Route,Route_Id,_Date,_Time,driver_id;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_add_service, parent, false);
        btnAddservice=(Button)view.findViewById(R.id.btn_addservice);
       /* btn_date=(Button)view.findViewById(R.id.btn_date);
        btn_time=(Button)view.findViewById(R.id.btn_time);*/

        // Progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
/*
        edt_cost=(EditText)view.findViewById(R.id.input_layout_cost);
        edt_pass_capacity=(EditText)view.findViewById(R.id.input_pass_capacity);
        edt_date=(EditText)view.findViewById(R.id.input_layout_date);
        edt_time=(EditText)view.findViewById(R.id.input_layout_time);

        txtroute=(TextView)view.findViewById(R.id.txtroute);*/

        btnAddservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* //get the edit text values
                Cost=edt_cost.getText().toString();
                Pass_Capacity=edt_pass_capacity.getText().toString();
                _Date=edt_date.getText().toString();
                _Time=edt_time.getText().toString();
                if (Cost!=null && Pass_Capacity!=null && _Date!=null && _Time!=null)
                {
                    //method to post data to the server
                    //postData();
                }
                else
                {
                    Toast.makeText(getActivity(),"Please enter all the fields above",Toast.LENGTH_LONG).show();
                }*/
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Saving...");
                progressDialog.show();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft .replace(R.id.content,new fragment_main_driver());
                                ft .addToBackStack(null);
                                ft  .commit();

                            }
                        }, 3000);



            }
        });






        return view ;
    }



//method to post data to the server


    private void postData() {

        post_my_service(driver_id,Route_Id,Cost,Pass_Capacity,_Date,_Time);
        //saving phone number
        //SaveSharedPreference.setPhoneNumber(getActivity(),signupInputPhoneNumber.getText().toString());
    }

    private void post_my_service(final String driver_id, final String route_Id,final String cost,final String no_of_pass, final String date,final String time) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Saving Data");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                //Toast.makeText(getActivity(), "Response "+response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getActivity(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        /*Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();*/
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to car details url
                Map<String, String> params = new HashMap<String, String>();
                params.put("driver_id", driver_id);
                params.put("route_id", route_Id);
                params.put("cost", cost);
                params.put("no_of_pass", no_of_pass);
                params.put("date", date);
                params.put("time", time);
                return params;
            }
        };
        // Adding request to request queue
        //requestQueue.add(strReq);
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
                                }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}