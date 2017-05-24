package com.service_mikke.mikke.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://mikke-d5d0a.appspot.com/");


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        ImageView photo;
        View item;

        public ViewHolder(View v){
            super(v);
            title = (TextView)v.findViewById(R.id.notice_title);
            content = (TextView)v.findViewById(R.id.notice_content);
            photo = (ImageView)v.findViewById(R.id.notice_icon);
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
    public void onBindViewHolder(final ViewHolder holder, int position){
        holder.title.setText(mDataset.get(position).getTitle());
        holder.content.setText(mDataset.get(position).getContent());

        StorageReference logo_ref = storageReference.child(mDataset.get(position).getPhotoUrl());
        logo_ref.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap logo = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.photo.setImageBitmap(logo);
            }
        });

        holder.item.setOnClickListener(new MessageClick(mDataset.get(position)));
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    private class MessageClick implements View.OnClickListener{
        private String title,content,photoUrl;
        public MessageClick(Message message){
            title = message.getTitle();
            content = message.getContent();
            photoUrl = message.getPhotoUrl();
        }

        @Override
        public void onClick(View view){

            Intent intent = new Intent(view.getContext(), MessageDetailActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("content",content);
            intent.putExtra("photoUrl",photoUrl);
            view.getContext().startActivity(intent);
        }
    }

}










