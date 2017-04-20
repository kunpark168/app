package com.example.win81version2.orderdrink.signup.model;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.win81version2.orderdrink.main.view.MainActivity;
import com.example.win81version2.orderdrink.signup.view.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Win 8.1 Version 2 on 4/20/2017.
 */

public class SignUpActvitySubmitter {
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private SignupActivity view;

    public SignUpActvitySubmitter(DatabaseReference mData) {
        this.mData = mData;
        mAuth = FirebaseAuth.getInstance();
    }
    public void signUpWithEmail (String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    view.hideProgressDialog();
                    view.showToast("Đăng ký thành công");
                    Intent intent = new Intent(view, MainActivity.class);
                    view.startActivity(intent);

                }
                else {
                    view.hideProgressDialog();
                    view.showToast("Đăng ký không thành công, vui lòng thử lại!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideProgressDialog();
                view.showToast("Email đã được đăng ký");
            }
        });
    }
}
