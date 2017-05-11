package com.example.win81version2.orderdrink.ordered_list.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.ordered_list.presenter.OrderListPresenter;

import java.util.ArrayList;

/**
 * Created by Win 8.1 Version 2 on 5/10/2017.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListViewHolder> {

    private ArrayList<OrderList> arrOrderList;
    private Context mContext;
    private String idStore;
    private boolean flag = true;
    private OrderListPresenter presenter;

    public OrderListAdapter(ArrayList<OrderList> arrOrderList, Context mContext, String idStore) {
        this.arrOrderList = arrOrderList;
        this.mContext = mContext;
        this.idStore = idStore;
        presenter = new OrderListPresenter();
    }

    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_order_history, parent, false);
        return new OrderListViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final OrderListViewHolder holder, final int position) {
        OrderList orderList = arrOrderList.get(position);
        //set Data for item
        holder.txtProductName.setText(orderList.getProductName());
        holder.txtUserName.setText(orderList.getUserName());
        holder.txtTimeOrder.setText(orderList.getTimeOrder());
        holder.txtPhoneNumber.setText(orderList.getPhoneNumber());
        holder.txtCount.setText(String.valueOf(orderList.getCount()));
        String linkPhotoUser = "";
        linkPhotoUser = orderList.getLinkPhotoUser();
        if (!linkPhotoUser.equals("")){
            Glide.with(mContext)
                    .load(linkPhotoUser)
                    .into(holder.imgAvataUser);
        }
        if (orderList.getStatusOrder() == 0 ){
            holder.imgStatusShip.setVisibility(View.GONE);
        }
        else if (orderList.getStatusOrder() == 1 ){
            holder.imgStatusShip.setVisibility(View.VISIBLE);
            holder.imgStatusShip.setImageResource(R.drawable.imgshipped);
        }
        else  if (orderList.getStatusOrder() == 2 ){
            holder.imgStatusShip.setVisibility(View.VISIBLE);
            holder.imgStatusShip.setImageResource(R.drawable.imgcanceled);
        }
        //Set Event Click "Đã Giao"
        holder.btnShipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderList orderList = arrOrderList.get(position);
                progressShipped (position, orderList.getIdOrderList());
            }
        });
        //set Event Click Canceled
        holder.btnCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderList orderList = arrOrderList.get(position);
                progressCanceled (position, orderList.getIdOrderList());
            }
        });
        //set layoutInfo click
        holder.layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true){
                    holder.layoutShippedorCanceled.setVisibility(View.VISIBLE);
                    flag = false;
                }
                if (flag == flag){
                    holder.layoutShippedorCanceled.setVisibility(View.GONE);
                    flag = true;
                }
            }
        });
        //set Avata click
        holder.layoutAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Replace Fragment Profile User
                //put isStore = true;
                //put idUser
            }
        });
    }

    private void progressCanceled(final int position, final String idOrderList) {
        AlertDialog.Builder aler = new AlertDialog.Builder(mContext);
        aler.setMessage("Bạn có chắc hóa đơn này đã bị hủy?");
        aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.updateStatusOrder(idStore, idOrderList, 2);
                Toast.makeText(mContext, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                arrOrderList.remove(position);;
            }
        });
        aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void progressShipped(final int position, final String idOrderList) {
        AlertDialog.Builder aler = new AlertDialog.Builder(mContext);
        aler.setMessage("Bạn có chắc đã giao hóa đơn này?");
        aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.updateStatusOrder(idStore, idOrderList, 1);
                Toast.makeText(mContext, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                arrOrderList.remove(position);
            }
        });
        aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    @Override
    public int getItemCount() {
        return arrOrderList.size();
    }
}
