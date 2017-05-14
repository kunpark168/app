package com.example.win81version2.orderdrink.ordered_list_store.model;

import com.example.win81version2.orderdrink.product.model.Flag_Product;
import com.example.win81version2.orderdrink.utility.Constain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 5/10/2017.
 */

public class OrderList {
    private String idOrderList;
    private String idUser;
    private String userName;
    private String linkPhotoUser;
    private String phoneNumber;
    private String productName;
    private String timeOrder;
    private ArrayList<Flag_Product> arrProduct;
    private int statusOrder; // 0 : Đang chờ // 1: Đã giao //2 : Bị hủy
    //contructor
    public OrderList() {
    }
    public OrderList(String idOrderList, String idUser, String userName, String linkPhotoUser, String phoneNumber, String productName, String timeOrder, ArrayList<Flag_Product> arrProduct, int statusOrder) {
        this.idOrderList = idOrderList;
        this.idUser = idUser;
        this.userName = userName;
        this.linkPhotoUser = linkPhotoUser;
        this.phoneNumber = phoneNumber;
        this.productName = productName;
        this.timeOrder = timeOrder;
        this.arrProduct = arrProduct;
        this.statusOrder = statusOrder;
    }
    //put all to HashMap
    public HashMap<String, Object> putMap (){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_ORDERLIST, idOrderList);
        myMap.put(Constain.ID_USER, idUser);
        myMap.put(Constain.USER_NAME, userName);
        myMap.put(Constain.LINKPHOTOUSER, linkPhotoUser);
        myMap.put(Constain.PHONENUMBER, phoneNumber);
        myMap.put(Constain.PRODUCT_NAME, productName);
        myMap.put(Constain.TIME_ORDER, timeOrder);
        myMap.put(Constain.PRODUCT_LIST, arrProduct);
        myMap.put(Constain.STATUS_PRODUCTS, statusOrder);
        return myMap;
    }

    public String getIdOrderList() {
        return idOrderList;
    }

    public void setIdOrderList(String idOrderList) {
        this.idOrderList = idOrderList;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public ArrayList<Flag_Product> getArrProduct() {
        return arrProduct;
    }

    public void setArrProduct(ArrayList<Flag_Product> arrProduct) {
        this.arrProduct = arrProduct;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }
}
