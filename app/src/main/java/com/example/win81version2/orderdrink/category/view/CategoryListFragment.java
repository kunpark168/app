package com.example.win81version2.orderdrink.category.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.model.Category;
import com.example.win81version2.orderdrink.category.model.CategoryListAdapter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment {


    private RecyclerView recyclerCategory;
    private ArrayList<Category> arrCategory;
    private CategoryListAdapter adapter;
    private LinearLayoutManager mManager;
    private DatabaseReference mData;
    private String idStore;
    private ImageView imgCreate;
    public CategoryListFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addEvents() {
        imgCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateCategory.class);
                intent.putExtra(Constain.ID_STORE, idStore);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        Intent intent = getActivity().getIntent();
        idStore = intent.getStringExtra(Constain.ID_STORE);
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.PRODUCTS).addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        arrCategory.clear();
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            try {
                                Category category = dt.getValue(Category.class);
                                arrCategory.add(category);
                                adapter.notifyDataSetChanged();
                            } catch (Exception ex) {

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            recyclerCategory.setLayoutManager(mManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addControls() {
        imgCreate = (ImageView) getActivity().findViewById(R.id.imgCreateCategoryFrag);
        mData = FirebaseDatabase.getInstance().getReference();
        //List Category
        mManager = new LinearLayoutManager(getActivity());
        recyclerCategory = (RecyclerView) getActivity().findViewById(R.id.recyclerCategory);
        arrCategory = new ArrayList<>();
        adapter = new CategoryListAdapter(arrCategory, getActivity());
        recyclerCategory.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo();
        addEvents();
    }
}