package com.service_mikke.mikke.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.service_mikke.mikke.R;

import java.util.ArrayList;

/**
 * Created by takuya on 3/27/17.
 */

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.ViewHolder>{

    private ArrayList<String> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView)v.findViewById(R.id.notice_text);
        }
    }

    public NoticeRecyclerAdapter(ArrayList<String> myDataset){
        mDataset = myDataset;
    }

    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        holder.mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}










