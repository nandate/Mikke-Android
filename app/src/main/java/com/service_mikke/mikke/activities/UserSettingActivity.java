package com.service_mikke.mikke.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service_mikke.mikke.R;

/**
 * Created by takuya on 4/11/17.
 */

public class UserSettingActivity extends AppCompatActivity{

    private String uid;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
    }
}
