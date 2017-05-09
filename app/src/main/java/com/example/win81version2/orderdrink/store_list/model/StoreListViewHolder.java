package com.example.win81version2.orderdrink.store_list.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win81version2.orderdrink.R;

/**
 * Created by Win 8.1 Version 2 on 4/24/2017.
 */

public class StoreListViewHolder extends RecyclerView.ViewHolder {

    public TextView txtStoreName, txtSumfavorite, txtSumShipped, txtAddress, txtTimeWork, txtDistance;
    public ImageView imgHeart, imgPhotoStore;
    public LinearLayout linearLayout;
    public CardView cardView;
    public Button btnCallnow, btnViewOrder;

    public StoreListViewHolder(View itemView) {
        super(itemView);
        txtStoreName = (TextView) itemView.findViewById(R.id.txtStoreName_itemstore);
        txtSumfavorite = (TextView) itemView.findViewById(R.id.txtHeart);
        txtSumShipped = (TextView) itemView.findViewById(R.id.txtSumShipped_itemstore);
        txtAddress = (TextView) itemView.findViewById(R.id.txtAddress_itemstore);
        txtTimeWork = (TextView) itemView.findViewById(R.id.txtTimeWork_itemstore);
        txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutInfo);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        btnCallnow = (Button) itemView.findViewById(R.id.btnCallNow);
        btnViewOrder = (Button) itemView.findViewById(R.id.btnViewOrder);

        imgHeart = (ImageView) itemView.findViewById(R.id.imgHeart);
        imgPhotoStore = (ImageView) itemView.findViewById(R.id.imgPhotoStore_itemStore);
    }
}
