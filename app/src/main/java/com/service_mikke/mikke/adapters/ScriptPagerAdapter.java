package com.service_mikke.mikke.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.service_mikke.mikke.fragments.Script1Fragment;
import com.service_mikke.mikke.fragments.Script2Fragment;
import com.service_mikke.mikke.fragments.Script3Fragment;

/**
 * Created by takuya on 3/28/17.
 */

public class ScriptPagerAdapter extends FragmentPagerAdapter{

    public ScriptPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new Script1Fragment();
            case 1:
                return new Script2Fragment();
            default:
                return new Script3Fragment();
        }
    }

    @Override
    public int getCount(){
        return 3;
    }

}
