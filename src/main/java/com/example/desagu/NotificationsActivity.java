package com.example.desagu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationsAdapter adapter;
    AlertDialog.Builder aler;

    List<Notification>notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        aler=new AlertDialog.Builder(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotifications);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList =new ArrayList<>();

        loadNotification();
    }

    public void loadNotification(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.FETCH_NOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray notifications = new JSONArray(response);

                            for (int i=0; i < notifications.length();i++){

                                JSONObject notification = notifications.getJSONObject(i);

                                String title = notification.getString("title");
                                String description = notification.getString("description");
                                String datePosted = notification.getString("datePosted");
                                String by = notification.getString("by");


                                Notification fetchedNotification = new Notification(title,description,datePosted,by);

                                notificationList.add(fetchedNotification);

                            }

                            adapter = new NotificationsAdapter(NotificationsActivity.this,notificationList);
                            recyclerView.setAdapter(adapter);


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
        });

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
