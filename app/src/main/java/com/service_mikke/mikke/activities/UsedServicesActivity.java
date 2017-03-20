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
import com.mindorks.placeholderview.annotations.LongClick;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.models.Genre;
import com.service_mikke.mikke.models.Service;
import com.service_mikke.mikke.models.ServiceTinderCard;
import com.service_mikke.mikke.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by takuya on 3/18/17.
 */

public class UsedServicesActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private Button next_view_button;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    private List<Service> services = new ArrayList<>();
    private HashMap<String,Integer> tags = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used_services);
        Toolbar toolbar = (Toolbar)findViewById(R.id.used_service_toolbar);
        setSupportActionBar(toolbar);

        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.used_services_swipeView);
        mContext = getApplicationContext();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId =mFirebaseUser.getUid();

        User.getInstance().getUsed_services();

        next_view_button = (Button)findViewById(R.id.next_view_butotn);

        next_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTagPoint(User.getInstance().getUsed_services(),mDatabase,mUserId);

                Intent intent = new Intent(view.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));

        try{
            final List<String> service_names = new ArrayList<>();
            for(int i=0;i<User.getInstance().getSelected_genres().size();i++){
                Genre genre = User.getInstance().getSelected_genres().get(i);
                ArrayList<String> genre_services = genre.getServices();
                service_names.add(genre_services.get(0));
            }

            mDatabase.child("services").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String name = (String)snapshot.child("name").getValue();
                        for(int i=0;i<service_names.size();i++){
                            if(name.equals(service_names.get(i))){
                                Service service = new Service();

                                service.setName(name);
                                service.setTitle((String)snapshot.child("title").getValue());
                                service.setCost((String)snapshot.child("cost").getValue());
                                service.setDescription((String)snapshot.child("description").getValue());
                                service.setLink((String)snapshot.child("link").getValue());
                                ArrayList<String> tags = (ArrayList<String>)snapshot.child("tags").getValue();
                                service.setTags(tags);

                                services.add(service);

                                mSwipeView.addView(new ServiceTinderCard(mContext,service,mSwipeView));
                            }
                        }
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


    private void addTagPoint(final List<Service> services, final DatabaseReference mDatabase, final String UserId)
    {
        final ArrayList<String> tag_names = new ArrayList<String>();
        for(int i = 0;i < services.size();i++) {
            Service service = services.get(i);
            tag_names.addAll(service.getTags());
        }

        mDatabase.child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot tagsSnapshot:dataSnapshot.getChildren()){
                    tags.put(tagsSnapshot.getValue(String.class),0);
                }
                for(int i=0;i<tag_names.size();i++){
                    String name =tag_names.get(i);
                    if(tags.containsKey(name)){
                        Integer point = tags.get(name);
                        tags.put(name,point+1);
                    }else{
                        tags.put(name,1);
                    }
                }
                mDatabase.child("users").child(UserId).child("tags_point").setValue(tags);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}