package com.service_mikke.mikke_android.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.service_mikke.mikke_android.R;

/**
 * Created by takuya on 4/9/17.
 */

public class MessageDetailActivity extends AppCompatActivity{

    private String title,content,photoUrl;
    private TextView title_view,content_view;
    private ImageView photo_icon;


    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://mikke-d5d0a.appspot.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            title = extras.getString("title");
            content = extras.getString("content");
            photoUrl = extras.getString("photoUrl");
        }

        title_view = (TextView)findViewById(R.id.message_title);
        title_view.setText(title);
        content_view = (TextView)findViewById(R.id.message_content);
        content_view.setText(content);

        photo_icon = (ImageView)findViewById(R.id.message_icon);
        StorageReference icon_ref = storageReference.child(photoUrl);
        icon_ref.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap icon = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                photo_icon.setImageBitmap(icon);
            }
        });
    }
}
