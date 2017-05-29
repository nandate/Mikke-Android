package com.service_mikke.mikke.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.adapters.FavoriteRecyclerAdapter;
import com.service_mikke.mikke.models.Service;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


/**
 * Created by takuya on 3/17/17.
 */

public class MyPageFragment extends Fragment{

    private DatabaseReference mDatabase;
    private String mUserId;

    private TextView username_textView;
    private ImageView user_icon_imageView;
    private RecyclerView favorite_recycler_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.mypage_fragment,container,false);


        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mUserId);


        user_icon_imageView = (ImageView)v.findViewById(R.id.user_icon);
        mDatabase.child("photo_url").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){

                    int identifier = getContext().getResources().getIdentifier("profile_image_blank","drawable",getContext().getPackageName());
                    user_icon_imageView.setImageResource(identifier);

                }else{
                    String photo_url = dataSnapshot.getValue(String.class);
                    Picasso.with(getContext()).load(photo_url).into(user_icon_imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        username_textView = (TextView)v.findViewById(R.id.user_name);
        mDatabase.child("user_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_name = dataSnapshot.getValue(String.class);
                username_textView.setText(user_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        favorite_recycler_view = (RecyclerView)v.findViewById(R.id.favorite_recyclerView);

        FavoriteRecyclerAdapter mAdapter = new FavoriteRecyclerAdapter(mDatabase.child("fav"));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        favorite_recycler_view.setLayoutManager(mLayoutManager);
        favorite_recycler_view.setAdapter(mAdapter);

        return v;
    }
}
