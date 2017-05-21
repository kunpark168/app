package com.nht.uit.orderdrink.product.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.nht.uit.orderdrink.main.view.MainStoreActivity;
import com.nht.uit.orderdrink.product.view.CreateProductFragment;
import com.nht.uit.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/7/2017.
 */

public class ProductSubmitter {
    private DatabaseReference mData;
    private StorageReference mStorage;
    private String linkPhotoProduct = "";
    private MainStoreActivity view;
    private CreateProductFragment createProductFragment;

    public ProductSubmitter(MainStoreActivity view, DatabaseReference mData, StorageReference mStorage) {
        this.view = view;
        this.mData = mData;
        this.mStorage = mStorage;
        createProductFragment = new CreateProductFragment();
    }

    public void createProduct(Bitmap bitmap, final String idStore, final String idCategory, final String idProduct, final String productName, final String describeProduct, final float price, final int sumProduct) {
        view.showProgressDialog();
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
                Product product = new Product(idProduct, idCategory, productName, linkPhotoProduct, 0, price, describeProduct, true);
                if (!linkPhotoProduct.equals("")) {
                    HashMap<String, Object> myMap = product.myMap();
                    mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).setValue(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                view.hideProgressDialog();
                                view.showToast("Tạo sản phẩm thành công");
                                updateSumProductStore(idStore, sumProduct);
                            }
                            else {
                                view.hideProgressDialog();
                                view.showToast("Tạo sản phẩm không thành công, vui lòng thử lại");
                            }
                        }
                    });
                } else {
                    view.hideProgressDialog();
                    view.showToast("Tạo sản phẩm không thành công, vui lòng thử lại");
                }
            }
        });
    }

    public void updateProductName(String idStore, String idCategory, String idProduct, String productName) {
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).child(Constain.PRODUCT_NAME).setValue(productName);
    }

    public void updateDescribe(String idStore, String idCategory, String idProduct, String describe) {
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).child(Constain.INFO_PRODUCT).setValue(describe);
    }

    public void updatePrice(String idStore, String idCategory, String idProduct, String price) {
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).child(Constain.PRICE).setValue(price);
    }

    public void updateLinkPhotoProduct(String idStore, String idCategory, String idProduct, String linkPhotoProduct) {
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).child(Constain.PRICE).setValue(linkPhotoProduct);
    }

    public void updatePhotoProduct(final String idStore, final String idCategory, final String idProduct, Bitmap bitmap) {
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
                if (!linkPhotoProduct.equals("")) {
                    updateLinkPhotoProduct(idStore, idCategory, idProduct, linkPhotoProduct);
                }
            }
        });
    }
    public void updateStatusProduct (String idStore, String idCategory, String idProduct, boolean status){
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).child(Constain.STATUS).setValue(status);
    }

    public void updateSumProductStore (String idStore, int sumProduct){
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_PRODUCT).setValue(sumProduct);
    }
}
