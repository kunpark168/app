package com.example.win81version2.orderdrink.profile_user.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.win81version2.orderdrink.main.model.UserSubmitter;
import com.example.win81version2.orderdrink.profile_user.model.UserProfileSubmitter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Win 8.1 Version 2 on 5/11/2017.
 */

public class UserProfilePresenter {
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private UserProfileSubmitter submitter;

    public UserProfilePresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        submitter = new UserProfileSubmitter(mData, mAuth, mStorage);
    }

    public void updateUserName(String idUser, String userName) {
        submitter.updateUserName(idUser, userName);
    }

    public void updatePhoneNumber(String idUser, String phoneNumber) {
        submitter.updatePhoneNumber(idUser, phoneNumber);
    }

    public void updateEmail(String idUser, String email) {
        submitter.updateEmail(idUser, email);
    }

    public void updatePhoto(Bitmap bitmap, final String idUser) {
        submitter.updatePhoto(bitmap, idUser);
    }

    public void updateAdress(String idUser, String address) {
        submitter.updateAdress(idUser, address);
    }

    public void updateSumOrderUser(String idUser, int sumOrdered) {
        submitter.updateSumOrderUser(idUser, sumOrdered);
    }
}
