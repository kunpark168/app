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
import com.example.win81version2.orderdrink.product.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhan on 5/8/2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<Product> listProducts;

    private Context mContext;

    public ProductListAdapter(Context context, ArrayList<Product> listProducts) {
        this.listProducts = listProducts;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_product, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = listProducts.get(position);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(String.valueOf(product.getPrice()));
        String linkPhotoStore = product.getLinkPhotoProduct();
        if (!linkPhotoStore.equals("")) {
            Glide.with(mContext)
                    .load(linkPhotoStore)
                    .fitCenter()
                    .into(holder.img);
        }

    }

    private void setAnimation(FrameLayout container, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        container.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return (null != listProducts ? listProducts.size() : 0 );
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtName;
        TextView txtPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
        }
    }
}

