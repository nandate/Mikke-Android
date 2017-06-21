package com.service_mikke.mikke_android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.service_mikke.mikke_android.R;
import com.service_mikke.mikke_android.models.Service;
import com.service_mikke.mikke_android.models.ServiceTinderCard;

/**
 * Created by takuya on 3/17/17.
 */


public class RecommendFragment extends Fragment{

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private DatabaseReference recommend_ref;
    private String mUserId;

    private Button like_button;
    private Button dislike_button;

    private SoonFragment soonFragment;

    private FragmentManager fm;
    private FragmentTransaction ft;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.recommend_fragment,container,false);

        mSwipeView = (SwipePlaceHolderView)v.findViewById(R.id.recommend_swipeView);
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recommend_ref = FirebaseDatabase.getInstance().getReference().child("users").child(mUserId).child("recommends");



        mSwipeView.getBuilder()
                .setDisplayViewCount(1)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(10)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));

        mSwipeView.getScrollBarSize();


        fm = getFragmentManager();
        ft = fm.beginTransaction();


        recommend_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Service recommend = dataSnapshot.getValue(Service.class);
                mSwipeView.addView(new ServiceTinderCard(mContext,recommend,mSwipeView));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recommend_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    if(soonFragment == null){
                        soonFragment = new SoonFragment();
                        ft.add(R.id.recommend_fragment,soonFragment);
                        ft.commit();
                    }
                }else{
                    if(soonFragment != null){
                        ft.remove(soonFragment);
                        ft.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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