package com.example.win81version2.orderdrink.my_order.presenter;

import com.example.win81version2.orderdrink.my_order.model.MyOrder;
import com.example.win81version2.orderdrink.my_order.model.MyOrderSubmitter;
import com.example.win81version2.orderdrink.ordered_list.model.OrderList;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/12/2017.
 */

public class MyOrderPresenter {
    private DatabaseReference mData;
    private MyOrderSubmitter submitter;

    public MyOrderPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new MyOrderSubmitter(mData);
    }
    public void createMyOrder (String idUser, MyOrder myOrder){
       submitter.createMyOrder(idUser, myOrder);
    }
}
