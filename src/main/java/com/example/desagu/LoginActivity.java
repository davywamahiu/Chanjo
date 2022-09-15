package com.example.desagu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText textEditUsername, getTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, DoctorDashboardActivity.class));
            return;
        }

        textEditUsername = (EditText) findViewById(R.id.textEditUsername);
        getTextPassword = (EditText) findViewById(R.id.textEditPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        userLogin();
    }

    public void userLogin() {
        //  final  String User_name =textEditUsername.getText().toString().trim();


        if (TextUtils.isEmpty(textEditUsername.getText())) {

            textEditUsername.setError("User Name Required");

        } else if (TextUtils.isEmpty(getTextPassword.getText())) {
            getTextPassword.setError("Password Required");

        } else {

            progressDialog.show();
           final String User_name = textEditUsername.getText().toString().trim();
           final String Password = getTextPassword.getText().toString().trim();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, Constants.USERLOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {


                                JSONObject obj = new JSONObject(response);
                                if (!obj.getBoolean("error")) {
                                    SharedPrefManager.getInstance(getApplicationContext())
                                            .userLogin(
                                                    obj.getString("Employee_no"),
                                                    obj.getString("User_name"),
                                                    obj.getString("Email")
                                            );
                                    Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), DoctorDashboardActivity.class));

                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }

                            } catch (JSONException e) {
                                startActivity(new Intent(getApplicationContext(), DoctorDashboardActivity.class));

                            }

                            finish();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext(),"No connection", Toast.LENGTH_LONG).show();
                 /*   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();*/

                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("User_name", User_name);
                    params.put("Password", Password);

                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            userLogin();
        }
    }
}
