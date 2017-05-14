package com.example.win81version2.orderdrink.ordered_list_store.model;

import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Win 8.1 Version 2 on 5/11/2017.
 */

public class OrderListSubmitter  {
    private DatabaseReference mData;
    public OrderListSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void updateStatusOrder (String idStore, String idOrderList, int statusProduct){
        mData.child(Constain.STORES).child(Constain.ORDER_LIST).child(idOrderList).child(Constain.STATUS_PRODUCTS).setValue(statusProduct);
    }
}
