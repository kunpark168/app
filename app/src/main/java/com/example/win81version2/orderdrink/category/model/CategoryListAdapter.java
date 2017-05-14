package com.example.win81version2.orderdrink.category.model;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.presenter.CategoryPresenter;
import com.example.win81version2.orderdrink.category.view.CreateCategory;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Win 8.1 Version 2 on 5/2/2017.
 */

public class CategoryListAdapter extends RecyclerSwipeAdapter<CategoryListViewHolder> {

    private ArrayList<Category> arrCategory;
    private Activity activity;
    private String idStore;
    private CategoryPresenter presenter;

    public CategoryListAdapter(ArrayList<Category> arrCategory, Activity activity, String idStore) {
        this.arrCategory = arrCategory;
        this.activity = activity;
        this.idStore = idStore;
        presenter = new CategoryPresenter();
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_category, parent, false);
        return new CategoryListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder viewHolder, final int position) {
        Category category = arrCategory.get(position);
        int stt = Integer.parseInt(category.getIdCategory()) + 1;
        if (stt % 2 == 0) {
            viewHolder.txtSTT.setBackgroundColor(activity.getResources().getColor(R.color.stt1));
            viewHolder.txtSTTSwipe.setBackgroundColor(activity.getResources().getColor(R.color.stt1));
        } else {
            viewHolder.txtSTT.setBackgroundColor(activity.getResources().getColor(R.color.stt2));
            viewHolder.txtSTTSwipe.setBackgroundColor(activity.getResources().getColor(R.color.stt2));
        }
        viewHolder.txtSTT.setText(String.valueOf(stt));
        viewHolder.txtSTTSwipe.setText(String.valueOf(stt));
        viewHolder.txtCategoryName.setText(category.getCategoryName());
        viewHolder.txtSumProduct.setText(String.valueOf(category.getSumProduct()));
        viewHolder.txtTimeCreate.setText(category.getTimeCreateCategory());
        viewHolder.txtDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Category category1 = arrCategory.get(position);
                AlertDialog.Builder aler = new AlertDialog.Builder(activity);
                aler.setMessage("Xóa Category bao gồm xóa toàn bộ sản phẩm trong category ?");
                aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteCategory(idStore, category1.getIdCategory());
                        Toast.makeText(activity, "Bạn đã xóa Category", Toast.LENGTH_SHORT).show();

                    }
                });
                aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                aler.show();
            }
        });
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
