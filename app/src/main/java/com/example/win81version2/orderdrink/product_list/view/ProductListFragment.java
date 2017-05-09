package com.example.win81version2.orderdrink.product_list.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.product_list.model.ProductListAdapter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductListFragment extends Fragment {

    private ArrayList<Product> arrProduct;
    private ProductListAdapter adapter;
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
        addControls ();
        initInfor ();
    }

    private void initInfor() {
        try {
           mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   if (dataSnapshot.getValue() != null){
                       try {
                           for (DataSnapshot dt : dataSnapshot.getChildren()){
                               mData.child(Constain.STORES).child(idStore).child(Constain.PRODUCTS).child(dt.getKey()).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       if (dataSnapshot.getValue() != null){
                                           try {
                                               for (DataSnapshot dt : dataSnapshot.getChildren()){
                                                   Product product = dt.getValue(Product.class);
                                                   arrProduct.add(product);
                                                   adapter.notifyDataSetChanged();
                                               }
                                           }
                                           catch (Exception ex){
                                               ex.printStackTrace();
                                           }
                                       }
                                   }
                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {
                                   }
                               });
                           }
                       }catch (Exception ex){
                           ex.printStackTrace();
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
        arrProduct = new ArrayList<>();
        recyclerProduct = (RecyclerView) getActivity().findViewById(R.id.recyclerProducts);
        adapter = new ProductListAdapter(getActivity(), arrProduct);
        mData = FirebaseDatabase.getInstance().getReference();
    }
}
