package com.example.desagu;

import android.os.Bundle;

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

public class ViewCaseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewCaseAdapter adapter;

    List<ViewCase> viewCaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_case);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCase);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        viewCaseList = new ArrayList<>();

        loadCase();
    }

    public void loadCase(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.LOAD_REPORTED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i<jsonArray.length();i++){

                                JSONObject listCases = jsonArray.getJSONObject(i);

                                String title = listCases.getString("title");
                                String desc = listCases.getString("description");
                                String contact = listCases.getString("contact");

                                ViewCase fetchedCases = new ViewCase(contact,title,desc);
                                viewCaseList.add(fetchedCases);
                            }
                            adapter = new ViewCaseAdapter(ViewCaseActivity.this,viewCaseList);
                            recyclerView.setAdapter(adapter);

                          //recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
