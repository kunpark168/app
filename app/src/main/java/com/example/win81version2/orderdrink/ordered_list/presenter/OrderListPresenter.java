package com.example.win81version2.orderdrink.ordered_list.presenter;

import com.example.win81version2.orderdrink.ordered_list.model.OrderListSubmitter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Win 8.1 Version 2 on 5/11/2017.
 */

public class OrderListPresenter {
    private DatabaseReference mData;
    private OrderListSubmitter submitter;

    public OrderListPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new OrderListSubmitter(mData);
    }
    public void updateStatusOrder (String idStore, String idOrderList, int statusProduct){
        submitter.updateStatusOrder(idStore, idOrderList, statusProduct);
    }
}
