package com.service_mikke.mikke.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.service_mikke.mikke.R;
import com.service_mikke.mikke.activities.GenresActivity;
import com.service_mikke.mikke.activities.SignUpActivity;

/**
 * Created by takuya on 3/27/17.
 */

public class Script3Fragment extends Fragment{

    private Button next_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.script3_fragment,container,false);

        next_button = (Button)v.findViewById(R.id.script3_next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SignUpActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
