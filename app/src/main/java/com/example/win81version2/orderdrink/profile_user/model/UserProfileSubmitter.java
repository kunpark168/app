package com.example.win81version2.orderdrink.profile_user.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.win81version2.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/11/2017.
 */

public class UserProfileSubmitter {
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private String linkPhotoUser = "";

    public UserProfileSubmitter(DatabaseReference mData, FirebaseAuth mAuth, StorageReference mStorage) {
        this.mData = mData;
        this.mAuth = mAuth;
        this.mStorage = mStorage;
    }
    public void updateUserName (String idUser, String userName){
        mData.child(Constain.USERS).child(idUser).child(Constain.USER_NAME).setValue(userName);
    }
    public void updatePhoneNumber (String idUser, String phoneNumber){
        mData.child(Constain.USERS).child(idUser).child(Constain.PHONENUMBER).setValue(phoneNumber);
    }
    public void updateEmail (String idUser, String email){
        mData.child(Constain.USERS).child(idUser).child(Constain.EMAIL).setValue(email);
        mAuth.getCurrentUser().updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("OK", "OK");
                }
                else {
                    Log.d("OK", "FAILED");
                }
            }
        });
    }
    public void updatePhoto (Bitmap bitmap, final String idUser){
        StorageReference mountainsRef = mStorage.child(Constain.USERS).child(idUser);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                linkPhotoUser = String.valueOf(downloadUrl);
                if (!linkPhotoUser.equals("")){
                    updateLinkPhotoUser(idUser, linkPhotoUser);
                }
            }
        });
    }
    public void updateAdress (String idUser, String address){
        mData.child(Constain.USERS).child(idUser).child(Constain.LOCATION).child(Constain.ADDRESS).setValue(address);
    }
    public void updateLinkPhotoUser (String idUser, String linkPhotoUser){
        mData.child(Constain.USERS).child(idUser).child(Constain.LINKPHOTOUSER).setValue(linkPhotoUser);
    }
}
