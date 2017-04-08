package com.service_mikke.mikke.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.service_mikke.mikke.R;
import com.service_mikke.mikke.activities.MessageDetailActivity;
import com.service_mikke.mikke.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takuya on 3/27/17.
 */

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.ViewHolder>{

    private List<Message> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView content;
        public View item;

        public ViewHolder(View v){
            super(v);
            title = (TextView)v.findViewById(R.id.notice_title);
            content = (TextView)v.findViewById(R.id.notice_content);
            item = v.findViewById(R.id.message_item);
        }
    }

    public NoticeRecyclerAdapter(List<Message> myDataset){
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
        holder.title.setText(mDataset.get(position).getTitle());
        holder.content.setText(mDataset.get(position).getContent());
        holder.item.setOnClickListener(new MessageClick(mDataset.get(position)));
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    private class MessageClick implements View.OnClickListener{
        private String title,content;
        public MessageClick(Message message){
            title = message.getTitle();
            content = message.getContent();
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(view.getContext(), MessageDetailActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("content",content);
            view.getContext().startActivity(intent);
        }
    }

}










