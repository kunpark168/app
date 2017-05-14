package com.example.win81version2.orderdrink.my_cart.model;

import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/12/2017.
 */

public class MyCartSubmitter {
    private DatabaseReference mData;

    public MyCartSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void addProductToCart (String idUser, String idMyCart, String idStore, MyCart myCart){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap = myCart.putMap();
        mData.child(Constain.USERS).child(idUser).child(Constain.MY_CART).child(idStore).child(idMyCart).setValue(myMap);
    }
    public void deleteProductOrder (String idUser, String idMyCart, String idStore){
        mData.child(Constain.USERS).child(idUser).child(Constain.MY_CART).child(idStore).child(idMyCart).removeValue();
    }
    public void updateCountProduct (String idUser, String idMyCart, String idStore, int count){
        mData.child(Constain.USERS).child(idUser).child(Constain.MY_CART).child(idStore).child(idMyCart).child(Constain.COUNT).setValue(count);
    }
    public void updatePrice (String idUser, String idMyCart, String idStore, float price){
        mData.child(Constain.USERS).child(idUser).child(Constain.MY_CART).child(idStore).child(idMyCart).child(Constain.PRICE).setValue(price);
    }
}
