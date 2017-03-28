package com.service_mikke.mikke.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.service_mikke.mikke.R;
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

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, Script1Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Script2Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Script3Fragment.class.getName()));
    }
}
