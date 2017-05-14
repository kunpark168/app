package com.example.win81version2.orderdrink.history_order_user.presenter;

import com.example.win81version2.orderdrink.history_order_user.model.HistoryOrderSubmitter;
import com.example.win81version2.orderdrink.history_order_user.model.HistoryOrderUser;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Win 8.1 Version 2 on 5/14/2017.
 */

public class HistoryOrderPresenter {
    private DatabaseReference mData;
    private HistoryOrderSubmitter submitter;

    public HistoryOrderPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new HistoryOrderSubmitter(mData);
    }
    public void createHistoryOrder (String idUser, String idHistoryOrder, final String idStore, HistoryOrderUser historyOrderUser){
       submitter.createHistoryOrder(idUser, idHistoryOrder, idStore, historyOrderUser);
    }
    public void deleteMyCart (String idUser, String idStore){
       submitter.deleteMyCart(idUser, idStore);
    }
}
