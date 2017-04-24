package com.example.win81version2.orderdrink.main.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
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
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.ordered_history.view.Ordered_History_Fragment;
import com.example.win81version2.orderdrink.profile_user.model.User;
import com.example.win81version2.orderdrink.profile_user.view.ProfileUser_Fragment;
import com.example.win81version2.orderdrink.store_list.view.Store_List_Fragment;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class MainUserActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener , AHBottomNavigation.OnTabSelectedListener{

    private ImageView imgAvata;
    private TextView txtUserName, txtSumOrdered;
    private DatabaseReference mData;
    private String idUser, userName, linkPhotoUser;
    private String sumOrdered;
    private AHBottomNavigation ahBottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        addControls ();
        initInfo ();
    }

    private void initInfo() {
        Store_List_Fragment storeListFragment = new Store_List_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id, storeListFragment).commit();
        Intent intent = getIntent();
        idUser = intent.getStringExtra(Constain.ID_USER);
        mData.child(Constain.USERS).child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    try
                    {
                        User user = dataSnapshot.getValue(User.class);
                        userName = user.getUserName();
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
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                else {
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        txtUserName = (TextView) header.findViewById(R.id.txtusername_mainuser);
        txtSumOrdered = (TextView) header.findViewById(R.id.txtsumordered);
        imgAvata = (ImageView) header.findViewById(R.id.profile_image);

        //Navigation Bottom
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.navigation);
        initItemNavigation ();
        mData = FirebaseDatabase.getInstance().getReference();


    }

    private void initItemNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Stores", R.drawable.ic_store);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("History Ordered", R.drawable.icon_shopping);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("My Profile", R.drawable.icon_profile);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.bg_ahbottom));
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        if (id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
            Store_List_Fragment storeListFragment = new Store_List_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, storeListFragment).commit();
        } else if (position == 1) {
            Ordered_History_Fragment ordered_history_fragment = new Ordered_History_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, ordered_history_fragment).commit();
        } else if (position == 2) {
            ProfileUser_Fragment profileUser_fragment = new ProfileUser_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, profileUser_fragment).commit();

        }
    }
}
