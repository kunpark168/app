package com.example.win81version2.orderdrink.profile_store.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.win81version2.orderdrink.main.view.MainAdminActivity;
import com.example.win81version2.orderdrink.profile_store.model.CreateStoreSubmitter;
import com.example.win81version2.orderdrink.profile_store.view.CreateStoreActivity;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Win 8.1 Version 2 on 4/22/2017.
 */

public class CreateStorePresenter {
    private DatabaseReference mData;
    private CreateStoreSubmitter submitter;
    private CreateStoreActivity view;
    private FirebaseAuth mAuth;

    public CreateStorePresenter(CreateStoreActivity view, FirebaseAuth mAuth) {
        this.mAuth = mAuth;
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new CreateStoreSubmitter(mData, view);
    }

    public void createNewStore(String email, String password, final String storeName, final String phoneNumber, final String address, final String from, final String to) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    HashMap<String, Object> location = new HashMap<>();
                    location.put(Constain.ADDRESS, address);
                    location.put(Constain.LO, 0);
                    location.put(Constain.LA, 0);
                    HashMap<String, Object> favoriteList = new HashMap<>();
                    HashMap<String, Object> products = new HashMap<>();
                    HashMap<String, Object> orderSchedule = new HashMap<>();
                    String timework = from + "-" + to;
                    addNewStore(task.getResult().getUser().getUid().toString(), storeName, task.getResult().getUser().getEmail(), true, true, phoneNumber, "", timework, location, favoriteList, products, orderSchedule);
                    view.hideProgressDialog();
                    view.showToast("Create new store successful");
                    view.startActivity(new Intent(view, MainAdminActivity.class));
                }
            }
        });
    }

    public void addNewStore(String idStore, String storeName, String email, boolean isStore, boolean isOpen, String phoneNumber, String linkPhotoStore, String timeWork, HashMap<String, Object>location, HashMap<String, Object> favoriteList, HashMap<String, Object> products, HashMap<String, Object> orderSchedule) {
        submitter.addNewStore(idStore, storeName, email, isStore, isOpen, phoneNumber, linkPhotoStore, timeWork,location, favoriteList, products, orderSchedule);
    }
}
