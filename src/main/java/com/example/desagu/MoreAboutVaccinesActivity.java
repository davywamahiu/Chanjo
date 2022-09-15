package com.example.desagu;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MoreAboutVaccinesActivity extends AppCompatActivity {

    private  static final String TAG ="MoreAboutVaccinesActivity";

    private Context mContext;
    private RecyclerView mRecyclerView;
    ArrayList<String> titleAllayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_about_vaccines);

        mContext = MoreAboutVaccinesActivity.this;

        titleAllayList = new ArrayList<String>();

        titleAllayList.add(Constants.BCG);
        titleAllayList.add(Constants.DTV);
        titleAllayList.add(Constants.MSV);
        titleAllayList.add(Constants.OPV);
        titleAllayList.add(Constants.TTV);
        titleAllayList.add(Constants.WCPV);

        mRecyclerView = (RecyclerView) findViewById(R.id.title_layout_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        TitleAdapter adapter = new TitleAdapter(mContext, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        Intent desc = new Intent(mContext,VaccinesDescriptionActivity.class);

                        desc.putExtra("titles",titleAllayList.get(position));

                        startActivity(desc);

                    }
                }, titleAllayList);
        mRecyclerView.setAdapter(adapter);
    }
}
