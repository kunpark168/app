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

    public void createHistoryShip(String idStore, String idUser, String idHistoryShip, HistoryShipStore historyShipStore) {
        mData.child(Constain.STORES).child(idStore).child(Constain.HISTORY_SHIP_STORE).child(idUser).child(idHistoryShip).setValue(historyShipStore);
    }
    public void updateStatusOrderStore (String idStore, String idUser, String idHistoryShip, int statusOrder){
        mData.child(Constain.STORES).child(idStore).child(Constain.HISTORY_SHIP_STORE).child(idUser).child(idHistoryShip).child(Constain.STATUS_ORDER).setValue(statusOrder);
    }
    public void updateStatusOrderUser (String idStore, String idUser, String idHistory, int statusOrder){
        mData.child(Constain.USERS).child(idUser).child(Constain.HISTORY_ORDER_USER).child(idStore).child(idHistory).child(Constain.STATUS_ORDER).setValue(statusOrder);
    }
    public void updateSumShippedStore (String idStore, int sumShipped){
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_SHIPPED).setValue(sumShipped);
    }
    public void updateSumShippedUser (String idUser, int sumShipped){
        mData.child(Constain.USERS).child(idUser).child(Constain.SUM_SHIPPED).setValue(sumShipped);
    }
}
