package com.service_mikke.mikke.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.fragments.SoonFragment;
import com.service_mikke.mikke.fragments.WebViewFragment;

/**
 * Created by takuya on 4/8/17.
 */

public class FullServicesActivity extends AppCompatActivity{

    private String name,title,description,link;
    private TextView name_view,title_view,description_view;
    private ImageView full_logo;
    private Button link_button;
    private WebView webView;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://mikke-d5d0a.appspot.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_services);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name = extras.getString("name");
            title = extras.getString("title");
            description = extras.getString("description");
            link = extras.getString("link");
        }

        full_logo = (ImageView)findViewById(R.id.full_service_logo);

        StorageReference logo_ref = storageReference.child(name+"_logo.png");
        logo_ref.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap logo = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                full_logo.setImageBitmap(logo);
            }
        });

        name_view = (TextView)findViewById(R.id.full_service_name);
        name_view.setText(name);
        title_view = (TextView)findViewById(R.id.full_company_name);
        title_view.setText(title);
        description_view = (TextView)findViewById(R.id.full_service_description);
        description_view.setText(description);

        webView = (WebView)findViewById(R.id.web_view);
        link_button = (Button)findViewById(R.id.link_button);
        link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(link);
            }
        });
    }

}
