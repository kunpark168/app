package com.example.win81version2.orderdrink.category.model;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Win 8.1 Version 2 on 4/26/2017.
 */

public class CategorySubmitter  {
    private DatabaseReference mData;

    public CategorySubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
}
