package com.example.win81version2.orderdrink.product.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.win81version2.orderdrink.utility.Constain;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 4/21/2017.
 */

public class Product implements Parcelable{
    private String idProduct;
    private String productName;
    private String linkPhotoProduct;
    private int rating;
    private float price;
    private String infoProduct;
    private boolean status;
    //Contructor


    public Product() {
    }

    public Product(String idProduct, String productName, String linkPhotoProduct, int rating, float price, String infoProduct, boolean status) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.linkPhotoProduct = linkPhotoProduct;
        this.rating = rating;
        this.price = price;
        this.infoProduct = infoProduct;
        this.status = status;
    }
    //put a Map and set on FireBase
    public HashMap<String, Object> myMap() {
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_PRODUCT, idProduct);
        myMap.put(Constain.PRODUCT_NAME, productName);
        myMap.put(Constain.LINK_PHOTO_PRODUCT, linkPhotoProduct);
        myMap.put(Constain.RATING, rating);
        myMap.put(Constain.PRICE, price);
        myMap.put(Constain.INFO_PRODUCT, infoProduct);
        myMap.put(Constain.STATUS, status);
        return myMap;
    }
    //Getter and setter

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLinkPhotoProduct() {
        return linkPhotoProduct;
    }

    public void setLinkPhotoProduct(String linkPhotoProduct) {
        this.linkPhotoProduct = linkPhotoProduct;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getInfoProduct() {
        return infoProduct;
    }

    public void setInfoProduct(String infoProduct) {
        this.infoProduct = infoProduct;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /////Parcelable
    protected Product (Parcel parcel){
        idProduct = parcel.readString();
        productName= parcel.readString();
        linkPhotoProduct= parcel.readString();
        rating = parcel.readInt();
        price = parcel.readFloat();
        infoProduct= parcel.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idProduct);
        parcel.writeString(productName);
        parcel.writeString(linkPhotoProduct);
        parcel.writeString(infoProduct);
        parcel.writeInt(rating);
        parcel.writeFloat(price);
    }
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel parcel) {
            return new Product(parcel);
        }

        @Override
        public Product[] newArray(int i) {
            return new Product[i];
        }
    };
}
