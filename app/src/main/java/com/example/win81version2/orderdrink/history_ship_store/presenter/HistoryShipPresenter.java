package com.example.win81version2.orderdrink.history_ship_store.presenter;

import com.example.win81version2.orderdrink.history_ship_store.model.HistoreShipSubmitter;
import com.example.win81version2.orderdrink.history_ship_store.model.HistoryShipStore;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Win 8.1 Version 2 on 5/14/2017.
 */

public class HistoryShipPresenter {
    private DatabaseReference mData;
    private HistoreShipSubmitter submitter;

    public HistoryShipPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new HistoreShipSubmitter(mData);
    }
    public void createHistoryShip (String idStore, String idUser, String idHistoryShip, HistoryShipStore historyShipStore){
       submitter.createHistoryShip(idStore, idUser, idHistoryShip, historyShipStore);
    }
}
