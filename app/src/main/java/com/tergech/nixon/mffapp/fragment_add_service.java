package com.tergech.nixon.mffapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class fragment_add_service extends Fragment {
    private Button btnAddservice;
    private Button btn_time;
    private Button btn_date;
    private EditText edt_cost,edt_pass_capacity,edt_time,edt_date;
    private TextView txtroute;
    private String Cost,Pass_Capacity,Route,Route_Id,_Date,_Time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_add_service, parent, false);
        btnAddservice=(Button)view.findViewById(R.id.btn_addservice);
       /* btn_date=(Button)view.findViewById(R.id.btn_date);
        btn_time=(Button)view.findViewById(R.id.btn_time);*/

        edt_cost=(EditText)view.findViewById(R.id.input_layout_cost);
        edt_pass_capacity=(EditText)view.findViewById(R.id.input_pass_capacity);
        edt_date=(EditText)view.findViewById(R.id.input_layout_date);
        edt_time=(EditText)view.findViewById(R.id.input_layout_time);

        txtroute=(TextView)view.findViewById(R.id.txtroute);

        btnAddservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the edit text values
                Cost=edt_cost.getText().toString();
                Pass_Capacity=edt_pass_capacity.getText().toString();
                _Date=edt_date.getText().toString();
                _Time=edt_time.getText().toString();
                if (Cost!=null & Pass_Capacity!=null &_Date!=null & _Time!=null)
                {
                    //method to post data to the server



                }
                else
                {
                    Toast.makeText(getActivity(),"Please enter all the fields above",Toast.LENGTH_LONG).show();
                }



            }
        });






        return view ;
    }



//method to post data to the server
    public void post_data(String route,String pass_Capacity,String _Date,String _Time)
    {

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}