package com.example.desagu;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressBarActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.textViewP);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();
try{
   final Handler h=new Handler();
    h.postDelayed(new Runnable() {
        @Override
        public void run() {
                finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }
    },8000);

}catch (Exception p){

}

    }

    public void progressAnimation(){

        ProgressBarAnimation anim = new ProgressBarAnimation(this,progressBar,textView,0f,100f);

        anim.setDuration(8000);
        progressBar.setAnimation(anim);




    }


}
