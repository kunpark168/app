package com.example.win81version2.orderdrink.ordered_history.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win81version2.orderdrink.R;

/**
 * Created by Win 8.1 Version 2 on 5/10/2017.
 */

public class OrderListViewHolder extends RecyclerView.ViewHolder {
    public TextView txtUserName, txtPhoneNumber, txtProductName, txtCount, txtTimeOrder;
    public Button btnShipped, btnCanceled;
    public ImageView imgAvataUser;
    public LinearLayout layoutShippedorCanceled;
    public OrderListViewHolder(View itemView) {
        super(itemView);
        txtUserName = (TextView) itemView.findViewById(R.id.txtUserName_itemOrderList);
        txtPhoneNumber = (TextView) itemView.findViewById(R.id.txtPhoneNumber_itemOrderList);
        txtCount = (TextView) itemView.findViewById(R.id.txtCount);
        txtProductName = (TextView) itemView.findViewById(R.id.txtProductName_itemOrderList);
        txtTimeOrder = (TextView) itemView.findViewById(R.id.txtTimeOrder);
        btnShipped = (Button) itemView.findViewById(R.id.btnShipped);
        btnCanceled = (Button) itemView.findViewById(R.id.btnCanceled);
        imgAvataUser = (ImageView) itemView.findViewById(R.id.imgAvataUser_itemOrderList);
        layoutShippedorCanceled = (LinearLayout) itemView.findViewById(R.id.layoutShippedorCanceled);

    }
}
