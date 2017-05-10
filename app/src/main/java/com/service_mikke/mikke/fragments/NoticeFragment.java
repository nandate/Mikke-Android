package com.service_mikke.mikke.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.adapters.NoticeRecyclerAdapter;
import com.service_mikke.mikke.models.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by takuya on 3/17/17.
 */

public class NoticeFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private List<Message> mDataset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.notice_fragment,container,false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDataset = new ArrayList<Message>();

        mRecyclerView =(RecyclerView)v.findViewById(R.id.notice_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDatabase.child("messages").orderByChild("created_at").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllMessage(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void getAllMessage(DataSnapshot dataSnapshot){
        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
            Message message = new Message();
            message.setTitle((String)snapshot.child("title").getValue());
            message.setContent((String)snapshot.child("content").getValue());
            message.setCreated_at((Long)snapshot.child("created_at").getValue());
            message.setPhotoUrl((String)snapshot.child("photoUrl").getValue());
            mDataset.add(message);
        }
        Collections.reverse(mDataset);
        mAdapter = new NoticeRecyclerAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
