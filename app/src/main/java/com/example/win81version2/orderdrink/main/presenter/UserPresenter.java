package com.example.win81version2.orderdrink.main.presenter;

import com.example.win81version2.orderdrink.main.model.UserSubmitter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/4/2017.
 */

public class UserPresenter {
    private DatabaseReference mData;
    private UserSubmitter submitter;

    public UserPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new UserSubmitter(mData);
    }
    public void updateLocation (String idUser, HashMap<String, Object> location){
        submitter.updateLocation(idUser, location);
    }
}
