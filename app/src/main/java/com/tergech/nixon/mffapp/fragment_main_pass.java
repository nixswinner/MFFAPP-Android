package com.tergech.nixon.mffapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class fragment_main_pass extends Fragment {
    private EditText search;
    private Button btn_search;
    ListView lv;
    private ProgressDialog pDialog;
    private String url="http://192.168.137.1/Api/MFFAPP/public/index.php/api/getTrips/";
    String route_name;
    ArrayList<HashMap<String, String>> tripList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_main_pass, parent, false);

        tripList = new ArrayList<>();
        lv=(ListView) view.findViewById(R.id.listView);
        search=(EditText) view.findViewById(R.id.search);

        btn_search=(Button) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name=search.getText().toString();
                //search
                Toast.makeText(getActivity(),"Searching for route "+route_name,Toast.LENGTH_SHORT).show();
                try
                {
                    new getTrips().execute();;

                }catch (Exception ex)
                {
                    Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view ;
    }


    //.......................................Start of fetch from db................................................
    private class getTrips extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url+route_name);

            // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String routeName = c.getString("route_name");
                        String cost = c.getString("cost");
                        String _date = c.getString("_date");
                        String _time = c.getString("_time");
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("routeName", routeName);
                        contact.put("cost", cost);
                        contact.put("_date", _date);
                        contact.put("_time", _time);
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        tripList.add(contact);
                    }

                    Toast.makeText(getActivity(),
                            "Cost " ,
                            Toast.LENGTH_LONG)
                            .show();


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


            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), tripList,
                    R.layout.list_item, new String[]{"routeName", "cost","_date","_time"}, new int[]{
                    R.id.routeName, R.id.cost,R.id.txtDate,R.id.txtTime});

            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);




            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    //Toast.makeText(getActivity(),"You have selected  "+tripList.get(pos),Toast.LENGTH_LONG).show();
                    //hashmaps


                    try{
                        String food_name=tripList.get(pos).get("route_name");
                        String cost=tripList.get(pos).get("cost");
                       // alert(food_name);
                        Toast.makeText(getActivity(),"You have selected  "+food_name,Toast.LENGTH_SHORT).show();
                        String data=tripList.get(pos).toString();
                        //myarray[0]=calories;

                        //storing on Hashmaps
                       // mMap.put(food_name, cost);

                    }catch (Exception ex)
                    {
                        Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_LONG).show();
                    }

                }
            });


        }

    }
    //.......................................end of fetch from db................................................
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}