package com.service_mikke.mikke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.service_mikke.mikke.R;
import com.service_mikke.mikke.adapters.ScriptPagerAdapter;

/**
 * Created by takuya on 3/27/17.
 */

public class Script1Fragment extends Fragment{

    private Button next_button;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.script1_fragment,container,false);
        viewPager = (ViewPager)getActivity().findViewById(R.id.script_pager);

        next_button = (Button)v.findViewById(R.id.script1_next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });

        return v;
    }

}
