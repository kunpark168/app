package com.example.win81version2.orderdrink.history_ship_store.model;

import com.example.win81version2.orderdrink.product.model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win 8.1 Version 2 on 5/15/2017.
 */

public class ListOrderProduct {
    public ArrayList<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(ArrayList<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    private ArrayList<OrderProduct> orderProductList;

    public ListOrderProduct(ArrayList<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }
}
