package com.example.win81version2.orderdrink.my_order.model;

import com.example.win81version2.orderdrink.ordered_list.model.OrderList;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/12/2017.
 */

public class MyOrderSubmitter {
    private DatabaseReference mData;

    public MyOrderSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void createMyOrder (String idUser, MyOrder myOrder){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap = myOrder.putMap();
        mData.child(Constain.USERS).child(idUser).child(Constain.MY_ORDER).push().setValue(myMap);
    }
}
