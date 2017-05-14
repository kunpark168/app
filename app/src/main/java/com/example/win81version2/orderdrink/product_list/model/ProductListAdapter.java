package com.example.win81version2.orderdrink.product_list.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.my_cart.view.DisplayProduct;
import com.example.win81version2.orderdrink.utility.Constain;

import java.util.List;

/**
 * Created by Nhan on 5/8/2017.
 */

public class ProductListAdapter extends ExpandableRecyclerAdapter<GroupProduct , Product ,GroupProductViewHolder , ViewHolders> {

    private static final int NOI_BAT = 1;
    private static final int BINH_THUONG = 2;
    private Context mContext;
    private LayoutInflater inflater;
    private List<GroupProduct> parentList;


    public ProductListAdapter(Context context , @NonNull List<GroupProduct> parentList) {
        super(parentList);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.parentList = parentList;
    }

    @Override
    public GroupProductViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.product_section_header, parentViewGroup, false);
        return new GroupProductViewHolder(recipeView);
    }


    @Override
    public ViewHolders onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.product_cardview, childViewGroup, false);
        return new ViewHolders(recipeView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull GroupProductViewHolder parentViewHolder, int parentPosition, @NonNull GroupProduct parent) {

        parentViewHolder.setTxtCategoryProductName(parent.getTitle());
    }

    @Override
    public void onBindChildViewHolder(@NonNull ViewHolders childViewHolder, final int parentPosition, final int childPosition, @NonNull Product child) {

        final Product product = child;
        childViewHolder.setTxtPriceName(Math.round(child.getPrice()) + " VNĐ");
        childViewHolder.setTxtNameName(child.getProductName());
        childViewHolder.btnGetit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupProduct groupProduct = parentList.get(parentPosition);
                displayProduct(product, groupProduct.getTitle());
            }
        });
        String linkPhoto = child.getLinkPhotoProduct();
        if(!linkPhoto.equals("")){
            Glide.with(mContext).load(linkPhoto).into(childViewHolder.getImg());
        }
    }

    private void displayProduct(Product product, String categoryName){
        Intent intent = new Intent(mContext, DisplayProduct.class);
        intent.putExtra(Constain.PRODUCTS, product);
        intent.putExtra(Constain.CATEGORY_NAME, categoryName);
        mContext.startActivity(intent);
    }

}


