package com.example.win81version2.orderdrink.product_list.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.model.Category;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.product_list.model.GroupProduct;
import com.example.win81version2.orderdrink.product_list.model.ProductListAdapter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public class ProductListFragment extends Fragment {

    private ProductListAdapter adapter;
    private List<GroupProduct> categoryList;
    private RecyclerView recyclerProduct;
    private DatabaseReference mData;
    private String idStore;

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        idStore = getArguments().getString(Constain.ID_STORE);
        return inflater.inflate(R.layout.store_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
    }

    private void addControls() {
        categoryList = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference();
        recyclerProduct = (RecyclerView) getActivity().findViewById(R.id.recyclerProducts);
        recyclerProduct.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new ProductListAdapter(getContext() , initData());
        recyclerProduct.setAdapter(adapter);
    }

    private int getTypeForPosition(int position) {
        if(position > 2)
            return 1;
        return 2;
    }

    private List<GroupProduct> initData() {
        List<GroupProduct> parentObjectList = new ArrayList<>();
        getListCategory(parentObjectList);
        return parentObjectList;
    }

    private void getListCategory(final List<GroupProduct> parentObjectList) {

        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                try {
                                    final Category cate = dt.getValue(Category.class);
                                    final List<Product> childList = new ArrayList<>();
                                    //get all product ,then add List item
                                    mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(dt.getKey()).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getValue() != null) {
                                                try {
                                                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                                        Product product = dt.getValue(Product.class);
                                                        childList.add(product);
                                                    }
                                                    GroupProduct category = new GroupProduct(cate.getCategoryName() , childList);
                                                    parentObjectList.add(category);
                                                    adapter.notifyParentDataSetChanged(true);
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((ProductListAdapter) recyclerProduct.getAdapter()).onSaveInstanceState(outState);
    }
}
