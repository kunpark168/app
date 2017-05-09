package com.example.win81version2.orderdrink.main.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
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
import com.example.win81version2.orderdrink.category.view.CategoryListFragment;
import com.example.win81version2.orderdrink.notification_store.view.Notification_Store_Fragment;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.product.view.CreateProductFragment;
import com.example.win81version2.orderdrink.product_list.view.ProductListFragment;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.profile_store.presenter.UpdateStorePresenter;
import com.example.win81version2.orderdrink.profile_store.view.Profile_Store_Fragment;
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
    private LinearLayout layoutMyCategory, layoutReport, layoutCreateProduct, layoutRateUs, layoutShare;
    private String idStore, linkPhotoStore = "";
    private DatabaseReference mData;
    private Button btnLogout;
    private SwitchCompat switchCompatStatus;
    private boolean isOpen = true;
    private Bitmap bitmap = null;
    private CreateProductFragment createProductFragment;
    private UpdateStorePresenter presenter;

    public MainStoreActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main_store);
        addControls();
        innitInfo();
        addEvents ();
    }

    private void addEvents() {
        //My Category
        layoutMyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                setTitle("My Category");
                CategoryListFragment categoryListFragment = new CategoryListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, categoryListFragment).commit();
            }
        });
        //Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        //Opent - Close Store
        switchCompatStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompatStatus.isChecked() == true ){
                    isOpen = true;
                    presenter.updateStatusStore(idStore, isOpen);
                }
                else {
                    isOpen = false;
                    presenter.updateStatusStore(idStore, isOpen);
                }
            }
        });
        //Create Product
        layoutCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                setTitle("Create Product");
                createProductFragment = new CreateProductFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, createProductFragment).commit();
            }
        });

    }

    private void innitInfo() {
        Intent intent = getIntent();
        idStore = intent.getStringExtra(Constain.ID_STORE);
        try {
          mData.child(Constain.STORES).child(idStore).addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  if (dataSnapshot.getValue() != null){
                      try {
                          Store store = dataSnapshot.getValue(Store.class);
                          txtStoreName.setText(store.getStoreName().toString());
                          txtSumShipped.setText(String.valueOf(store.getSumShipped()) + " Shipped");
                          linkPhotoStore = store.getLinkPhotoStore().toString();
                          isOpen = store.isOpen();
                          switchCompatStatus.setChecked(isOpen);
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
        txtSumShipped = (TextView) findViewById(R.id.txtSumShipped_mainstore);
        imgPhotoStore = (ImageView) findViewById(R.id.imgPhotoStore_mainstore);
        btnLogout = (Button) findViewById(R.id.btn_logout_store);
        layoutMyCategory = (LinearLayout) findViewById(R.id.navigation_mycategory);
        layoutCreateProduct = (LinearLayout) findViewById(R.id.navigation_createproduct);
        layoutReport = (LinearLayout) findViewById(R.id.navigation_report);
        layoutRateUs = (LinearLayout) findViewById(R.id.navigation_rateus_store);
        layoutShare = (LinearLayout) findViewById(R.id.navigation_share_store);
        switchCompatStatus = (SwitchCompat) findViewById(R.id.switchCompat_Status);
        //Presenter
        presenter = new UpdateStorePresenter(this);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_store);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder aler = new AlertDialog.Builder(this);
            aler.setMessage("Bạn có muốn thoát app ?");
            aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
            aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            aler.show();
        }
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
           // ProductListActivity product_list_fragment = new ProductListActivity();
           // getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, product_list_fragment).commit();
        } else if (position == 1) {
            Notification_Store_Fragment notification_store_fragment = new Notification_Store_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, notification_store_fragment).commit();
        } else if (position == 2) {
            Profile_Store_Fragment profile_store_fragment = new Profile_Store_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, profile_store_fragment).commit();

        }
    }

    private void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainStoreActivity.this);
        alert.setMessage("Do you want to logout?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainStoreActivity.this, MainActivity.class));
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
