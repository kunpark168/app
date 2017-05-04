package com.example.win81version2.orderdrink.store_list.presenter;

import com.example.win81version2.orderdrink.store_list.model.StoreListSubmitter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/4/2017.
 */

public class StoreListPresenter {
    private DatabaseReference mData;
    private StoreListSubmitter submitter;

    public StoreListPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new StoreListSubmitter(mData);
    }
    public void removeHeart (String idStore, String idUser){
        submitter.removeHeart(idStore, idUser);
    }
    public void addHeart (String idStore, String idUser, String emailUser){
        submitter.addHeart(idStore, idUser, emailUser);
    }

}
