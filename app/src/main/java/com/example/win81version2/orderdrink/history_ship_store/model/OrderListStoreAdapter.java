package com.example.win81version2.orderdrink.history_ship_store.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.product.model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win 8.1 Version 2 on 5/15/2017.
 */

public class OrderListStoreAdapter extends ExpandableRecyclerAdapter<ParentHistoryStore , ListOrderProduct , ParentOrderViewHolder , ChildOrderViewHolder> {

    List<ParentHistoryStore> parentList;
    LayoutInflater inflater ;
    Context mContext;
    public OrderListStoreAdapter(Context mContext , @NonNull List<ParentHistoryStore> parentList) {
        super(parentList);
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.parentList = parentList;
    }

    @Override
    public ParentOrderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.item_store_history_parent, parentViewGroup, false);
        return new ParentOrderViewHolder(recipeView);
    }

    @Override
    public ChildOrderViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.item_store_history_child, childViewGroup, false);
        return new ChildOrderViewHolder(recipeView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentOrderViewHolder parentViewHolder, int parentPosition, @NonNull ParentHistoryStore parent) {
        parentViewHolder.setNameTxtAddressUser(parent.getInfoUserOrder().getAddressUser());
        parentViewHolder.setNameTxtUserNameItemOrderList(parent.getInfoUserOrder().getNameUser());
        parentViewHolder.setNameTxtPhoneNumberItemOrderList(parent.getInfoUserOrder().getPhoneNumberUser());
        parentViewHolder.setNameTxtTimeOrder(parent.getInfoUserOrder().getTimeOrder());

        String linkPhotoUser = parent.getInfoUserOrder().getLinkPhotoUser();
        if(!linkPhotoUser.equals("")){
            Glide.with(mContext).load(linkPhotoUser).into(parentViewHolder.getImgAvataUserItemOrderHistory());
        }
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildOrderViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull ListOrderProduct child) {

        childViewHolder.recyclerView.setAdapter(new OrderItemAdapter(child.getOrderProductList()));
        childViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
/*
    @Override
    public ParentOrderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {

    }

    @Override
    public ChildOrderViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {

    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentOrderViewHolder parentViewHolder, int parentPosition, @NonNull ParentHistoryStore parent) {

    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildOrderViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull ListOrderProduct child) {


    }
    */
}
