package com.tergech.nixon.mffapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Tonui on 9/24/2017.
 */

public class driver extends AppCompatActivity {
private String Frag_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver);

        String userId=SaveSharedPreference.getDriverID(driver.this);
        Toast.makeText(getApplicationContext(), "Driver ID "+userId, Toast.LENGTH_SHORT).show();
        if (userId!=null)
        {
            Fragment mFragment1 = new fragment_main_driver();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment1).commit();
        }
        else
        {
            Fragment mFragment = new fragment_car_details();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
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
