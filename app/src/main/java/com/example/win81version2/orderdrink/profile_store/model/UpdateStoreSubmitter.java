package com.example.win81version2.orderdrink.profile_store.model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.win81version2.orderdrink.main.view.MainStoreActivity;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Win 8.1 Version 2 on 5/8/2017.
 */

public class UpdateStoreSubmitter  {
    private DatabaseReference mData;
    private MainStoreActivity view;

    public UpdateStoreSubmitter(DatabaseReference mData, MainStoreActivity view) {
        this.mData = mData;
        this.view = view;
    }

    public void updateStatusStore (String idStore, final boolean isOpen){
        mData.child(Constain.STORES).child(idStore).child(Constain.IS_OPEN).setValue(isOpen).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if (isOpen == true) {
                        view.showToast("Mở cửa");
                    }
                    else {
                        view.showToast("Đóng cửa");
                    }
                }

            }
        });
    }
}
