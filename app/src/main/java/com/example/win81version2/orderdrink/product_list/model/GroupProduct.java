package com.example.win81version2.orderdrink.product_list.model;

import android.os.Parcelable;


import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.search_user.model.SearchStore;


import java.util.List;
import java.util.UUID;

/**
 * Created by Nhan on 5/9/2017.
 */

public class GroupProduct implements Parent<Product> {

    private List<Product> mChildrenList;
    private String title;

    public GroupProduct(String title , List<Product> mChildrenList) {
        this.title = title;
        this.mChildrenList = mChildrenList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Product> getChildList() {
        return mChildrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
