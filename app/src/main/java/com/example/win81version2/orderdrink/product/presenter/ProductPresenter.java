package com.example.win81version2.orderdrink.product.presenter;

import android.graphics.Bitmap;

import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.product.model.ProductSubmitter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/8/2017.
 */

public class ProductPresenter {
    private DatabaseReference mData;
    private ProductSubmitter submitter;
    private StorageReference mStorage;

    public ProductPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();;
        submitter = new ProductSubmitter(mData, mStorage);
    }
    public void createProduct (Bitmap bitmap, String idStore, String idCategory, String idProduct, String productName, String describeProduct, float price){
        submitter.createProduct(bitmap, idStore, idCategory, idProduct, productName, describeProduct, price);
    }
}
