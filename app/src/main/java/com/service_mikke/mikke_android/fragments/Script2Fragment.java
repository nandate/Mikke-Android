package com.service_mikke.mikke_android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.service_mikke.mikke_android.R;

/**
 * Created by takuya on 3/27/17.
 */

public class Script2Fragment extends Fragment{

    private Button next_button;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.script2_fragment,container,false);
        next_button = (Button)v.findViewById(R.id.script2_next_button);

        viewPager = (ViewPager)getActivity().findViewById(R.id.script_pager);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        return v;
    }
}
