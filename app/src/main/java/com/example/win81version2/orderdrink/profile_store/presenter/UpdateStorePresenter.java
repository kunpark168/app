package com.example.win81version2.orderdrink.profile_store.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.win81version2.orderdrink.main.view.MainStoreActivity;
import com.example.win81version2.orderdrink.profile_store.model.UpdateStoreSubmitter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Win 8.1 Version 2 on 5/8/2017.
 */

public class UpdateStorePresenter {
    private DatabaseReference mData;
    private MainStoreActivity view;
    private UpdateStoreSubmitter submitter;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;

    public UpdateStorePresenter(MainStoreActivity view) {
        this.view = view;
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        submitter = new UpdateStoreSubmitter(mData, this.view, mAuth, mStorage);
    }

    public void updateStatusStore(String idStore, final boolean isOpen) {
        submitter.updateStatusStore(idStore, isOpen);
    }

    public void updateStoreName(String idStore, String storeName) {
        submitter.updateStoreName(idStore, storeName);
    }

    public void updatephoneNumberStore(String idStore, String phoneNumber) {
        submitter.updatephoneNumberStore(idStore, phoneNumber);
    }

    public void updateAddressStore(String idStore, String addressStore) {
        submitter.updateAddressStore(idStore, addressStore);
    }

    public void updateEmailStore(String idStore, String emailStore) {
        submitter.updateEmailStore(idStore, emailStore);
    }

    public void updatePasswordStore(String email, String password, final String newPassword) {
        submitter.updatePasswordStore(email, password, newPassword);
    }
    public void updateCoverStore (Bitmap bitmap, final String idStore){
       submitter.updateCoverStore(bitmap, idStore);
    }

    public void updateAvataStore (Bitmap bitmap, final String idStore){
       submitter.updateAvataStore(bitmap, idStore);
    }
    public void updateSumOrderedStore (String idStore, int sumOrdered){
        submitter.updateSumOrderedStore(idStore, sumOrdered);
    }
    public void updateSumShippedStore (String idStore, int sumShipped){
        submitter.updateSumShippedStore(idStore, sumShipped);
    }
}
