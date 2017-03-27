package com.service_mikke.mikke.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.models.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takuya on 3/27/17.
 */

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>{

    private List<Service> mDataset = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView logo;
        public ViewHolder(View v){
            super(v);
            name = (TextView)v.findViewById(R.id.fav_name);
            logo = (ImageView)v.findViewById(R.id.fav_logo);
        }
    }

    public FavoriteRecyclerAdapter(DatabaseReference ref){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot favorite:dataSnapshot.getChildren()){
                    mDataset.add(favorite.getValue(Service.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        holder.name.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}
