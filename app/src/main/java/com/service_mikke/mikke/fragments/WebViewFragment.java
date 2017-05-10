package com.service_mikke.mikke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service_mikke.mikke.R;

/**
 * Created by takuya on 5/10/17.
 */

public class WebViewFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.webview_fragment,container,false);
    }
}
