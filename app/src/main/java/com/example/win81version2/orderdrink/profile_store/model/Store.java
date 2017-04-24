package com.example.win81version2.orderdrink.profile_store.model;

import com.example.win81version2.orderdrink.utility.Constain;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 4/21/2017.
 */

public class Store {
    private String idStore;
    private String storeName;
    private String email;
    private boolean isStore;
    private String phoneNumber;
    private String linkPhotoStore;
    private int sumFavorite;
    private int sumShipped;
    private HashMap<String, Object> location;
    private HashMap<String, Object> favoriteList;
    private HashMap<String, Object> timeWork;
    private HashMap<String, Object> products;
    private HashMap<String, Object> orderSchedule;

    //Contructor
    public Store() {
    }

    public Store(String idStore, String storeName, String email, boolean isStore, String phoneNumber, String linkPhotoStore, int sumFavorite, int sumShipped, HashMap<String, Object> location, HashMap<String, Object> favorite_list, HashMap<String, Object> timeWork, HashMap<String, Object> products, HashMap<String, Object> orderSchedule) {
        this.idStore = idStore;
        this.storeName = storeName;
        this.email = email;
        this.isStore = isStore;
        this.phoneNumber = phoneNumber;
        this.linkPhotoStore = linkPhotoStore;
        this.sumFavorite = sumFavorite;
        this.sumShipped = sumShipped;
        this.location = location;
        this.favoriteList = favorite_list;
        this.timeWork = timeWork;
        this.products = products;
        this.orderSchedule = orderSchedule;
    }
    //put everything a HashMap
    public HashMap<String, Object> putMap (){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_STORE, idStore);
        myMap.put(Constain.STORE_NAME, storeName);
        myMap.put(Constain.EMAIL, email);
        myMap.put(Constain.IS_STORE, isStore);
        myMap.put(Constain.PHONENUMBER, phoneNumber);
        myMap.put(Constain.LINKPHOTOSTORE, linkPhotoStore);
        myMap.put(Constain.SUMFAVORITE, sumFavorite);
        myMap.put(Constain.SUM_SHIPPED, sumShipped);
        myMap.put(Constain.LOCATION, location);
        myMap.put(Constain.FAVORITE_LIST, favoriteList);
        myMap.put(Constain.TIME_WORK, timeWork);
        myMap.put(Constain.PRODUCTS, products);
        myMap.put(Constain.ORDER_SCHEDULE, orderSchedule);
        return myMap;
    }
    //getter and setter

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStore() {
        return isStore;
    }

    public void setStore(boolean store) {
        isStore = store;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLinkPhotoStore() {
        return linkPhotoStore;
    }

    public void setLinkPhotoStore(String linkPhotoStore) {
        this.linkPhotoStore = linkPhotoStore;
    }

    public int getSumFavorite() {
        return sumFavorite;
    }

    public void setSumFavorite(int sumFavorite) {
        this.sumFavorite = sumFavorite;
    }

    public HashMap<String, Object> getLocation() {
        return location;
    }

    public void setLocation(HashMap<String, Object> location) {
        this.location = location;
    }

    public HashMap<String, Object> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(HashMap<String, Object> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public HashMap<String, Object> getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(HashMap<String, Object> timeWork) {
        this.timeWork = timeWork;
    }

    public HashMap<String, Object> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Object> products) {
        this.products = products;
    }

    public HashMap<String, Object> getOrderSchedule() {
        return orderSchedule;
    }

    public void setOrderSchedule(HashMap<String, Object> orderSchedule) {
        this.orderSchedule = orderSchedule;
    }
}
