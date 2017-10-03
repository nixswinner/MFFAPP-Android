package com.tergech.nixon.mffapp;

/**
 * Created by Tonui on 6/25/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivityDriver extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "http://192.168.137.1/Api/MFFAPP/public/index.php/api/registerDrivers";
    ProgressDialog progressDialog;

    private EditText signupInputName, signupInputPhoneNumber, signupInputPassword, signupInputPassAgain,signupInputIDNO,signUpInputEmail;
    private Button btnSignUp;
    private Button btnLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        signupInputName = (EditText) findViewById(R.id.signup_input_name);
        signupInputPhoneNumber = (EditText) findViewById(R.id.signup_input_phone);
        signUpInputEmail=(EditText)findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);
        signupInputPassAgain = (EditText) findViewById(R.id.signup_input_password_again);
        signupInputIDNO=(EditText)findViewById(R.id.signup_input_id_number);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkLogin = (Button) findViewById(R.id.btn_link_login);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(InternetConnection.checkConnection(RegisterActivityDriver.this))
                {
                    submitForm();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enable your Internet connection!!", Toast.LENGTH_SHORT).show();
                }*/
                registerUser(signupInputName.getText().toString(),
                        signupInputPhoneNumber.getText().toString(),
                        signUpInputEmail.getText().toString(),
                        signupInputIDNO.getText().toString(),
                        signupInputPassword.getText().toString()

                );

            }
        });
        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
    }


    private void registerUser(final String Name,  final String Phone,final String email,final String IDNO, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";
        progressDialog.setMessage("Adding you ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                Toast.makeText(getApplicationContext(), "Response "+response, Toast.LENGTH_SHORT).show();
                hideDialog();
                //Toast.makeText(getApplicationContext(), "Response "+response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");


                   // String user = jObj.getJSONObject("user").getString("name");
                   // Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Registered!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivityDriver.this,
                                LoginActivity.class);
                                intent.putExtra("status","0");
                        startActivity(intent);
                        finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error Occured Try again"+e, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", Name);
                params.put("phone", Phone);
                params.put("id_no", IDNO);
                params.put("email", email);
                params.put("password_hash", password);
                return params;
            }
        };
        // Adding request to request queue
        //requestQueue.add(strReq);
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }
   /* private void submitForm() {



        registerUser(signupInputName.getText().toString(),
                signupInputPhoneNumber.getText().toString(),
                signUpInputEmail.getText().toString(),
                signupInputIDNO.getText().toString(),
                signupInputPassword.getText().toString()

                );
        //saving phone number
        SaveSharedPreference.setPhoneNumber(getApplicationContext(),signupInputPhoneNumber.getText().toString());
    }

    private void registerUser(final String name,  final String phone_number,final String Email, final String IdNo, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Adding you ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                //Toast.makeText(getApplicationContext(), "Response "+response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getApplicationContext(), "Hi " + user +", You successfully joined us!", Toast.LENGTH_SHORT).show();

                        // Launch login activity

                        Intent intent = new Intent(
                                RegisterActivityDriver.this,
                                LoginActivity.class);
                        intent.putExtra("who","D");
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("phone_number", phone_number);
                params.put("id_no", IdNo);
                params.put("email", Email);
                params.put("password_hash", password);
                return params;
            }
        };
        // Adding request to request queue
        //requestQueue.add(strReq);
       AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
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
