package com.service_mikke.mikke.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.models.Genre;
import com.service_mikke.mikke.models.GenreTinderCard;
import com.service_mikke.mikke.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takuya on 3/17/17.
 */

public class GenresActivity extends AppCompatActivity{


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private Button next_view_button;


    private String mUserId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        Toolbar toolbar = (Toolbar)findViewById(R.id.genres_toolbar);
        setSupportActionBar(toolbar);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        User user = User.getInstance();
        user.getSelected_genres();

        mUserId =mFirebaseUser.getUid();

        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.genres_swipeView);
        mContext = getApplicationContext();

        next_view_button = (Button)findViewById(R.id.next_view_butotn);
        next_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("users").child(mUserId).child("selected_genres").setValue(User.getInstance().getSelected_genres());
                Intent intent = new Intent(view.getContext(),UsedServicesActivity.class);
                startActivity(intent);
            }
        });


        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));


        try{
            mDatabase.child("genres").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot genreSnapshot: dataSnapshot.getChildren()){
                        Genre genre = genreSnapshot.getValue(Genre.class);
                        mSwipeView.addView(new GenreTinderCard(mContext,genre,mSwipeView));
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
