package com.example.win81version2.orderdrink.history_ship_store.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.example.win81version2.orderdrink.product.model.OrderProduct;

import java.util.List;

/**
 * Created by Win 8.1 Version 2 on 5/15/2017.
 */

public class ParentHistoryStore implements Parent<ListOrderProduct> {

    private List<ListOrderProduct> mChildrenProduct;

    public ParentInfoUserOrder getInfoUserOrder() {
        return infoUserOrder;
    }

    public ParentHistoryStore(List<ListOrderProduct> mChildrenProduct, ParentInfoUserOrder infoUserOrder) {
        this.mChildrenProduct = mChildrenProduct;
        this.infoUserOrder = infoUserOrder;
    }

    public void setInfoUserOrder(ParentInfoUserOrder infoUserOrder) {
        this.infoUserOrder = infoUserOrder;
    }

    private ParentInfoUserOrder infoUserOrder;


    @Override
    public List<ListOrderProduct> getChildList() {
        return mChildrenProduct;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
