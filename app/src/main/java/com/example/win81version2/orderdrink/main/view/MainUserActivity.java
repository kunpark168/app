package com.example.win81version2.orderdrink.main.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.main.presenter.UserPresenter;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.profile_user.model.User;
import com.example.win81version2.orderdrink.search_user.model.SearchStore;
import com.example.win81version2.orderdrink.search_user.view.SearchStoreActivity;
import com.example.win81version2.orderdrink.store_list.view.Store_List_Fragment;
import com.example.win81version2.orderdrink.utility.Constain;
import com.example.win81version2.orderdrink.utility.GPSTracker;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainUserActivity extends BaseActivity implements View.OnClickListener, Serializable {

    private ImageView imgAvata, imgSearch;
    private TextView txtUserName, txtSumOrdered, txtSearch;
    private DatabaseReference mData;
    private String idUser, userName, linkPhotoUser, sumOrdered, addressUser = "";
    private Button btnLogout;
    private GPSTracker gps;
    private UserPresenter presenter;
    private HashMap<String, Object> location;
    private double lo = 0, la = 0;
    private LinearLayout layoutSearch;
    private Store_List_Fragment storeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main_user);
        addControls();
        checkGPS();
        initInfo();
        addEvvents();
    }

    private void checkGPS() {
        if (gps.canGetLocation()) {
            lo = gps.getLongitude();
            la = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }
    }

    private void addEvvents() {
        btnLogout.setOnClickListener(this);
        layoutSearch.setOnClickListener(this);
    }

    private void initInfo() {
        Intent intent = getIntent();
        idUser = intent.getStringExtra(Constain.ID_USER);
        mData.child(Constain.USERS).child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    try {
                        User user = dataSnapshot.getValue(User.class);
                        userName = user.getUserName();
                        if (user.getLocation() != null) {
                            HashMap<String, Object> flag = new HashMap<>();
                            flag = user.getLocation();
                            addressUser = String.valueOf(flag.get(Constain.ADDRESS));
                            if (lo != 0 && la != 0) {
                                location.put(Constain.LO, lo);
                                location.put(Constain.LA, la);
                                location.put(Constain.ADDRESS, addressUser);
                                presenter.updateLocation(idUser, location);
                            }
                        }
                        linkPhotoUser = user.getLinkPhotoUser();
                        sumOrdered = user.getSumOrdered() + " Ordered";
                        if (!linkPhotoUser.equals("")) {
                            Glide.with(MainUserActivity.this)
                                    .load(linkPhotoUser)
                                    .fitCenter()
                                    .into(imgAvata);
                        }
                        txtUserName.setText(userName);
                        txtSumOrdered.setText(sumOrdered);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    showToast("Lỗi không load được User!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //View
        imgAvata = (ImageView) findViewById(R.id.imgPhotoUser);
        imgSearch = (ImageView) findViewById(R.id.imgSearchLabel);
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        txtUserName = (TextView) findViewById(R.id.txtusername_mainuser);
        txtSumOrdered = (TextView) findViewById(R.id.txtSumOreders_mainuser);
        btnLogout = (Button) findViewById(R.id.btn_logout_user);
        layoutSearch = (LinearLayout) findViewById(R.id.layoutSearch);
        location = new HashMap<>();
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new UserPresenter();
        gps = new GPSTracker(this);
        storeListFragment = new Store_List_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, storeListFragment).commit();


    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btn_logout_user) {
            logOut();
        }
        if (view == R.id.layoutSearch) {
            txtSearch.setEnabled(true);
            imgSearch.setEnabled(true);
            moveToSearchAcitvity();
        }
    }

    private void moveToSearchAcitvity() {
        ArrayList<Store> arrStore = storeListFragment.getArrStore();
        ArrayList<SearchStore> arrSearch = new ArrayList<>();
        for (int i = 0; i < arrStore.size() - 1; i++) {
            SearchStore search = new SearchStore(arrStore.get(i).getLinkPhotoStore(), arrStore.get(i).getStoreName(), arrStore.get(i).getSumProduct());
            arrSearch.add(search);
        }
        Intent intent = new Intent(this,SearchStoreActivity.class);
        intent.putExtra("search" , arrSearch);
        startActivity(intent);
    }

    private void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainUserActivity.this);
        alert.setMessage("Do you want to logout?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainUserActivity.this, MainActivity.class));
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constain.REQUEST_CODE_GPS) {
            gps.getLocation();
            lo = gps.getLongitude();
            la = gps.getLatitude();
            if (lo != 0 && la != 0) {
                location.put(Constain.LO, lo);
                location.put(Constain.LA, la);
                location.put(Constain.ADDRESS, addressUser);
                presenter.updateLocation(idUser, location);
            }
        }
    }

}
