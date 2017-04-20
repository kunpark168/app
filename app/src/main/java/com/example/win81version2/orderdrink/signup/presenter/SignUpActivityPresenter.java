package com.example.win81version2.orderdrink.signup.presenter;

import com.example.win81version2.orderdrink.signup.model.SignUpActvitySubmitter;
import com.example.win81version2.orderdrink.signup.view.SignupActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Win 8.1 Version 2 on 4/20/2017.
 */

public class SignUpActivityPresenter {
    private DatabaseReference mData;
    private SignupActivity view;
    private SignUpActvitySubmitter submitter;

    public SignUpActivityPresenter(SignupActivity view) {
        this.view = view;
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new SignUpActvitySubmitter(mData);
    }
    public void signUpWithEmail (String email, String password){
        submitter.signUpWithEmail(email, password);
    }
}
