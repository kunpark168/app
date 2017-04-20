package com.example.win81version2.orderdrink.Login.model;

import android.support.annotation.NonNull;

import com.example.win81version2.orderdrink.Login.view.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Win 8.1 Version 2 on 4/20/2017.
 */

public class LoginSubmitter {

    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private LoginActivity view;

    public LoginSubmitter(DatabaseReference mData) {
        this.mData = mData;
        mAuth = FirebaseAuth.getInstance();
    }
    public void login (String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    view.hideProgressDialog();
                    view.showToast("Đăng Nhập Thành Công");
                }
                else {
                    view.hideProgressDialog();
                    view.showToast("Email hoặc password không đúng");
                }
            }
        });
    }
}
