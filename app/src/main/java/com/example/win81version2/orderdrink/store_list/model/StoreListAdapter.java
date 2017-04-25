package com.example.win81version2.orderdrink.store_list.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.main.view.MainUserActivity;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.store_list.view.Store_List_Fragment;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Win 8.1 Version 2 on 4/24/2017.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListViewHolder> {

    private ArrayList<Store> arrStore;
    private String sumFavorite, sumShipped;
    private Context context;
    private String idStore;
    private DatabaseReference mData;
    private String linkPhotoStore, timeWork = "", from = "", to = "";
    private Store_List_Fragment store_list_fragment;
    private boolean isVisibility = true;

    public StoreListAdapter(ArrayList<Store> arrStore, Store_List_Fragment store_list_fragment) {
        this.arrStore = arrStore;
        this.store_list_fragment = store_list_fragment;
        mData = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public StoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, null);
        return new StoreListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StoreListViewHolder holder, int position) {
        Store store = arrStore.get(position);
        idStore = store.getIdStore();
        sumFavorite = String.valueOf(store.getSumFavorite());
        sumShipped = String.valueOf(store.getSumShipped());
        if (sumFavorite.length() == 1){
            sumFavorite = "0" + sumFavorite;
        }
        if (sumShipped.length() == 1){
            sumShipped = "0" + sumShipped;
        }
        holder.txtStoreName.setText(store.getStoreName());
        holder.txtSumfavorite.setText(sumFavorite);
        holder.txtSumShipped.setText(sumShipped);
        //set Adress for ViewHolder
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.LOCATION).child(Constain.ADDRESS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    holder.txtAddress.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //get LinkPhotoStore and Opent with Glide
        linkPhotoStore = store.getLinkPhotoStore();
        if (!linkPhotoStore.equals("")) {
            Glide.with(store_list_fragment.getActivity())
                    .load(linkPhotoStore)
                    .fitCenter()
                    .into(holder.imgPhotoStore);
        }
        //Get Time Work from "From" and "To"
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.TIME_WORK).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    timeWork = dataSnapshot.getValue().toString();
                    holder.txtTimeWork.setText(timeWork);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //Display InfoStore Event
        holder.displayInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisibility == true){
                    isVisibility = false;
                    holder.infoStore.setVisibility(View.VISIBLE);
                }
                else if (isVisibility == false){
                    isVisibility = true;
                    holder.infoStore.setVisibility(View.GONE);
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return arrStore.size();
    }
}
