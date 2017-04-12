package com.service_mikke.mikke.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.widget.LoginButton;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.activities.GenresActivity;
import com.service_mikke.mikke.activities.LogInActivity;
import com.service_mikke.mikke.activities.SignUpActivity;

/**
 * Created by takuya on 3/27/17.
 */

public class Script3Fragment extends Fragment{

    private Button SignUp_Button,Login_Button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.script3_fragment,container,false);

        SignUp_Button = (Button)v.findViewById(R.id.signup_button);
        SignUp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        Login_Button = (Button)v.findViewById(R.id.login_button);
        Login_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
