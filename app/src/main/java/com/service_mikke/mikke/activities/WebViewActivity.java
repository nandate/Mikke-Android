package com.service_mikke.mikke.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.service_mikke.mikke.R;

/**
 * Created by takuya on 5/13/17.
 */

public class WebViewActivity extends AppCompatActivity{
    private WebView webView;
    private String link;

    public void onCreate(Bundle savedInstanceState){


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            link = extras.getString("link");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = (WebView)findViewById(R.id.web_view);
        webView.loadUrl(link);
    }
}
