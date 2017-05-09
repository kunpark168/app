package com.example.win81version2.orderdrink.product.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.win81version2.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/7/2017.
 */

public class ProductSubmitter {
    private DatabaseReference mData;
    private StorageReference mStorage;
    private String linkPhotoProduct = "";

    public ProductSubmitter(DatabaseReference mData, StorageReference mStorage) {
        this.mData = mData;
        this.mStorage = mStorage;
    }

    public void createProduct (Bitmap bitmap, String idStore, String idCategory, String idProduct, String productName, String describeProduct, float price){
        uploadPhotoProduct(bitmap, idStore, idCategory, idProduct);
        Product product = new Product(idProduct, productName, linkPhotoProduct, 0, price, describeProduct, true);
        HashMap<String, Object> myMap = product.myMap();
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).setValue(myMap);
    }
    public void uploadPhotoProduct (Bitmap bitmap, String idStore, String idCategory, String idProduct){
        StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(idCategory).child(idProduct);
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
                linkPhotoProduct = String.valueOf(downloadUrl);
            }
        });
    }
}
