package com.tergech.nixon.mffapp;

/**
 * Created by Tonui on 6/25/2017.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    //String baseurl=getString(R.string.base_Url);
    //api url address
    private static final String URL_FOR_LOGIN = "http://139.162.42.154/app/meetff/public/index.php/api/loginUser";
    ProgressDialog progressDialog;
    private EditText loginInputEmail, loginInputPassword;
    private Button btnlogin;
    private Button btnLinkSignup;
    private  String who=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginInputEmail = (EditText) findViewById(R.id.login_input_email);
        loginInputPassword = (EditText) findViewById(R.id.login_input_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnLinkSignup = (Button) findViewById(R.id.btn_link_signup);
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        who=bundle.getString("name");
        this.setTitle("Login");




        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(InternetConnection.checkConnection(getApplicationContext()))
                {
                    *//*loginUser(loginInputEmail.getText().toString(),
                            loginInputPassword.getText().toString());*//*
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enable your Internet connection!!", Toast.LENGTH_SHORT).show();
                }*/

               /* final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Login you in ...");
                progressDialog.show();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                Intent i = new Intent(getApplicationContext(),driver.class);
                                startActivity(i);

                            }
                        }, 3000);*/
               if (loginInputEmail.getText().toString().trim().length()==0 && loginInputPassword.getText().toString().trim().length()==0)
               {
                   Toast.makeText(LoginActivity.this,"You must enter an email and password",Toast.LENGTH_SHORT).show();
               }else
               {
                   loginUser(loginInputEmail.getText().toString(),
                           loginInputPassword.getText().toString());
               }


            }
        });

        btnLinkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(LoginActivity.this);
                // builderSingle.setIcon(R.drawable.green_tick_add);
                builderSingle.setTitle("Join as..");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.select_dialog_item);
                arrayAdapter.add("A Driver");
                arrayAdapter.add("A Passenger");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        switch (strName)
                        {
                            case "A Driver":
                                Intent intent=new Intent(LoginActivity.this,RegisterActivityDriver.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                                break;
                            case "A Passenger":
                                Intent intent1=new Intent(LoginActivity.this,RegisterActivity.class);
                                startActivity(intent1);
                                LoginActivity.this.finish();
                                break;
                        }

                    }
                });
                builderSingle.show();

            }
        });
    }

    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    String  user_id="",name="";
                    JSONObject jObj = new JSONObject(response);
                    // Getting JSON Array node
                    JSONArray contacts = jObj.getJSONArray("result");

                    // looping through All Contacts
                    JSONObject c = contacts.getJSONObject(0);
                 /*   for (int i = 0; i < contacts.length(); i++) {




                    }*/
                    user_id = c.getString("id");
                    name = c.getString("name");

                    Toast.makeText(getApplicationContext(),
                            "Welcome "+name, Toast.LENGTH_SHORT).show();

                    // Launch User activity

                       /* Intent intent = new Intent(
                                LoginActivity.this,
                                MainActivity.class);
                        intent.putExtra("username", usertype);
                        startActivity(intent);
                        finish();*/
                       switch (who)
                       {
                           case "D":
                               //set driver id
                               SaveSharedPreference.setDriverid(getApplicationContext(),user_id);
                              /* Toast.makeText(getApplicationContext(),
                                       "Driver ID "+user_id, Toast.LENGTH_SHORT).show();*/
                              String car_status= c.getString("car_status");
                              int carStatus=Integer.parseInt(car_status);
                              if (carStatus>0)
                              {

                                  Intent intent=new Intent(getApplicationContext(),driver.class);
                                  intent.putExtra("carstatus","1");
                                  startActivity(intent);
                                  LoginActivity.this.finish();
                              }
                              else {
                                  //add your car
                                  Intent intent=new Intent(getApplicationContext(),driver.class);
                                  intent.putExtra("carstatus","0");
                                  startActivity(intent);
                                  LoginActivity.this.finish();
                              }

                              Log.e("Car Status"," "+car_status);

                               break;
                           default:
                               //set passenger id
                               SaveSharedPreference.setPassid(getApplicationContext(),user_id);
                             /*  Toast.makeText(getApplicationContext(),
                                       "Pass ID "+user_id, Toast.LENGTH_SHORT).show();*/
                               Intent intent1=new Intent(getApplicationContext(),passenger.class);
                               startActivity(intent1);
                               LoginActivity.this.finish();
                               break;
                       }
                    Toast.makeText(getApplicationContext(),
                            "Welcome "+name, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error Loggin Check your details ", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password_hash", password);
                params.put("who", who);
                return params;
            }
        };
        // Adding request to request queue
        // requestQueue.add(strReq);
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }

    /*private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        // Launch User activity
                        SaveSharedPreference.setUserName(getApplicationContext(),user);
                        Intent intent = new Intent(
                                LoginActivity.this,
                                MainActivity.class);
                        intent.putExtra("username", user);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);
                params.put("who", who);
                return params;
            }
        };
        // Adding request to request queue
        // requestQueue.add(strReq);
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }*/

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
