package com.service_mikke.mikke.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.models.Service;
import com.service_mikke.mikke.models.ServiceTinderCard;

/**
 * Created by takuya on 3/17/17.
 */


public class RecommendFragment extends Fragment{

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    private Button like_button;
    private Button dislike_button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.recommend_fragment,container,false);

        mSwipeView = (SwipePlaceHolderView)v.findViewById(R.id.recommend_swipeView);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId =mFirebaseUser.getUid();


        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));

        try{
            mDatabase.child("users").child(mUserId).child("recommends").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Service recommend = snapshot.getValue(Service.class);
                        mSwipeView.addView(new ServiceTinderCard(mContext,recommend,mSwipeView));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        like_button = (Button)v.findViewById(R.id.like_button);
        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwipeView.doSwipe(false);
            }
        });

        dislike_button = (Button)v.findViewById(R.id.dislike_button);
        dislike_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mSwipeView.doSwipe(true);
            }
        });
        return v;
    }




}