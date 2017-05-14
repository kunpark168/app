package com.example.win81version2.orderdrink.ordered_list_store.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.ordered_list_store.model.OrderList;
import com.example.win81version2.orderdrink.ordered_list_store.model.OrderListAdapter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderedListFragment extends Fragment {

    private ArrayList<OrderList> arrOrderList;
    private OrderListAdapter adapter;
    private String idStore;
    private RecyclerView recyclerOrderList;
    private DatabaseReference mData;

    public OrderedListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordered__history_, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo();
    }

    private void initInfo() {
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.ORDER_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            mData.child(Constain.STORES).child(idStore).child(dt.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        try {
                                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                                OrderList order = dt.getValue(OrderList.class);
                                                arrOrderList.add(order);
                                                adapter.notifyDataSetChanged();
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

    private void addControls() {
        arrOrderList = new ArrayList<>();
        idStore = getActivity().getIntent().getStringExtra(Constain.ID_STORE);
        adapter = new OrderListAdapter(arrOrderList, getContext(), idStore);
        recyclerOrderList = (RecyclerView) getActivity().findViewById(R.id.recyclerOrderList);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getActivity());
        recyclerOrderList.setAdapter(adapter);
        recyclerOrderList.setLayoutManager(mManager);
    }
}
