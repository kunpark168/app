package com.example.win81version2.orderdrink.product_list.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.product.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhan on 5/8/2017.
 */

public class ProductListAdapter extends ExpandableRecyclerAdapter<GroupProduct , Product ,GroupProductViewHolder , ViewHolders> {

    private Context mContext;
    private LayoutInflater inflater;

    public ProductListAdapter(Context context , @NonNull List<GroupProduct> parentList) {
        super(parentList);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public GroupProductViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.category_product, parentViewGroup, false);
        return new GroupProductViewHolder(recipeView);
    }


    @Override
    public ViewHolders onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.cardview_product, childViewGroup, false);
        return new ViewHolders(recipeView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull GroupProductViewHolder parentViewHolder, int parentPosition, @NonNull GroupProduct parent) {

        parentViewHolder.setTxtCategoryProductName(parent.getTitle());
    }

    @Override
    public void onBindChildViewHolder(@NonNull ViewHolders childViewHolder, int parentPosition, int childPosition, @NonNull Product child) {

        childViewHolder.setTxtPriceName(child.getProductName());
        childViewHolder.setTxtNameName(String.valueOf(child.getPrice()));

        String linkPhoto = child.getLinkPhotoProduct();
        if(linkPhoto.equals("")){
            Glide.with(mContext).load(linkPhoto).into(childViewHolder.getImg());
        }
    }

}


