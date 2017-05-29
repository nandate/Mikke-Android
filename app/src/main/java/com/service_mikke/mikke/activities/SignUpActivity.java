package com.service_mikke.mikke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service_mikke.mikke.R;
import com.service_mikke.mikke.helpers.LineLoginHelper;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by takuya on 3/17/17.
 */

public class SignUpActivity extends AppCompatActivity{

    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected Button signUpButton;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;

    private LoginButton mFacebookLoginButton;
    private CallbackManager mFacebookCallbackManager;

    private TwitterLoginButton mTwitterLoginButton;

    private HashMap<String,Integer> tags = new HashMap<>();

    private Button mLineLoginButton;
    private LineLoginHelper mLineLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
        signUpButton = (Button)findViewById(R.id.signupButton);


        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                password = password.trim();
                email = email.trim();

                if(password.isEmpty() || email.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(SignUpActivity.this,new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task){
                                    if(task.isSuccessful()){
                                        createUser(mDatabase,task.getResult().getUser().getUid());
                                        Intent intent =new Intent(SignUpActivity.this,GenresActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle(R.string.login_error_title)
                                                .setPositiveButton(android.R.string.ok,null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                }
            }
        });


        mFacebookCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton = (LoginButton)findViewById(R.id.fb_signupButton);
        mFacebookLoginButton.setReadPermissions("email","public_profile");
        mFacebookLoginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserInfo(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mTwitterLoginButton = (TwitterLoginButton)findViewById(R.id.tw_signupButton);
        mTwitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("tw","twitter error");

            }
        });


        mLineLoginHelper = new LineLoginHelper(this);
        mLineLoginButton = (Button)findViewById(R.id.line_signupButton);

        mLineLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });


    }

    private void createUser(final DatabaseReference mDatabase,final String userId){
        mDatabase.child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot tagsSnapshot:dataSnapshot.getChildren()){
                    tags.put(tagsSnapshot.getValue(String.class),0);
                }
                mDatabase.child("users").child(userId).child("tags_point").setValue(tags);
                mDatabase.child("users").child(userId).child("user_name").setValue(userId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUserInfo(final LoginResult loginResult){
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response){
                        try{
                            String name = object.getString("name");
                            String token = loginResult.getAccessToken().getToken();
                            String picUrl = "https://graph.facebook.com/me/picture?type=normal&method=GET&access_token="+ token;
                            handleFacebookAccessToken(loginResult.getAccessToken(),name,picUrl);
                        }catch (JSONException e){
                            System.out.println(e);
                        }
                    }
                });

        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
        data_request.executeAsync();
    }

    private void createFBUser(final DatabaseReference mDatabase,final String uid,final String name,final String photoUrl){
        mDatabase.child("users").child(uid).child("user_name").setValue(name);
        mDatabase.child("users").child(uid).child("photo_url").setValue(photoUrl);
        mDatabase.child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot tagsSnapshot:dataSnapshot.getChildren()){
                    tags.put(tagsSnapshot.getValue(String.class),0);
                }
                mDatabase.child("users").child(uid).child("tags_point").setValue(tags);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void handleFacebookAccessToken(AccessToken token, final String name, final String photoUrl){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            createFBUser(FirebaseDatabase.getInstance().getReference(),task.getResult().getUser().getUid(),name,photoUrl);
                            Intent intent = new Intent(SignUpActivity.this,GenresActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok,null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
    }

    private void handleTwitterSession(TwitterSession session){
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            createUser(FirebaseDatabase.getInstance().getReference(),task.getResult().getUser().getUid());
                            Intent intent = new Intent(SignUpActivity.this,GenresActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{

                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok,null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }

    private void onTapLineLogin(){
        mLineLoginHelper
                .startLineLogin()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("line","LINE Login was successful.");
                            System.out.println(task);
                            Intent intent = new Intent(SignUpActivity.this,GenresActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else{
                            Log.e("line","error");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        mFacebookCallbackManager.onActivityResult(requestCode,resultCode,data);
    }


}
