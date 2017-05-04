package com.example.win81version2.orderdrink.main.model;

import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/4/2017.
 */

public class UserSubmitter {
    private DatabaseReference mData;

    public UserSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void updateLocation (String idUser, HashMap<String, Object> location){
        mData.child(Constain.USERS).child(idUser).child(Constain.LOCATION).setValue(location);
    }
}
