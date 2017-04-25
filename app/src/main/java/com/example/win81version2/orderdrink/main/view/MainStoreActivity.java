package com.example.win81version2.orderdrink.main.view;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.notification_store.view.Notification_Store_Fragment;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.ordered_history.view.Ordered_History_Fragment;
import com.example.win81version2.orderdrink.product_list.view.Product_List_Fragment;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.profile_store.view.Profile_Store_Fragment;
import com.example.win81version2.orderdrink.profile_user.view.ProfileUser_Fragment;
import com.example.win81version2.orderdrink.store_list.view.Store_List_Fragment;
import com.example.win81version2.orderdrink.utility.Constain;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainStoreActivity extends BaseActivity implements AHBottomNavigation.OnTabSelectedListener {

    private AHBottomNavigation ahBottomNavigation;
    private ImageView imgPhotoStore;
    private TextView  txtStoreName, txtSumShipped;
    private String idStore, linkPhotoStore = "";
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main_store);
        addControls();
        innitInfo();
    }

    private void innitInfo() {
        Intent intent = getIntent();
        idStore = intent.getStringExtra(Constain.ID_STORE);
        try {
            mData.child(Constain.STORES).child(idStore).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        try {
                            Store store = dataSnapshot.getValue(Store.class);
                            txtStoreName.setText(store.getStoreName().toString());
                            txtSumShipped.setText(store.getSumShipped());
                            linkPhotoStore = store.getLinkPhotoStore();
                            if (!linkPhotoStore.equals("")) {
                                Glide.with(MainStoreActivity.this)
                                        .load(linkPhotoStore)
                                        .fitCenter()
                                        .into(imgPhotoStore);
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_store);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_store);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Navigation Bottom
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.navigation);
        initItemNavigation();
        mData = FirebaseDatabase.getInstance().getReference();
        txtStoreName = (TextView) findViewById(R.id.txtstorename_mainuser);
        txtSumShipped = (TextView) findViewById(R.id.txtsumshipped_mainstore);
        imgPhotoStore = (ImageView) findViewById(R.id.imgPhotoStore_mainstore);


    }

    private void initItemNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.ic_store, R.color.ahbottom);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.icon_shopping, R.color.ahbottom);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.icon_profile, R.color.ahbottom);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.setOnTabSelectedListener(this);
        ahBottomNavigation.setCurrentItem(0);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
            Product_List_Fragment product_list_fragment = new Product_List_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, product_list_fragment).commit();
        } else if (position == 1) {
            Notification_Store_Fragment notification_store_fragment = new Notification_Store_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, notification_store_fragment).commit();
        } else if (position == 2) {
            Profile_Store_Fragment profile_store_fragment = new Profile_Store_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, profile_store_fragment).commit();

        }
    }
}
