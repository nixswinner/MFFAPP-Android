package com.tergech.nixon.mffapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
        Intent intent = getIntent();
        String carStatus=intent.getStringExtra("carstatus");
        String userId=SaveSharedPreference.getDriverID(driver.this);
        int car_status=Integer.parseInt(carStatus);
        this.setTitle("My DashBoard");
        // Toast.makeText(getApplicationContext(), "Driver ID "+userId, Toast.LENGTH_SHORT).show();
        if (car_status>0)
        {
            Fragment mFragment1 = new fragment_main_driver();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment1).commit();
        }
        else
        {
            Fragment mFragment = new fragment_car_details();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();

            car_status+=1;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_settings)
        {
            SaveSharedPreference.clearUserName(this);
            Toast.makeText(driver.this,"Logging out...",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(driver.this,MainActivity.class);
            startActivity(intent);
            driver.this.finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
