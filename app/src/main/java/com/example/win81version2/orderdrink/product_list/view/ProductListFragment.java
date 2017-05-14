package com.example.win81version2.orderdrink.product_list.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.model.Category;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.product_list.model.GroupProduct;
import com.example.win81version2.orderdrink.product_list.model.ProductListAdapter;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductListFragment extends Fragment {

    private ProductListAdapter adapter;
    private List<GroupProduct> categoryList;
    private RecyclerView recyclerProduct;
    private DatabaseReference mData;
    private String idStore;

    private ScrollView scroll;
    private TextView txtStoreName, txtAddressStore, txtTimeWorkStore;
    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        idStore = getArguments().getString(Constain.ID_STORE);
        return inflater.inflate(R.layout.fragment_list_product, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo ();
    }

    private void initInfo() {
        try {
            mData.child(Constain.STORES).child(idStore).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        Store store = dataSnapshot.getValue(Store.class);
                        if (store.getStoreName() != null){
                            txtStoreName.setText(store.getStoreName().toString());
                        }
                        if (store.getTimeWork() != null){
                            txtTimeWorkStore.setText(store.getTimeWork().toString());
                        }
                        if (store.getLocation() != null){
                            HashMap<String, Object> location =  new HashMap<String, Object>();
                            location = store.getLocation();
                            if (location.get(Constain.ADDRESS) != null){
                                txtAddressStore.setText(location.get(Constain.ADDRESS).toString());
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
    }

    private void addControls() {
        categoryList = new ArrayList<>();
        scroll = (ScrollView) getActivity().findViewById(R.id.scrollbarP);
        mData = FirebaseDatabase.getInstance().getReference();
        recyclerProduct = (RecyclerView) getActivity().findViewById(R.id.recyclerProducts);
        recyclerProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductListAdapter(getContext() , initData());
        adapter.expandAllParents();
        recyclerProduct.setAdapter(adapter);
        recyclerProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scroll.scrollTo(dx ,dy);
            }
        });
        txtStoreName = (TextView) getActivity().findViewById(R.id.txtStoreName_listproduct);
        txtAddressStore = (TextView) getActivity().findViewById(R.id.txtAddressStore_listproduct);
        txtTimeWorkStore = (TextView) getActivity().findViewById(R.id.txtTimeWork_listproduct);
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
