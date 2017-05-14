package com.example.win81version2.orderdrink.my_cart.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.win81version2.orderdrink.R;

/**
 * Created by Win 8.1 Version 2 on 5/12/2017.
 */

public class MyCartViewHolder extends RecyclerView.ViewHolder {
    public TextView txtProductName, txtPrice, txtCountProduct, txtDeleteProduct;
    public ImageView imgStatusOrder;
    public SwipeLayout swipeLayout;
    public MyCartViewHolder(View itemView) {
        super(itemView);
        txtDeleteProduct = (TextView) itemView.findViewById(R.id.txtDeleteProduct);
        txtCountProduct = (TextView) itemView.findViewById(R.id.txtCountProduct_itemmyorder);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPrice_itemmyorder);
        txtProductName = (TextView) itemView.findViewById(R.id.txtProductName_itemmyorder);
        imgStatusOrder = (ImageView) itemView.findViewById(R.id.imgStatusOrder);

        swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_myorder);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.layout_left_myorder));
        swipeLayout.setRightSwipeEnabled(false);
        swipeLayout.setLeftSwipeEnabled(true);
    }
}
