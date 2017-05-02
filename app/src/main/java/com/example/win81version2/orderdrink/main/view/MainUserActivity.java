package com.example.win81version2.orderdrink.main.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class MainUserActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgAvata;
    private TextView txtUserName, txtSumOrdered;
    private DatabaseReference mData;
    private String idUser, userName, linkPhotoUser, sumOrdered;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main_user);
        addControls ();
        initInfo ();
        addEvvents ();
    }

    private void addEvvents() {
        btnLogout.setOnClickListener(this);
    }

    private void initInfo() {
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

        //View
        imgAvata = (ImageView) findViewById(R.id.imgPhotoUser);
        txtUserName = (TextView) findViewById(R.id.txtusername_mainuser);
        txtSumOrdered = (TextView) findViewById(R.id.txtSumOreders_mainuser);
        btnLogout = (Button) findViewById(R.id.btn_logout_user);
        mData = FirebaseDatabase.getInstance().getReference();
        Store_List_Fragment storeListFragment = new Store_List_Fragment();
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
        if (view == R.id.btn_logout_user){
            logOut();
        }
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
}
