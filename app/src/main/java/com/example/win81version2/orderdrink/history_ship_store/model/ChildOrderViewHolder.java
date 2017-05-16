package com.example.win81version2.orderdrink.history_ship_store.model;

import android.sax.RootElement;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.example.win81version2.orderdrink.R;

/**
 * Created by Win 8.1 Version 2 on 5/15/2017.
 */

public class ChildOrderViewHolder extends ChildViewHolder {

    public RecyclerView recyclerView;

    public ChildOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewFlagProduct);
    }
}
