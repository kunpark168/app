package com.example.win81version2.orderdrink.store_list.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win81version2.orderdrink.R;

/**
 * Created by Win 8.1 Version 2 on 4/24/2017.
 */

public class StoreListViewHolder extends RecyclerView.ViewHolder {

    public TextView txtStoreName, txtSumfavorite, txtSumShipped, txtAddress, txtTimeWork;
    public ImageView imgPhotoStore;
    public CardView infoStore, displayInfo;

    public StoreListViewHolder(View itemView) {
        super(itemView);
        txtStoreName = (TextView) itemView.findViewById(R.id.txtStoreName_itemstore);
        txtSumfavorite = (TextView) itemView.findViewById(R.id.txtSumFavorite_itemstore);
        txtSumShipped = (TextView) itemView.findViewById(R.id.txtSumShipped_itemstore);
        txtAddress = (TextView) itemView.findViewById(R.id.txtAddress_itemstore);
        txtTimeWork = (TextView) itemView.findViewById(R.id.txtTimeWork_itemstore);

        imgPhotoStore = (ImageView) itemView.findViewById(R.id.imgPhotoStore);
        infoStore = (CardView) itemView.findViewById(R.id.infoStore);
        displayInfo = (CardView) itemView.findViewById(R.id.displayStore);
    }
}
