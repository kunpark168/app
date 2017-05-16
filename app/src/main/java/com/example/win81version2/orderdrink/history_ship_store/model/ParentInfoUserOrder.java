package com.example.win81version2.orderdrink.history_ship_store.model;

/**
 * Created by Win 8.1 Version 2 on 5/15/2017.
 */

public class ParentInfoUserOrder {
    private String nameUser;
    private String linkPhotoUser;
    private String phoneNumberUser;
    private String addressUser;
    private String timeOrder;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public String getNameUser() {
        return nameUser;
    }

    public ParentInfoUserOrder(String nameUser, String linkPhotoUser, String phoneNumberUser, String addressUser, String timeOrder) {
        this.nameUser = nameUser;
        this.linkPhotoUser = linkPhotoUser;
        this.phoneNumberUser = phoneNumberUser;
        this.addressUser = addressUser;
        this.timeOrder = timeOrder;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLinkPhotoUser() {
        return linkPhotoUser;
    }

    public void setLinkPhotoUser(String linkPhotoUser) {
        this.linkPhotoUser = linkPhotoUser;
    }

    public String getPhoneNumberUser() {
        return phoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        this.phoneNumberUser = phoneNumberUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }
}
