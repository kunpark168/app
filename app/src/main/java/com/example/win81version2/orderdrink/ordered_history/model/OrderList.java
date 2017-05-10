package com.example.win81version2.orderdrink.ordered_history.model;

import com.example.win81version2.orderdrink.utility.Constain;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/10/2017.
 */

public class OrderList {
    private String idOrderList;
    private String userName;
    private String linkPhotoUser;
    private String phoneNumber;
    private String productName;
    private String timeOrder;
    private int count;
    private int statusOrder; //0 : Đang chờ 1: Đã giao 2 : Bị hủy
    //contructor

    public OrderList() {
    }

    public OrderList(String idOrderList, String userName, String linkPhotoUser, String phoneNumber, String productName, String timeOrder, int count, int statusOrder) {
        this.idOrderList = idOrderList;
        this.userName = userName;
        this.linkPhotoUser = linkPhotoUser;
        this.phoneNumber = phoneNumber;
        this.productName = productName;
        this.timeOrder = timeOrder;
        this.count = count;
        this.statusOrder = statusOrder;
    }
    //put all to HashMap
    public HashMap<String, Object> putMap (){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_ORDERLIST, idOrderList);
        myMap.put(Constain.USER_NAME, userName);
        myMap.put(Constain.LINKPHOTOUSER, linkPhotoUser);
        myMap.put(Constain.PHONENUMBER, phoneNumber);
        myMap.put(Constain.PRODUCT_NAME, productName);
        myMap.put(Constain.TIME_ORDER, timeOrder);
        myMap.put(Constain.COUNT, count);
        myMap.put(Constain.STATUS_PRODUCTS, statusOrder);
        return myMap;
    }

    public String getIdOrderList() {
        return idOrderList;
    }

    public void setIdOrderList(String idOrderList) {
        this.idOrderList = idOrderList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLinkPhotoUser() {
        return linkPhotoUser;
    }

    public void setLinkPhotoUser(String linkPhotoUser) {
        this.linkPhotoUser = linkPhotoUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }
}
