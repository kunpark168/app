package com.example.win81version2.orderdrink.product_list.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.example.win81version2.orderdrink.R;

/**
 * Created by Nhan on 5/9/2017.
 */

public class ViewHolders extends ChildViewHolder {


    ImageView img;
    TextView txtName;
    TextView txtPrice;



    public ViewHolders(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);

        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
    }

    public void setTxtNameName(String name){
        txtName.setText(name);
    }

    public void setTxtPriceName(String name){
        txtPrice.setText(name);
    }

    public ImageView getImg() {
        return img;
    }

}
