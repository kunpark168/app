package com.example.win81version2.orderdrink.history_ship_store.model;

import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Win 8.1 Version 2 on 5/14/2017.
 */

public class HistoreShipSubmitter {
    private DatabaseReference mData;

    public HistoreShipSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void createHistoryShip (String idStore, String idUser, String idHistoryShip, HistoryShipStore historyShipStore){
        mData.child(Constain.STORES).child(idStore).child(Constain.HISTORY_SHIP_STORE).child(idUser).child(idHistoryShip).setValue(historyShipStore);
    }
}
