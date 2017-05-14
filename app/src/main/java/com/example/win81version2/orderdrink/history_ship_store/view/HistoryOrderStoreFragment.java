package com.example.win81version2.orderdrink.history_ship_store.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.oop.BaseFragment;

public class HistoryOrderStoreFragment extends BaseFragment {


    public HistoryOrderStoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordered_history_store, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo();
    }

    private void initInfo() {

    }

    private void addControls() {
    }
}
