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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "http://139.162.42.154/app/meetff/public/index.php/api/registerPass";
    ProgressDialog progressDialog;

    private EditText signupInputName, signupInputPhoneNumber, signupInputPassword, signupInputPassAgain,signupInputEmail;
    private Button btnSignUp;
    private Button btnLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Register Now");
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        signupInputName = (EditText) findViewById(R.id.signup_input_name);
        signupInputPhoneNumber = (EditText) findViewById(R.id.signup_input_phone);
        signupInputEmail=(EditText)findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);
        signupInputPassAgain = (EditText) findViewById(R.id.signup_input_password_again);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkLogin = (Button) findViewById(R.id.btn_link_login);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   // submitForm();
                    //delaying
                try {
                  /*  final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Registering you ...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(i);

                                }
                            }, 3000);*/
                  if (signupInputName.getText().toString().isEmpty()&&
                          signupInputPhoneNumber.getText().toString().isEmpty()&&
                          signupInputEmail.getText().toString().isEmpty() &&
                          signupInputPassword.getText().toString().isEmpty()

                          )
                  {
                      Toast.makeText(RegisterActivity.this,"Error: You must enter all the details to register",Toast.LENGTH_SHORT).show();

                  }else
                  {
                      registerUser(signupInputName.getText().toString(),
                              signupInputPhoneNumber.getText().toString(),
                              signupInputEmail.getText().toString(),
                              signupInputPassword.getText().toString()

                      );
                  }

                }
                catch (Exception ex)
                {
                    //Toast.makeText(getApplicationContext(),"Error "+ex,Toast.LENGTH_LONG).show();
                }




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

    private void registerUser(final String Name,  final String Phone,final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";
        progressDialog.setMessage("Adding you ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                Toast.makeText(getApplicationContext(), "Hi you have successfully joined us!", Toast.LENGTH_SHORT).show();
                hideDialog();

                    // String user = jObj.getJSONObject("user").getString("name");
                    Toast.makeText(getApplicationContext(), "Hi you have successfully Registered!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("name","");
                    startActivity(intent);
                    RegisterActivity.this.finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Error Occured Please Try Again", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", Name);
                params.put("phone", Phone);
                params.put("email", email);
                params.put("password_hash", password);
                return params;
            }
        };
        // Adding request to request queue
        //requestQueue.add(strReq);
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
