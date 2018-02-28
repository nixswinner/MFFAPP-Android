package com.tergech.nixon.mffapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class fragment_bookings extends Fragment {
    private String driver_id,id="",name="",phone="";
    private ProgressDialog pDialog;
    private static final String url= "http://139.162.42.154/app/meetff/public/index.php/api/getBookings";
    ListView lv;
    ArrayList<HashMap<String, String>> booking_list;
    TextView txtBookings;
    Button btn_call;

    //Map mMap = new HashMap();
    HashMap<String,String> mMap = new HashMap<String,String >();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_bookings, parent, false);

        txtBookings=(TextView)view.findViewById(R.id.noBooking);
        booking_list = new ArrayList<>();
        lv=(ListView) view.findViewById(R.id.listView);


        pDialog = new ProgressDialog(getActivity());

        driver_id=SaveSharedPreference.getDriverID(getContext());
      //show data from the database of the bookings


        //fetch_bookings(userid);
        try
        {
            new getPassBookings().execute();



        }catch (Exception ex)
        {
            Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_SHORT).show();
        }

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
          /*  pDialog = new ProgressDialog(getActivity());*/
            pDialog.setMessage("Loading your bookings ");
            pDialog.setCancelable(false);
            //pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting responsell
            String jsonStr = sh.makeServiceCall(url + "/" + driver_id);


            // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        id = c.getString("id");
                        name = c.getString("name");
                        phone = c.getString("phone");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("phone", phone);

                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        booking_list.add(contact);
                        Log.e("Pass name ", name);
                    }


                    pDialog.hide();


                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
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
                                "Couldn't get json from server. Check LogCat for possible errors!",
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


            {
                if (name.isEmpty() || name=="")
                {
                    txtBookings.setText("No bookings available now ");
                }
              /*  SimpleAdapter k=new SimpleAdapter(
                        getActivity(), booking_list,
                        R.layout.bookings_item_list, new String[]{"name", "phone"}, new int[]{
                        R.id.pass_name, R.id.pass_phone})
                {
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        View v=getView(position, convertView, parent);
                        btn_call=(Button)v.findViewById(R.id.btn_call);
                        btn_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String x=booking_list.get(position).get("name");
                                Toast.makeText(getActivity(),"Clicked "+x,Toast.LENGTH_SHORT).show();

                            }
                        });


                        return v;
                    }
                };*/

                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), booking_list,
                        R.layout.bookings_item_list, new String[]{"name", "phone"}, new int[]{
                        R.id.pass_name, R.id.pass_phone});

                lv.setAdapter(adapter);
                lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String name=booking_list.get(position).get("name");
                        final String phone=booking_list.get(position).get("phone");
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                        // builderSingle.setIcon(R.drawable.green_tick_add);
                        builderSingle.setTitle("Calling the Passenger");
                        builderSingle.setMessage("Do you want to call this Passenger "+name+" ?");

                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderSingle.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //make a call


                                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

                                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(
                                            getActivity(),
                                            new String[]{Manifest.permission.CALL_PHONE},123);
                                } else {
                                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+phone)));
                                }
                            }
                        });


                        builderSingle.show();



                    }
                });



                /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        try {
                        String name=booking_list.get(pos).get("name");
                        String phone=booking_list.get(pos).get("phone");
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                        // builderSingle.setIcon(R.drawable.green_tick_add);
                        builderSingle.setTitle("Calling "+name);
                        builderSingle.setMessage("Do you want to call "+name+" ?");

                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderSingle.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //make a call


                            }
                        });


                        builderSingle.show();


                            //myarray[0]=calories;

                            //storing on Hashmaps
                            // mMap.put(food_name, cost);

                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Error " + ex, Toast.LENGTH_LONG).show();
                        }

                    }
                });
*/

            }

        }

    }}