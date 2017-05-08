package com.example.win81version2.orderdrink.product.model;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Win 8.1 Version 2 on 5/7/2017.
 */

public class ProductSubmitter {
    private DatabaseReference mData;

    public ProductSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void createProduct (String idStore, String idCategory, String idProduct, String productName, String linkPhotoProduct, String describeProduct, float price, boolean status){
        Product product = new Product(idProduct, productName, linkPhotoProduct, 0, price, describeProduct, status);
    }
}
