package com.tergech.nixon.mffapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class fragment_main_driver extends Fragment {
    private Button btn_add_service,btn_bookings;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_main_driver, parent, false);
        btn_add_service=(Button)view.findViewById(R.id.btn_add_service);
        btn_bookings=(Button)view.findViewById(R.id.btn_bookings);


        btn_add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft .replace(R.id.content,new fragment_add_service());
                ft .addToBackStack(null);
                ft  .commit();
            }
        });


        //bookings
        btn_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft .replace(R.id.content,new fragment_bookings());
                ft .addToBackStack(null);
                ft  .commit();

            }
        });
       /* //add service


        //bookings
        btn_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });*/






        return view ;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}