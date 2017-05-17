package com.example.win81version2.orderdrink.history_order_user.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.example.win81version2.orderdrink.R;

/**
 * Created by Nhan on 5/16/2017.
 */

public class ChildOrderUserViewHolder extends ChildViewHolder {

    public RecyclerView recyclerView;

    public ChildOrderUserViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewFlagProductShopOrder);
    }

}
