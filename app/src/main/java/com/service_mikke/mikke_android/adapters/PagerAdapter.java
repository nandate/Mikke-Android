package com.service_mikke.mikke_android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.service_mikke.mikke_android.fragments.MyPageFragment;
import com.service_mikke.mikke_android.fragments.NoticeFragment;
import com.service_mikke.mikke_android.fragments.RecommendFragment;

/**
 * Created by takuya on 3/17/17.
 */


public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                RecommendFragment tab1 = new RecommendFragment();
                return tab1;
            case 1:
                NoticeFragment tab2 = new NoticeFragment();
                return tab2;
            case 2:
                MyPageFragment tab3 = new MyPageFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return mNumOfTabs;
    }
}
