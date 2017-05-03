package com.example.win81version2.orderdrink.category.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.win81version2.orderdrink.R;

import java.util.ArrayList;

/**
 * Created by Win 8.1 Version 2 on 5/2/2017.
 */

public class CategoryListAdapter extends RecyclerSwipeAdapter<CategoryListViewHolder> {

    private ArrayList<Category> arrCategory;
    private Activity activity;

    public CategoryListAdapter(ArrayList<Category> arrCategory, Activity activity) {
        this.arrCategory = arrCategory;
        this.activity = activity;
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_category, parent, false);
        return new CategoryListViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(CategoryListViewHolder viewHolder, int position) {
        Category category = arrCategory.get(position);
        int stt = Integer.parseInt(category.getIdCategory()) + 1;
        if (stt %2 == 0 ){
            viewHolder.txtSTT.setBackgroundColor(activity.getResources().getColor(R.color.stt1));
            viewHolder.txtSTTSwipe.setBackgroundColor(activity.getResources().getColor(R.color.stt1));
        }
        else {
            viewHolder.txtSTT.setBackgroundColor(activity.getResources().getColor(R.color.stt2));
            viewHolder.txtSTTSwipe.setBackgroundColor(activity.getResources().getColor(R.color.stt2));
        }
        viewHolder.txtSTT.setText(String.valueOf(stt));
        viewHolder.txtSTTSwipe.setText(String.valueOf(stt));
        viewHolder.txtCategoryName.setText(category.getCategoryName());
        viewHolder.txtSumProduct.setText(String.valueOf(category.getSumProduct()));
        viewHolder.txtTimeCreate.setText(category.getTimeCreateCategory());
    }

    @Override
    public int getItemCount() {
        return arrCategory.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }
}
