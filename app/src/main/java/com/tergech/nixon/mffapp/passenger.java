package com.tergech.nixon.mffapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Tonui on 9/24/2017.
 */

public class passenger extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger);
        this.setTitle("My DashBoard");


        Fragment mFragment = new fragment_pass_bookings();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();

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
            Toast.makeText(passenger.this,"Logging out...",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(passenger.this,MainActivity.class);
            startActivity(intent);
            passenger.this.finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
