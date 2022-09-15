package com.example.desagu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImmunizationActivity extends AppCompatActivity {

    private TextView firstName, birthCert1,dobf,genderTextView,homephoneTextView;
    private Button immunize;
    private ImageView childImage;
    private ProgressDialog progressDialog;
    private Spinner spinner;
    String imageUrl;
    String a1;
    String birth;
    String birthCertNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_immunization);
        firstName = (TextView) findViewById(R.id.textFirtName);
        birthCert1 = (TextView) findViewById(R.id.textBirthNo);
        genderTextView = (TextView) findViewById(R.id.textViewgender);
        homephoneTextView = (TextView) findViewById(R.id.textViewHomephone);
        dobf =(TextView) findViewById(R.id.textViewDob);
        childImage = (ImageView) findViewById(R.id.childImage);
        spinner = (Spinner) findViewById(R.id.spinner);

        birth = getIntent().getStringExtra("birthCert");

       String preferences = SharedPrefManager.getInstance(this).getEmployee();

        Toast.makeText(getApplicationContext(),preferences,Toast.LENGTH_LONG).show();
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (i==1){
             a1="BCG";
        }
        else if (i==2){
            a1="DTV";
        }
        else if (i==3){
            a1="MSV";
        }
        else if(i==4){
            a1="OPV";
        }
        else if(i==5){
            a1="TTV";
        }
        else if(i==6){
            a1="WCPV";
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait Immunizing...");
        //end of dialog
        immunize = (Button) findViewById(R.id.buttonImmunize);

        birthCertNo = getIntent().getStringExtra("birthCert");

        imageUrl = Constants.SHOW_IMAGE + birthCertNo+".jpeg";
        loadChildRecords();
        showChildImage();

       immunize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.IMMUNIZE_CHILD,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(!jsonObject.getBoolean("error")){
                                       showDialog1();
                                    }
                                    else{
                                       showDialogError();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("vaccineCode",a1);
                        params.put("birthCert",birthCertNo);
                       /* SharedPreferences pre=getSharedPreferences("mimi",MODE_PRIVATE);
                        int string=pre.getInt("Employee_no", Integer.parseInt("0"));
                        String s=String.format("%d",string);*/
                        params.put("immunized_by","4");
                        return params;
                    }
                };
                RequestHandler.getInstance(ImmunizationActivity.this).addToRequestQueue(stringRequest);
            }
        });


    }

    public void loadChildRecords(){
        final String BIRTHCERT = birthCertNo;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOAD_CHILD_RECORDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject =jsonArray.getJSONObject(i);
                                String FIRSTNAME = jsonObject.getString("first_name");
                                String MIDDLE_NAME = jsonObject.getString("middle_name");
                                String LAST_NAME = jsonObject.getString("last_name");
                                String DATE_OF_BIRTH = jsonObject.getString("D_O_B");
                                String GENDER = jsonObject.getString("gender");
                                String PHONE = jsonObject.getString("home_phone");

                                birthCert1.setText(BIRTHCERT);
                                firstName.setText(FIRSTNAME+" "+MIDDLE_NAME+ " "+LAST_NAME);
                                genderTextView.setText(GENDER);
                                dobf.setText(DATE_OF_BIRTH);
                                homephoneTextView.setText(PHONE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("birthCert",BIRTHCERT);
                return  params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void showChildImage(){
        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(R.drawable.notavailable)
                .error(R.drawable.user_error)
                .into(childImage);

    }

    public void showDialogError(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alerdy_immunized_error);


        dialog.show();

    }

    public void showDialog1(){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("immunization");
        alert.setMessage("immunized");
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ImmunizationActivity acti=new ImmunizationActivity();
                acti.finish();
                Intent rt=new Intent(getApplicationContext(),DoctorDashboardActivity.class);
                startActivity(rt);
            }
        });
        alert.show();

    }


}
