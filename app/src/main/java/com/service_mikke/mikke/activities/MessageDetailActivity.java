package com.service_mikke.mikke.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.service_mikke.mikke.R;

/**
 * Created by takuya on 4/9/17.
 */

public class MessageDetailActivity extends AppCompatActivity{

    private String title,content;
    private TextView title_view,content_view;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            title = extras.getString("title");
            content = extras.getString("content");
        }

        title_view = (TextView)findViewById(R.id.notice_title);
        title_view.setText(title);
        content_view = (TextView)findViewById(R.id.notice_content);
        content_view.setText(content);
    }
}
