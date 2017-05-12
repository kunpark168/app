package com.example.win81version2.orderdrink.main.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.main.presenter.UserPresenter;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.ordered_list.view.OrderedListFragment;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.product_list.view.ProductListFragment;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.profile_store.view.Profile_Store_Fragment;
import com.example.win81version2.orderdrink.profile_user.model.User;
import com.example.win81version2.orderdrink.profile_user.view.ProfileUserActivity;
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

public class MainUser2Activity extends BaseActivity implements View.OnClickListener, Serializable , AHBottomNavigation.OnTabSelectedListener{

    private ImageView imgAvata, imgSearch;
    private AHBottomNavigation ahBottomNavigation;
    private TextView txtUserName, txtSumOrdered, txtSearch;
    private DatabaseReference mData;
    private String idUser, idStore, userName, linkPhotoUser, sumOrdered, addressUser = "";
    private Button btnLogout;
    private GPSTracker gps;
    private UserPresenter presenter;
    private HashMap<String, Object> location;
    private ArrayList<Product> arrAllProduct;
    private double lo = 0, la = 0;
    private LinearLayout layoutSearch, layoutHome, layoutMyfavorite, layoutOrderHistory, layoutRate, layoutShare, layoutMyProfile;
    private Store_List_Fragment storeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main_user2);
        addControls();
        checkGPS();
        initInfo();
        addEvvents();
    }

    private void checkGPS() {
        if (gps.canGetLocation()) {
            gps.getLocation();
            lo = gps.getLongitude();
            la = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }
    }

    private void addEvvents() {
        btnLogout.setOnClickListener(this);
        layoutSearch.setOnClickListener(this);
        layoutMyProfile.setOnClickListener(this);
    }

    private void initInfo() {
        try {
            mData.child(Constain.USERS).child(idUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            User user = dataSnapshot.getValue(User.class);
                            userName = user.getUserName();
                            addressUser = "";
                            if (user.getLocation() != null) {
                                HashMap<String, Object> flag = new HashMap<>();
                                    flag = user.getLocation();
                                    addressUser = String.valueOf(flag.get(Constain.ADDRESS));
                            }
                            location.put(Constain.LO, lo);
                            location.put(Constain.LA, la);
                            location.put(Constain.ADDRESS, addressUser);
                            presenter.updateLocation(idUser, location);
                            linkPhotoUser = user.getLinkPhotoUser();
                            sumOrdered = user.getSumOrdered() + " Ordered";
                            if (!linkPhotoUser.equals("")) {
                                Glide.with(MainUser2Activity.this)
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
        catch (Exception ex){

        }

    }

    private void addControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Intent intent = getIntent();
        idUser = intent.getStringExtra(Constain.ID_USER);
        idStore = intent.getStringExtra(Constain.ID_STORE);
        //Navigation Bottom
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.navigation_User);
        initItemNavigation();

        //View
        imgAvata = (ImageView) findViewById(R.id.imgPhotoUser2);
        imgSearch = (ImageView) findViewById(R.id.imgSearchLabel2);
        txtSearch = (TextView) findViewById(R.id.txtSearch2);
        txtUserName = (TextView) findViewById(R.id.txtusername_mainuser2);
        txtSumOrdered = (TextView) findViewById(R.id.txtSumOreders_mainuser2);
        btnLogout = (Button) findViewById(R.id.btn_logout_user2);
        layoutSearch = (LinearLayout) findViewById(R.id.layoutSearch2);
        layoutHome = (LinearLayout) findViewById(R.id.navigation_homeUser2);
        layoutMyProfile = (LinearLayout) findViewById(R.id.navigation_myprofile2);
        layoutMyfavorite = (LinearLayout) findViewById(R.id.navigation_myfavorite2);
        layoutOrderHistory = (LinearLayout) findViewById(R.id.navigation_historyorder2);
        layoutRate = (LinearLayout) findViewById(R.id.navigation_rateus_user2);
        layoutShare = (LinearLayout) findViewById(R.id.navigation_share_user2);
        location = new HashMap<>();
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new UserPresenter();
        gps = new GPSTracker(this);
        arrAllProduct = new ArrayList<>();
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
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
        if (view == R.id.navigation_homeUser){
            onBackPressed();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, storeListFragment).commit();
        }
        if (view == R.id.navigation_myprofile){
            moveToProfileActivity ();
        }
    }

    private void moveToProfileActivity() {
        Intent intent = new Intent(MainUser2Activity.this, ProfileUserActivity.class);
        intent.putExtra(Constain.ID_USER, idUser);
        intent.putExtra(Constain.ID_STORE, true);
        startActivity(intent);
    }

    private void moveToSearchAcitvity() {
        ArrayList<Store> arrStore = storeListFragment.getArrStore();
        ArrayList<SearchStore> arrSearch = new ArrayList<>();
        for (int i = 0; i < arrStore.size() - 1; i++) {
            try {
                double lo = (double) arrStore.get(i).getLocation().get(Constain.LO);
                double la = (double) arrStore.get(i).getLocation().get(Constain.LA);
                SearchStore search = new SearchStore(arrStore.get(i).getLinkPhotoStore(), arrStore.get(i).getStoreName(), lo, la);
                arrSearch.add(search);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        Intent intent = new Intent(this,SearchStoreActivity.class);
        intent.putExtra("search" , arrSearch);
        intent.putExtra(Constain.LO, lo);
        intent.putExtra(Constain.LA, la);
        startActivity(intent);
    }

    private void initItemNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Product", R.drawable.icon_coffe, R.color.ahbottom);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("History", R.drawable.icon_history, R.color.ahbottom);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Infomation", R.drawable.icon_info, R.color.ahbottom);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.setOnTabSelectedListener(this);
        ahBottomNavigation.setCurrentItem(0);
    }

    private void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainUser2Activity.this);
        alert.setMessage("Do you want to logout?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainUser2Activity.this, MainActivity.class));
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
    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
            createProductListFragment(idStore);

        } else if (position == 1) {

        } else if (position == 2) {
        }
    }

    public void createProductListFragment(String idStore) {
        Bundle bundle = new Bundle();
        bundle.putString(Constain.ID_STORE, idStore);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_id_user, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
