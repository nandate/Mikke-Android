package com.service_mikke.mikke.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                startRecommend(mUserId);
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
                        Log.d("a",snapshot.getKey());
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




    private void startRecommend(final String mUserId){
        String url = "https://mikke-rec.herokuapp.com/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("Response",response);
                        Intent intent = new Intent(UsedServicesActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("Error Response",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("UserID",mUserId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
