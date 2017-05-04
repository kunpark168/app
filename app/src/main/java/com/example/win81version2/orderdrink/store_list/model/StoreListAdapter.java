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
import com.example.win81version2.orderdrink.store_list.presenter.StoreListPresenter;
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
    private String sumShipped;
    private long sumFavorite;
    private Context context;
    private String idStore;
    private String idUser;
    private String emailUser;
    private DatabaseReference mData;
    private String linkPhotoStore, timeWork = "";
    private Store_List_Fragment store_list_fragment;
    private boolean flagVisibility = false;
    private StoreListPresenter presenter;

    public StoreListAdapter(ArrayList<Store> arrStore, Store_List_Fragment store_list_fragment, String idUser) {
        this.arrStore = arrStore;
        this.store_list_fragment = store_list_fragment;
        this.idUser = idUser;
        presenter = new StoreListPresenter();
        mData = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public StoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, null);
        return new StoreListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StoreListViewHolder holder, final int position) {
        final Store store = arrStore.get(position);
        idStore = store.getIdStore();
        sumShipped = String.valueOf(store.getSumShipped());
        holder.txtStoreName.setText(store.getStoreName());
        holder.txtSumShipped.setText(sumShipped);
        //get LinkPhotoStore and Opent with Glide
        linkPhotoStore = store.getLinkPhotoStore();
        if (!linkPhotoStore.equals("")) {
            Glide.with(store_list_fragment.getActivity())
                    .load(linkPhotoStore)
                    .fitCenter()
                    .into(holder.imgPhotoStore);
        }
        try {
            //get Address Store
            mData.child(Constain.STORES).child(idStore).child(Constain.LOCATION).child(Constain.ADDRESS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    holder.txtAddress.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //get Timework
            mData.child(Constain.STORES).child(idStore).child(Constain.TIME_WORK).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    timeWork = dataSnapshot.getValue().toString();
                    holder.txtTimeWork.setText(timeWork);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //get sumFavorite
            mData.child(Constain.STORES).child(idStore).child(Constain.FAVORITE_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        sumFavorite = dataSnapshot.getChildrenCount();
                        holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            if (idUser.equals(dt.getKey().toString())) {
                                holder.imgHeart.setImageResource(R.drawable.heart);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //set Item click
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagVisibility == true){
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    flagVisibility = false;
                }
                else {
                    holder.linearLayout.setVisibility(View.GONE);
                    flagVisibility = true;
                }
            }
        });
        //heart
        holder.imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Store store1 = arrStore.get(position);
                    final String idStore1 = store1.getIdStore();
                    mData.child(Constain.STORES).child(idStore1).child(Constain.FAVORITE_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                boolean flag = true;
                                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                    if (idUser.equals(dt.getKey())) {
                                        flag = false;
                                    }
                                }
                                    if (!flag){
                                        //remove and change img
                                        presenter.removeHeart(idStore1, idUser);
                                        sumFavorite = dataSnapshot.getChildrenCount() - 1;
                                        holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                                        holder.imgHeart.setImageResource(R.drawable.non_heart);
                                    }
                                    else {
                                        //add and change img
                                        presenter.addHeart(idStore1, idUser, emailUser);
                                        holder.imgHeart.setImageResource(R.drawable.heart);
                                        sumFavorite = dataSnapshot.getChildrenCount() + 1;
                                        holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                                    }
                            }
                            else {
                                presenter.addHeart(idStore1, idUser, emailUser);
                                holder.imgHeart.setImageResource(R.drawable.heart);
                                holder.txtSumfavorite.setText("1");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                catch (Exception ex){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrStore.size();
    }

    //set UserName

    public void setEmailUser (String emailUser) {
        this.emailUser = emailUser;
    }
}
