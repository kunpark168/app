package com.nht.uit.orderdrink.product.presenter;

import android.graphics.Bitmap;

import com.nht.uit.orderdrink.main.view.MainStoreActivity;
import com.nht.uit.orderdrink.product.model.ProductSubmitter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Win 8.1 Version 2 on 5/8/2017.
 */

public class ProductPresenter {
    private DatabaseReference mData;
    private ProductSubmitter submitter;
    private StorageReference mStorage;
    private MainStoreActivity view;

    public ProductPresenter(MainStoreActivity view) {
        this.view = view;
        mData = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();;
        submitter = new ProductSubmitter(view, mData, mStorage);
    }
    public void createProduct (Bitmap bitmap, String idStore, String idCategory, String idProduct, String productName, String describeProduct, float price, int sumProduct){
        submitter.createProduct(bitmap, idStore, idCategory, idProduct, productName, describeProduct, price, sumProduct);
    }
    public void updateProductName (String idStore, String idCategory, String idProduct, String productName){
        submitter.updateProductName(idStore, idCategory, idProduct, productName);
    }
    public void updateDescribe (String idStore, String idCategory, String idProduct, String describe){
        submitter.updateDescribe(idStore, idCategory, idProduct, describe);
    }
    public void updatePrice (String idStore, String idCategory, String idProduct, String price){
        submitter.updatePrice(idStore, idCategory, idProduct, price);
    }
    public void updatePhotoProduct (final String idStore, final String idCategory, final String idProduct, Bitmap bitmap) {
        submitter.updatePhotoProduct(idStore, idCategory, idProduct, bitmap);
    }
    public void updateStatusProduct (String idStore, String idCategory, String idProduct, boolean status){
        submitter.updateStatusProduct(idStore, idCategory, idProduct, status);
    }
}
