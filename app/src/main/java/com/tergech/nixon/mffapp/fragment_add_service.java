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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class fragment_add_service extends Fragment {
    Button btn_addservice;
    EditText edt_cost,edt_pass,edt_route,edt_date,edt_time,edt_pickup;
    TextView txtDate,txtTime;
    ProgressDialog progressDialog;
    private static String departuretime;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("hh:mm aa MMMM dd yyyy ");
    //http://code-one.byethost5.com/mffapp/public/index.php/api/getTrips/Nyeri-Nairobi
    //api endpoint
    private final String  URL_FOR_POST="http://139.162.42.154/app/meetff/public/index.php/api/addMyService";
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            /*Toast.makeText(getActivity(),
                    mFormatter.format(date), Toast.LENGTH_SHORT).show();*/
            departuretime=""+mFormatter.format(date);
            txtTime.setText(departuretime);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_add_service, parent, false);

       /* getActivity().setTitle("Add a Service");*/
        // Progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        btn_addservice=(Button) view.findViewById(R.id.btn_addservice);

        edt_cost=(EditText)view.findViewById(R.id.input_cost);
        edt_pass=(EditText)view.findViewById(R.id.input_pass_capacity);
        edt_route=(EditText)view.findViewById(R.id.input_route);
        txtDate=(TextView)view.findViewById(R.id.input_date);
        txtTime=(TextView)view.findViewById(R.id.input_time);
        edt_pickup=(EditText)view.findViewById(R.id.input_pickup_station);

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // txtTime.setText("0000");
                new SlideDateTimePicker.Builder(getChildFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .build()
                        .show();

            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDate.setText("00/00/0000");

            }
        });

        final String driverID=SaveSharedPreference.getDriverID(getActivity());
        btn_addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_route.getText().toString().isEmpty()&&
                        edt_cost.getText().toString().isEmpty()&&
                        edt_pass.getText().toString().isEmpty()&&
                        edt_pickup.getText().toString().isEmpty()
                        )
                {
                    Toast.makeText(getActivity(),"Error: You must enter all the details to add service",Toast.LENGTH_SHORT).show();

                }
                else
                {

                    Toast.makeText(getActivity(), "Adding Service", Toast.LENGTH_SHORT).show();
                    addService(driverID,edt_route.getText().toString(),
                            edt_cost.getText().toString(),
                            edt_pass.getText().toString(),
                            "00/00/0000",
                            departuretime,
                            edt_pickup.getText().toString()
                    );
                    fragment_main_driver nextFrag= new fragment_main_driver();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, nextFrag,"findThisFragment")
                            .addToBackStack(null)
                            .commit();

                }

            }
        });





        return view ;
    }


    private void addService(final String driver_id,  final String route_name,final String cost, final String no_of_pass,final String date,final String time,final String pick_up) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";
        progressDialog.setMessage("Adding Service ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //Toast.makeText(getActivity(), "Response "+response.toString(), Toast.LENGTH_SHORT).show();

                hideDialog();
                //Toast.makeText(getActivity(), "Response "+response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");


                    // String user = jObj.getJSONObject("user").getString("name");
                    // Toast.makeText(getActivity(), "Hi " + user +", You are successfully Registered!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                    Toast.makeText(getActivity(), "Service Add Successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(
                                getActivity(),
                                driver.class);
                        startActivity(intent);
                        getActivity().finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                   /* Toast.makeText(getActivity(),
                            "Error Occured Try again"+e, Toast.LENGTH_LONG).show();*/
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
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("driver_id", driver_id);
                params.put("route_name", route_name);
                params.put("cost", cost);
                params.put("status", "1");
                params.put("no_of_pass", no_of_pass);
                params.put("date", date);
                params.put("time", time);
                params.put("pick_up", pick_up);
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