package com.example.desagu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView immunizationCard,addVaccineCard,addChildCard,addNotificationCard,recordsCard,reportedCard;
    private ProgressDialog progressDialog;

AlertDialog.Builder aler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        aler=new AlertDialog.Builder(this);

        immunizationCard = (CardView) findViewById(R.id.immunizeCard);
        addVaccineCard = (CardView) findViewById(R.id.addVaccineCard);
        addChildCard = (CardView) findViewById(R.id.addChildCard);
        reportedCard= (CardView) findViewById(R.id.reportedCard);
        recordsCard = (CardView) findViewById(R.id.showRecordsCard);
        addNotificationCard=(CardView) findViewById(R.id.addNotificationCard);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching Child ...");


        addVaccineCard.setOnClickListener(this);
        addNotificationCard.setOnClickListener(this);
        recordsCard.setOnClickListener(this);
        addChildCard.setOnClickListener(this);
        reportedCard.setOnClickListener(this);

        immunizationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(DoctorDashboardActivity.this);
                View mView =getLayoutInflater().inflate(R.layout.search_child,null);

                final EditText birthCert = (EditText) mView.findViewById(R.id.editTextBct);


                Button search =(Button) mView.findViewById(R.id.buttonSearch);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog.show();
                       final String birthNo = birthCert.getText().toString().trim();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SEARCH_CHILD,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            progressDialog.dismiss();
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (!jsonObject.getBoolean("error")){

                                                Intent intent = new Intent(DoctorDashboardActivity.this,ImmunizationActivity.class);
                                                intent.putExtra("birthCert",birthNo);
                                               /* intent.putExtra("firstname",FiRSTNAME);
                                                intent.putExtra("middlename",MIDDLENAME);
                                                intent.putExtra("lastname",LASTNAME);
                                                intent.putExtra("gender",GENDER);
                                                intent.putExtra("homephone",HOMEPHONE);
                                                intent.putExtra("birthday",DATEOFBIRTH);*/

                                                startActivity(intent);

                                            }
                                            else{
                                                progressDialog.hide();
                                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
                                Map<String, String> params = new HashMap<>();
                                params.put("birthCert",birthNo);
                                return params;
                            }
                        };
                        RequestHandler.getInstance(DoctorDashboardActivity.this).addToRequestQueue(stringRequest);
                    }
                });
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
            break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.addVaccineCard:i = new Intent(this,AddNotificationsActivity.class);
            startActivity(i);
            break;
            case R.id.addChildCard:i = new Intent(this,AddChildActivity.class);
            startActivity(i);
            break;
            case R.id.addNotificationCard:i = new Intent(this,AddNotificationsActivity.class);
            startActivity(i);
            break;
            case R.id.showRecordsCard:i= new Intent(this,RegisteredActivity.class);
            startActivity(i);
            break;
            case R.id.reportedCard:i= new Intent(this,ViewCaseActivity.class);
                startActivity(i);
                break;
            default:
                break;


        }

    }
}
