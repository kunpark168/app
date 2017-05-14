package com.example.win81version2.orderdrink.my_cart.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.my_cart.model.MyCart;
import com.example.win81version2.orderdrink.my_cart.model.MyCartAdapter;
import com.example.win81version2.orderdrink.oop.BaseFragment;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyCartFragment extends BaseFragment {

    private ArrayList<MyCart> arrMyCart;
    private String idUser;
    private MyCartAdapter adapter;
    private RecyclerView recyclerMyOrder;
    private DatabaseReference mData;
    private TextView txtSumMoney;
    private Button btnPay;
    private float sumMoney = 0;

    public MyCartFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cart, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo();
        addEvents ();
    }

    private void addEvents() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initInfo() {
        try {
            mData.child(Constain.USERS).child(idUser).child(Constain.MY_CART).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arrMyCart.clear();
                    sumMoney = 0;
                      if (dataSnapshot.getValue() != null){
                          try {
                              for (DataSnapshot dt : dataSnapshot.getChildren()){
                                  MyCart myCart = dt.getValue(MyCart.class);
                                  sumMoney += myCart.getPrice();
                                  arrMyCart.add(myCart);
                                  adapter.notifyDataSetChanged();
                              }
                              txtSumMoney.setText(Math.round(sumMoney) + " VNĐ");
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
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addControls() {
        arrMyCart = new ArrayList<>();
        idUser = getActivity().getIntent().getStringExtra(Constain.ID_USER);
        mData = FirebaseDatabase.getInstance().getReference();
        adapter = new MyCartAdapter(arrMyCart, getContext(), idUser);
        recyclerMyOrder = (RecyclerView) getActivity().findViewById(R.id.recyclerViewMyOrder);
        recyclerMyOrder.setAdapter(adapter);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getActivity());
        recyclerMyOrder.setLayoutManager(mManager);
        txtSumMoney = (TextView) getActivity().findViewById(R.id.txtSumMoney);
        btnPay = (Button) getActivity().findViewById(R.id.btnPay);
    }
}
