package com.example.desagu;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class noti_description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_description);
        String s=getIntent().getStringExtra("brian");
        String s1=getIntent().getStringExtra("brian1");
        String s2=getIntent().getStringExtra("brian2");
        String s3=getIntent().getStringExtra("brian3");
        TextView notificationDesc = (TextView) findViewById(R.id.textViewNotificationDescription);
        TextView title = (TextView) findViewById(R.id.title);
        TextView date = (TextView) findViewById(R.id.date);
        TextView by= (TextView) findViewById(R.id.posted_p);
        notificationDesc.setText(s);
        title.setText(s1);
        date.setText(s2);
        by.setText(s3);
    }
}
