package com.service_mikke.mikke_android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service_mikke.mikke_android.R;

/**
 * Created by takuya on 4/11/17.
 */

public class UserSettingActivity extends AppCompatActivity{

    private String uid;
    private DatabaseReference mDatabase;

    private EditText user_name_textView;
    private EditText user_birth_textView;
    private RadioGroup user_sex_radioGroup;
    private String prefectures[];
    private Spinner prefectures_spinner;

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        user_name_textView = (EditText)findViewById(R.id.editText);
        user_birth_textView = (EditText)findViewById(R.id.editText5);
        user_sex_radioGroup = (RadioGroup)findViewById(R.id.radiogroup_sex);

        prefectures = getResources().getStringArray(R.array.prefectures);

        prefectures_spinner = (Spinner)findViewById(R.id.spinner2);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,prefectures);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefectures_spinner.setAdapter(adapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_name_textView.setText(dataSnapshot.child("user_name").getValue().toString());
                if(dataSnapshot.child("user_birthday").exists()){
                    user_birth_textView.setText(dataSnapshot.child("user_birthday").getValue().toString());
                }

                if(dataSnapshot.child("user_sex").exists()){
                    String gender = dataSnapshot.child("user_sex").getValue().toString();
                    if(gender.equals("男性")){
                        user_sex_radioGroup.check(R.id.radio_male);

                    }else if(gender.equals("女性")){
                        user_sex_radioGroup.check(R.id.radio_female);
                    }
                }

                if(dataSnapshot.child("user_prefectures").exists()){
                    String prefecture = dataSnapshot.child("user_prefectures").getValue().toString();
                    int position = adapter.getPosition(prefecture);
                    prefectures_spinner.setSelection(position);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        submitButton = (Button)findViewById(R.id.button5);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("user_name").setValue(user_name_textView.getText().toString());
                mDatabase.child("user_birthday").setValue(user_birth_textView.getText().toString());
                int checkId = user_sex_radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)findViewById(checkId);
                mDatabase.child("user_sex").setValue(radioButton.getText().toString());
                mDatabase.child("user_prefectures").setValue(prefectures_spinner.getSelectedItem());
                finish();
            }
        });
    }
}
