package com.tergech.nixon.mffapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class fragment_car_details extends Fragment {
    //api address to push data
    private final String  URL_FOR_POST="http://139.162.42.154/app/meetff/public/index.php/api/addCarDetails";
    private EditText edt_car_type,edt_car_no_plate,edt_car_color,edt_pass_no ;
    private Button btnSave;
    private static String driver_id,car_type,car_no_plate,car_color,car_pass_no;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_car_details, parent, false);

        // Progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        edt_car_type=(EditText)view.findViewById(R.id.input_car_type);
        edt_car_no_plate=(EditText)view.findViewById(R.id.input_car_no_plate);
        edt_car_color=(EditText)view.findViewById(R.id.input_car_color);
       // edt_pass_no=(EditText)view.findViewById(R.id.input_pass_no);

        btnSave=(Button)view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //get the edit text values
                car_type=edt_car_type.getText().toString();
                car_no_plate=edt_car_no_plate.getText().toString();
                car_color=edt_car_color.getText().toString();
               // car_pass_no=edt_pass_no.getText().toString();

                if (car_type.isEmpty() && car_no_plate.isEmpty() && car_color.isEmpty())
                {
                    //method to post data to the server
                    Toast.makeText(getActivity(),"Please enter all the fields above",Toast.LENGTH_LONG).show();


                }
                else
                {
                    postData();
                    fragment_main_driver nextFrag= new fragment_main_driver();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, nextFrag,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    getActivity().finish();

                }

            }
        });




        return view ;
    }

    //method to post data to the server


    private void postData() {


         driver_id=SaveSharedPreference.getDriverID(getActivity());
        post_car_details(driver_id,car_type,car_no_plate,car_color);
        //saving phone number
        //SaveSharedPreference.setPhoneNumber(getActivity(),signupInputPhoneNumber.getText().toString());
    }

    private void post_car_details(final String driver_id,final String car_type,final String car_no_plate, final String car_color) {
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
                    Log.e("Response "," "+response);

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getActivity(), "Your car details successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                getActivity(),
                                LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        Log.e("Error "," "+errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
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
                params.put("type", car_type);
                params.put("no_plate", car_no_plate);
                params.put("color", car_color);
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