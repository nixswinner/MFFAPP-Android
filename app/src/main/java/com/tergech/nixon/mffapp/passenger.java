package com.tergech.nixon.mffapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Tonui on 9/24/2017.
 */

public class passenger extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger);


        Fragment mFragment = new fragment_main_pass();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();

    }
}
