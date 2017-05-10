package com.example.win81version2.orderdrink.product_list.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.model.CategoryListViewHolder;
import com.example.win81version2.orderdrink.product.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhan on 5/8/2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {


    private ArrayList<Product> arrProducts;
    private Context mContext;
    private String linkPhotoProduct = "";

    public ProductListAdapter(ArrayList<Product> arrProducts, Context mContext) {
        this.arrProducts = arrProducts;
        this.mContext = mContext;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        Product product = arrProducts.get(position);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(String.valueOf(product.getPrice()));
        linkPhotoProduct = product.getLinkPhotoProduct();
        if (!linkPhotoProduct.equals(" ")){
            Glide.with(mContext)
                    .load(linkPhotoProduct)
                    .fitCenter()
                    .into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return arrProducts.size();
    }
}

