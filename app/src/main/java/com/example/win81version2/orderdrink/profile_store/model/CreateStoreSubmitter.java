package com.example.win81version2.orderdrink.profile_store.model;

import com.example.win81version2.orderdrink.profile_store.view.CreateStoreActivity;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 4/22/2017.
 */

public class CreateStoreSubmitter {
    private DatabaseReference mData;
    private CreateStoreActivity view;

    public CreateStoreSubmitter(DatabaseReference mData, CreateStoreActivity view) {
        this.mData = mData;
        this.view = view;
    }
    public void addNewStore (String idStore, String storeName, String email, boolean isStore, String phoneNumber, String linkPhotoStore, int sumFavorite, HashMap<String, Object>location, HashMap<String, Object> favoriteList, HashMap<String, Object> timeWork, HashMap<String, Object> products, HashMap<String, Object> orderSchedule){
        Store store = new Store(idStore, storeName, email, isStore, phoneNumber, linkPhotoStore, sumFavorite, 0, location, favoriteList, timeWork, products, orderSchedule);
        HashMap<String, Object> myMap = new HashMap<>();
        myMap = store.putMap();
        mData.child(Constain.STORES).child(idStore).setValue(myMap);

    }
}
