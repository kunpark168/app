package com.example.win81version2.orderdrink.Login.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.win81version2.orderdrink.Login.model.LoginSubmitter;
import com.example.win81version2.orderdrink.Login.view.LoginActivity;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.utility.Constain;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Win 8.1 Version 2 on 4/21/2017.
 */

public class LoginFacebookPresenter {
    private LoginSubmitter submitter;
    private FirebaseAuth mAuth;
    private LoginActivity view;
    private DatabaseReference mData;

    public LoginFacebookPresenter(LoginActivity view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new LoginSubmitter(mData, view);

    }
    public void loginFacebook (Context context, CallbackManager callbackManager){
        //khởi tạo Facebook SDK
        FacebookSdk.sdkInitialize(context);
        String [] listPermisstion = {"email", "public_profile", "user_friends"};
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(view, Arrays.asList(listPermisstion));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                 view.showToast("Đăng nhập thành công!");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                view.showToast("Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                view.showToast("Error");
            }
        });

    }
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //creare new user on firebase
                    HashMap<String, Object> location = new HashMap<>();
                    HashMap<String, Object> favorite_drink = new HashMap<>();
                    submitter.addUser(task.getResult().getUser().getUid(), task.getResult().getUser().getDisplayName(), task.getResult().getUser().getEmail(), true, "", task.getResult().getUser().getPhotoUrl().toString(), "", false, location,favorite_drink);
                    view.moveToMainActivity();
                }
                else {
                    view.moveToMainActivity();
                }
            }
        });
    }

}
