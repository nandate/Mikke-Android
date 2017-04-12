package com.service_mikke.mikke.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.service_mikke.mikke.R;
import com.service_mikke.mikke.adapters.ScriptPagerAdapter;
import com.service_mikke.mikke.fragments.Script1Fragment;
import com.service_mikke.mikke.fragments.Script2Fragment;
import com.service_mikke.mikke.fragments.Script3Fragment;

import java.util.ArrayList;

/**
 * Created by takuya on 3/27/17.
 */

public class StartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FragmentManager manager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.script_pager);

        ScriptPagerAdapter adapter = new ScriptPagerAdapter(manager);

        viewPager.setAdapter(adapter);
    }
}
