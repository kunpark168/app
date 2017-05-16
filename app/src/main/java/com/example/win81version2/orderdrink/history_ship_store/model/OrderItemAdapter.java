package com.example.win81version2.orderdrink.history_ship_store.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.model.Category;
import com.example.win81version2.orderdrink.product.model.OrderProduct;

import java.util.ArrayList;

/**
 * Created by Win 8.1 Version 2 on 5/15/2017.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolderOrder> {

    private ArrayList<OrderProduct> orderProducts;

    public OrderItemAdapter(ArrayList<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public ViewHolderOrder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_flag_product, parent, false);
        return new ViewHolderOrder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolderOrder holder, int position) {
        holder.txtNameOrderItem.setText(orderProducts.get(position).getProductName());
        holder.txtCountOrderItem.setText(String.valueOf(orderProducts.get(position).getCount()));
        holder.txtPriceOrderItem.setText(String.valueOf(orderProducts.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }


    public class ViewHolderOrder extends RecyclerView.ViewHolder {

        TextView txtNameOrderItem;
        TextView txtCountOrderItem;
        TextView txtPriceOrderItem;

        public ViewHolderOrder(View itemView) {
            super(itemView);

            txtNameOrderItem = (TextView) itemView.findViewById(R.id.txtNameOrderItem);
            txtCountOrderItem = (TextView) itemView.findViewById(R.id.txtCountOrderItem);
            txtPriceOrderItem = (TextView) itemView.findViewById(R.id.txtPriceOrderItem);
        }
    }
}
