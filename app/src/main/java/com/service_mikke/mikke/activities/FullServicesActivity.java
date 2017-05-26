package com.service_mikke.mikke.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.service_mikke.mikke.R;

/**
 * Created by takuya on 4/8/17.
 */

public class FullServicesActivity extends AppCompatActivity{

    private String name,title,description,link;
    private TextView name_view,title_view,description_view;
    private ImageView full_logo;
    private Button link_button,remove_fav_button;
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

        remove_fav_button = (Button)findViewById(R.id.remove_favrite_button);

        remove_fav_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                remove_fav(name);
                finish();
            }
        });

        link_button = (Button)findViewById(R.id.link_button);
        link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullServicesActivity.this,WebViewActivity.class);
                intent.putExtra("link",link);
                startActivity(intent);
            }
        });
    }

    private void remove_fav(String name){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference fav_ref = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("fav");
        fav_ref.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                    firstChild.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
