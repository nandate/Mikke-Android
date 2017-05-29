package com.service_mikke.mikke.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.service_mikke.mikke.R;

import org.json.JSONObject;

import java.util.HashMap;

import jp.line.android.sdk.LineSdkContext;
import jp.line.android.sdk.LineSdkContextManager;
import jp.line.android.sdk.exception.LineSdkLoginException;
import jp.line.android.sdk.login.LineAuthManager;
import jp.line.android.sdk.login.LineLoginFuture;
import jp.line.android.sdk.login.LineLoginFutureListener;

/**
 * Created by takuya on 5/23/17.
 */

public class LineLoginHelper {
    private String mLineAccesscodeVerificationEndpoint;
    private static final String TAG = LineLoginHelper.class.getSimpleName();

    private Activity mActivity;

    public LineLoginHelper(Activity activity){
        mActivity = activity;
        mLineAccesscodeVerificationEndpoint =
                "https://nameless-inlet-90700.herokuapp.com/verifyToken";
    }

    public Task<AuthResult> startLineLogin(){
        Task<AuthResult> conmbinedTask =
                getLineAccessCode(mActivity)
                .continueWithTask(new Continuation<String,Task<String>>(){
                    @Override
                    public Task<String> then(@NonNull Task<String> task) throws Exception{
                        String lineAccessCode = task.getResult();
                        return getFirebaseAuthToken(mActivity,lineAccessCode);
                    }
        }).continueWithTask(new Continuation<String,Task<AuthResult>>(){
                    @Override
                    public Task<AuthResult> then(@NonNull Task<String> task) throws Exception{
                        String firebaseToken = task.getResult();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        return auth.signInWithCustomToken(firebaseToken);
                    }
                });
        return conmbinedTask;
    }

    private Task<String> getLineAccessCode(final Activity activity){
        final TaskCompletionSource<String> source = new TaskCompletionSource<>();
        LineSdkContextManager.initialize(mActivity);
        LineSdkContext sdkContext = LineSdkContextManager.getSdkContext();
        LineAuthManager authManager = sdkContext.getAuthManager();
        LineLoginFuture loginFuture = authManager.login(activity);
        loginFuture.addFutureListener(new LineLoginFutureListener() {
            @Override
            public void loginComplete(LineLoginFuture lineLoginFuture) {
                switch (lineLoginFuture.getProgress()){
                    case SUCCESS:
                        String lineAccessToken = lineLoginFuture.getAccessToken().accessToken;
                        Log.d(TAG,"LINE Access token =" + lineAccessToken);
                        source.setResult(lineAccessToken);
                        break;

                    case CANCELED:
                        Exception e = new Exception("User cancelled LINE login.");
                        source.setException(e);
                        break;

                    default:
                        Throwable cause = lineLoginFuture.getCause();
                        if(cause instanceof LineSdkLoginException){
                            LineSdkLoginException loginException = (LineSdkLoginException)cause;
                            Log.e(TAG,loginException.getMessage());
                            source.setException(loginException);
                        }else {
                            source.setException(new Exception("Unknown error occurred in LINE SDK"));
                        }
                        break;
                }
            }
        });
        return source.getTask();
    }

    private Task<String> getFirebaseAuthToken(Context context,final String lineAccessToken){
        final TaskCompletionSource<String> source = new TaskCompletionSource<>();

        HashMap<String,String> validationObject = new HashMap<>();
        validationObject.put("token",lineAccessToken);

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try{
                    String firebaseToken = response.getString("firebase_token");
                    Log.d(TAG,"Firebase Token :" + firebaseToken);
                    source.setResult(firebaseToken);
                }catch (Exception e){
                    source.setException(e);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(TAG,error.toString());
                source.setException(error);
            }
        };

        JsonObjectRequest TokenRequest = new JsonObjectRequest(
                Request.Method.POST,mLineAccesscodeVerificationEndpoint,
                new JSONObject(validationObject),
                responseListener,errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(TokenRequest);
        return source.getTask();
    }

    public void LinesignOut(){
        FirebaseAuth.getInstance().signOut();
        LineSdkContextManager.getSdkContext().getAuthManager().logout();
    }
}
