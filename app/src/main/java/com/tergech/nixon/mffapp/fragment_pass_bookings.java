package com.tergech.nixon.mffapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class fragment_pass_bookings extends Fragment {
    private String pass_id;
    private ProgressDialog pDialog;
    private static final String url= "http://139.162.42.154/app/meetff/public/index.php/api/getPassBookings";
    private static final String url_cancle_booking= "http://139.162.42.154/app/meetff/public/index.php/api/cancleABooking";
    ListView lv;
    TextView txtJourney,txtCost,txtPassNo,txtDepatureTime,txtCancle,txtPickUpStation;
    Button findARide,MyBooking;
    CardView cardBooking;
    private String route_name="",cost="",departure_time="",no_of_pass="",pick_up="";



    //Map mMap = new HashMap();
    HashMap<String,String> mMap = new HashMap<String,String >();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragmentpassenger, parent, false);
        cardBooking=(CardView)view.findViewById(R.id.cardBooking);
        txtCost=(TextView)view.findViewById(R.id.txtCost);
        txtDepatureTime=(TextView)view.findViewById(R.id.txtTime);
        txtJourney=(TextView)view.findViewById(R.id.txtrouteName);
        txtPassNo=(TextView)view.findViewById(R.id.txtPassNo);
        txtCancle=(TextView)view.findViewById(R.id.txtcancle);
        findARide=(Button)view.findViewById(R.id.find_ride);
        MyBooking=(Button)view.findViewById(R.id.myBookings);
        txtPickUpStation=(TextView)view.findViewById(R.id.txtPickup);


        pass_id=SaveSharedPreference.getPassID(getContext());
        //get passenger bookings
        new getPassBookings().execute();
        Log.e("Pass id "," "+pass_id);
//cancle booking
txtCancle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        // builderSingle.setIcon(R.drawable.green_tick_add);
        builderSingle.setTitle("Cancle Booking");
        builderSingle.setMessage("Are you sure you want to cancle this Booking?");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //method to cancle booking
                new CancleMYPassBookings().execute();
               Toast.makeText(getActivity(),"Cancled successfully",Toast.LENGTH_SHORT).show();
            }
        });


        builderSingle.show();

    }
});
    findARide.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fragment_main_pass nextFrag= new fragment_main_pass();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, nextFrag,"findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    });


    //view my Bookings
        MyBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {

                    if (route_name.isEmpty())
                    {
                        cardBooking.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"You do not have a booking please book now by clicking on Find a Ride",Toast.LENGTH_LONG).show();
                    }else
                    {
                        cardBooking.setVisibility(View.VISIBLE);
                    }



                }catch (Exception ex)
                {
                    Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_SHORT).show();
                }

            }
        });
        //fetch_bookings(userid);


        return view ;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    //.......................................Start of fetch from db................................................
    private class getPassBookings extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading your bookings ");
            pDialog.setCancelable(false);
            pDialog.show();
            //clear
            route_name="";cost="";departure_time="";no_of_pass="";pick_up="";

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting responsell
            String jsonStr = sh.makeServiceCall(url+"/"+pass_id);


            // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    Log.e("Response "," "+jsonObj);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");
                    Log.e("Response  Object"," "+contacts);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                         route_name = c.getString("route_name");
                         cost = c.getString("cost");
                         departure_time = c.getString("_time");
                         no_of_pass = c.getString("no_of_pass");
                         pick_up=c.getString("pick_up");




                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        Log.e("Pick ",pick_up);
                    }




                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "" + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                // Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Error Occured Please Try again",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            txtCost.setText(cost);
            txtDepatureTime.setText(departure_time);
            txtJourney.setText(route_name);
            txtPassNo.setText(no_of_pass);
            txtPickUpStation.setText(pick_up);

        }

    }

    //.......................................Start of fetch from db................................................
    private class CancleMYPassBookings extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cancling your bookings ");
            pDialog.setCancelable(false);
            pDialog.show();

            //clear
            route_name="";cost="";departure_time="";no_of_pass="";
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting responsell
            String jsonStr = sh.makeServiceCall(url_cancle_booking+"/"+pass_id);


            // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    Log.e("Response "," "+jsonObj);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");


                    Toast.makeText(getActivity(),"Your booking has been Cancled Successfully.You can book again by clicking on a Find A Ride Thank You",Toast.LENGTH_SHORT).show();

                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Error " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                // Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Error Occured Please Try Again",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            //dismise the card
            cardBooking.setVisibility(View.GONE);

        }

    }

}