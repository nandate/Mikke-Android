package com.service_mikke.mikke.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.activities.FullServicesActivity;
import com.service_mikke.mikke.models.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takuya on 3/27/17.
 */

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>{

    private List<Service> mDataset = new ArrayList<>();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://mikke-d5d0a.appspot.com/");

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
    public void onBindViewHolder(final ViewHolder holder, int position){
        holder.name.setText(mDataset.get(position).getName());
        StorageReference logo_ref = storageReference.child(mDataset.get(position).getName()+"_logo.png");
        logo_ref.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap logo = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.logo.setImageBitmap(logo);
            }
        });
        holder.logo.setOnClickListener(new FavClick(mDataset.get(position)));

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    private class FavClick implements View.OnClickListener{
        private Service service;
        private String name,title,description,link;

        public FavClick(Service service){
            this.service = service;
            this.name = service.getName();
            this.title = service.getTitle();
            this.description = service.getDescription();
            this.link = service.getLink();
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(view.getContext(), FullServicesActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("title",title);
            intent.putExtra("description",description);
            intent.putExtra("link",link);
            view.getContext().startActivity(intent);
        }
    }
}
