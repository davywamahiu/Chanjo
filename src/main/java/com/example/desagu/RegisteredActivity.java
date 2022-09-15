package com.example.desagu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisteredActivity extends AppCompatActivity {
    RegisteredAdapter adapter;
    List<Registered> registeredList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRegistered);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registeredList = new ArrayList<>();

        loadRegisteredChild();

    }

    public void loadRegisteredChild(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.REGISTERED_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray listOfRegistered = new JSONArray(response);

                            for (int i =0; i < listOfRegistered.length();i++){

                                JSONObject jsonObject = listOfRegistered.getJSONObject(i);
                                String firstName = jsonObject.getString("firstName");
                                String middleName = jsonObject.getString("middleName");
                                String lastName = jsonObject.getString("lastName");
                                String bithCert = jsonObject.getString("birthCert");
                                String gender =jsonObject.getString("gender");

                                Toast.makeText(getApplicationContext(),middleName,Toast.LENGTH_LONG);

                                String fullName = firstName+ " " +middleName + " "+lastName;

                                Registered registered = new Registered(fullName,gender,bithCert);
                                registeredList.add(registered);
                            }


                       adapter = new RegisteredAdapter(RegisteredActivity.this,registeredList);
                          recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
