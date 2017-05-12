package com.example.win81version2.orderdrink.my_order.model;

import android.view.View;

import com.example.win81version2.orderdrink.utility.Constain;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/12/2017.
 */

public class MyOrder {
    private String categoryName;
    private String idProduct;
    private String productName;
    private int count;
    private float price;
    private String timeOrder;
    private int statusOrder; //0 : Ordered (default) 1 : bought
    //Contructor

    public MyOrder() {
    }

    public MyOrder(String categoryName, String idProduct, String productName, int count, String timeOrder, int statusOrder, float price) {
        this.categoryName = categoryName;
        this.idProduct = idProduct;
        this.productName = productName;
        this.count = count;
        this.timeOrder = timeOrder;
        this.statusOrder = statusOrder;
        this.price = price;
    }

    //putMap
    public HashMap<String, Object> putMap() {
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.CATEGORY_NAME, categoryName);
        myMap.put(Constain.ID_PRODUCT, idProduct);
        myMap.put(Constain.PRODUCT_NAME, productName);
        myMap.put(Constain.COUNT, count);
        myMap.put(Constain.TIME_ORDER, timeOrder);
        myMap.put(Constain.STATUS_ORDER, statusOrder);
        myMap.put(Constain.PRICE, price);
        return myMap;
    }

    //getter setter

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
