package com.example.desagu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
AlertDialog.Builder aler;
    String BIRTH_CERT;
ListView list;
custom cust;
ArrayList<Registered> reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
list=findViewById(R.id.list);
reg=new ArrayList<>();
cust=new custom(this,reg);
list.setAdapter(cust);
        aler=new AlertDialog.Builder(this);
        BIRTH_CERT =  getIntent().getStringExtra("birthNumber");
        Toast.makeText(getApplicationContext(),BIRTH_CERT,Toast.LENGTH_LONG).show();

        loadHistory();
    }

    public void loadHistory(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CHILD_HISTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                          for(int i = 0; i<jsonArray.length();i++){

                              JSONObject jsonObject = jsonArray.getJSONObject(i);
                              String vaccine = jsonObject.getString("vaccine");
                              String status = jsonObject.getString("status");
                              String date = jsonObject.getString("date");
                              String doctor = jsonObject.getString("doctor");

                            Registered re=new Registered(vaccine,status,date,doctor);
                            cust.add(re);

                          }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                aler.setTitle("connection Error")
                        .setMessage("you are Offline")
                        .setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                Intent iw=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(iw);
                            }
                        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                        Intent iw=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(iw);
                    }
                }).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("birthCert",BIRTH_CERT);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    public class custom extends ArrayAdapter<Registered>{
        Activity cont;
        ArrayList<Registered> reg;
        public custom(Activity cont, ArrayList<Registered> reg) {
            super(cont, R.layout.list_histry,reg);
            this.cont=cont;
            this.reg=reg;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
           Registered registered=getItem(position);
            if(convertView==null){
                LayoutInflater li=cont.getLayoutInflater();
                convertView=li.inflate(R.layout.list_histry,null,true);
            }
            TextView vac=convertView.findViewById(R.id.vac);
            TextView sta=convertView.findViewById(R.id.sta);
            TextView date=convertView.findViewById(R.id.date);
            TextView doctor=convertView.findViewById(R.id.doctor);
            vac.setText(registered.getVaccine());
            sta.setText(registered.getStatus());
            date.setText(registered.getDate());
            doctor.setText(registered.getDoctor());
            return  convertView;

    }
}}
