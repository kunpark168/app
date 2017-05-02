package com.example.win81version2.orderdrink.category.presenter;

import com.example.win81version2.orderdrink.category.model.CategorySubmitter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Win 8.1 Version 2 on 4/26/2017.
 */

public class CategoryPresenter {
    private DatabaseReference mData;
    private CategorySubmitter submitter;

    public CategoryPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new CategorySubmitter(mData);
    }
}
