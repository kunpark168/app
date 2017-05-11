package com.example.win81version2.orderdrink.profile_user.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win81version2.orderdrink.R;

public class ProfileUser_Fragment extends Fragment {
    public ProfileUser_Fragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user_, container, false);
        return view;
    }
}
