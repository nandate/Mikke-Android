package com.service_mikke.mikke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service_mikke.mikke.R;
import com.service_mikke.mikke.adapters.NoticeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takuya on 3/17/17.
 */

public class NoticeFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.notice_fragment,container,false);

        ArrayList<String> mDataset = new ArrayList<String>();
        mDataset.add("aaa");
        mDataset.add("bbb");
        mDataset.add("ccc");

        mRecyclerView =(RecyclerView)v.findViewById(R.id.notice_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NoticeRecyclerAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
}
