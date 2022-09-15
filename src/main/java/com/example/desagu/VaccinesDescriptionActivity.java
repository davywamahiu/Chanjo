package com.example.desagu;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class VaccinesDescriptionActivity extends AppCompatActivity {

    private Bundle extras;
    private static final  String TAG = "DescriptionActivity";
    private Context mContext;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines_description);

        mContext = VaccinesDescriptionActivity.this;
        webView = (WebView) findViewById(R.id.simpleWebView);

        extras = getIntent().getExtras();

        if(!extras.equals(null)){
            String data = extras.getString("titles");

            Log.d(TAG,"onCreate: the coming data is "+data);


            String url ="file:///android_asset/"+data+".html";
            webView.loadUrl(url);

            WebSettings webSettings = webView.getSettings();
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);

            webSettings.setJavaScriptEnabled(true);

        }
    }
}
