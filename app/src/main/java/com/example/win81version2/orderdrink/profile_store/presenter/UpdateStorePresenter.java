package com.example.win81version2.orderdrink.profile_store.presenter;

import com.example.win81version2.orderdrink.main.view.MainStoreActivity;
import com.example.win81version2.orderdrink.profile_store.model.UpdateStoreSubmitter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Win 8.1 Version 2 on 5/8/2017.
 */

public class UpdateStorePresenter {
    private DatabaseReference mData;
    private MainStoreActivity view;
    private UpdateStoreSubmitter submitter;

    public UpdateStorePresenter(MainStoreActivity view) {
        this.view = view;
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new UpdateStoreSubmitter(mData, view);
    }

    public void updateStatusStore(String idStore, final boolean isOpen) {
        submitter.updateStatusStore(idStore, isOpen);
    }
}
