package com.tergech.nixon.mffapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Tonui on 9/24/2017.
 */

public class driver extends AppCompatActivity {
private String Frag_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String status =bundle.getString("status");

        switch (status)
        {
            case "0":
                Fragment mFragment = new fragment_car_details();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
                break;
            default:
                Fragment mFragment1 = new fragment_main_driver();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment1).commit();

        }

    /*    Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Frag_no=bundle.getString("frag_no");
       try {

           if (Frag_no=="one")
           {

           }
       }catch (Exception ex)
       {
           Toast.makeText(getApplicationContext(), "Error "+ex, Toast.LENGTH_LONG).show();
       }*/


    }
}
