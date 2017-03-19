package com.service_mikke.mikke.models;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.service_mikke.mikke.R;

/**
 * Created by takuya on 3/19/17.
 */


@Layout(R.layout.service_card_view)
public class ServiceTinderCard {
    @View(R.id.service_name)
    private TextView service_name_view;

    @View(R.id.company_name)
    private TextView company_name_view;

    @View(R.id.service_description)
    private TextView service_description_view;


    private Service mService;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public ServiceTinderCard(Context context,Service service,SwipePlaceHolderView swipeView){
        mContext = context;
        mService = service;
        mSwipeView = swipeView;
    }


    @Resolve
    private void onResolved(){
        service_name_view.setText(mService.getName());
        company_name_view.setText(mService.getTitle());
        service_description_view.setText(mService.getDescription());
    }

    @Click(R.id.service_name)
    private void onClick(){
        Log.d("EVENT", "profileImageView click");
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        User user = User.getInstance();
        user.addUsedService(mService);
        System.out.println(user.getSelected_genres());
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");

    }
}
