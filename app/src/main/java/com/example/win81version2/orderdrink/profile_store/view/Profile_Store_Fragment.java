package com.example.win81version2.orderdrink.profile_store.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profile_Store_Fragment extends Fragment {

    private String idStore, storeName, phoneNumber, address, email;
    private DatabaseReference mData;
    public Profile_Store_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile__store_, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls ();
        initInfo ();
    }

    private void initInfo() {
        try {
            mData.child(Constain.STORES).child(idStore).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                      try {
                          Store store = dataSnapshot.getValue(Store.class);
                          if (store.getStoreName() != null){
                              storeName = store.getStoreName();
                          }
                          if (store.getPhoneNumber() != null){
                              phoneNumber = store.getPhoneNumber();
                          }
                          if (store.getLocation() != null){
                              HashMap<String, Object> location = new HashMap<String, Object>();
                              location = store.getLocation();
                              address = (String) location.get(Constain.ADDRESS);
                          }
                          if (store.getEmail() != null){
                              email = store.getEmail();
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
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addControls() {
        idStore = getActivity().getIntent().getStringExtra(Constain.ID_STORE);
        mData = FirebaseDatabase.getInstance().getReference();
    }

    public String getEmail() {
        return email;
    }
}
