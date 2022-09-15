package com.example.desagu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.*;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      Toolbar toolbar= (Toolbar) findViewById(R.id.toolbars);
          setSupportActionBar(toolbar);

        androidx.drawerlayout.widget.DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_login){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.checkHistory) {
            final Dialog d=new Dialog(this);
            d.setContentView(R.layout.search_child);
            d.show();
            final EditText ed=d.findViewById(R.id.editTextBct);
            Button b=d.findViewById(R.id.buttonSearch);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ed.getText().toString().length()==0){
                        ed.setError("birth certificate number is required");
                        View k;
                        k=ed;
                        k.requestFocus();
                    }
                    else{
                        d.dismiss();
                        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                        intent.putExtra("birthNumber",ed.getText().toString());
                        startActivity(intent);
                    }
                }
            });
        } else if (id == R.id.Report) {

            Intent intent = new Intent(MainActivity.this, ReportIssueActivity.class);
            startActivity(intent);

        } else if (id == R.id.readmore) {

            Intent intent =  new Intent(MainActivity.this,MoreAboutVaccinesActivity.class);
            startActivity(intent);

        }else if (id == R.id.notifications){
            Intent intent = new Intent(MainActivity.this,NotificationsActivity.class);
            startActivity(intent);
        }else if (id == R.id.help){
            Intent intent = new Intent(MainActivity.this,Help.class);
            startActivity(intent);


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

