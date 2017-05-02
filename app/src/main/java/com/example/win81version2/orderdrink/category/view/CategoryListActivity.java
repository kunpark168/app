package com.example.win81version2.orderdrink.category.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.main.view.MainActivity;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.category.model.Category;
import com.example.win81version2.orderdrink.category.model.CategoryListAdapter;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.utility.Constain;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgAvata, imgCreateCategory;
    private TextView txtStoreName, txtSumShipped;
    private DatabaseReference mData;
    private String idStore, linkPhotoStore;
    private Button btnLogout;
    private RecyclerView recyclerCategory;
    private ArrayList<Category> arrCategory;
    private CategoryListAdapter adapter;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_category_list);
        addControls();
        initInfo();
        addEvvents();
    }

    private void addEvvents() {
        btnLogout.setOnClickListener(CategoryListActivity.this);
        imgCreateCategory.setOnClickListener(this);
    }

    private void initInfo() {
        Intent intent = getIntent();
        idStore = intent.getStringExtra(Constain.ID_STORE);
        //Infor inside sliding menu
        try {
            mData.child(Constain.STORES).child(idStore).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            Store store = dataSnapshot.getValue(Store.class);
                            txtStoreName.setText(store.getStoreName().toString());
                            txtSumShipped.setText(store.getSumShipped());
                            linkPhotoStore = store.getLinkPhotoStore();
                            if (!linkPhotoStore.equals("")) {
                                Glide.with(CategoryListActivity.this)
                                        .load(linkPhotoStore)
                                        .fitCenter()
                                        .into(imgAvata);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Info List Category
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.PRODUCTS).addValueEventListener(new ValueEventListener() {
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_createCategory);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_create_category);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //View
        imgAvata = (ImageView) findViewById(R.id.imgPhotoStore_createCategory);
        txtStoreName = (TextView) findViewById(R.id.txtstorename_createCategory);
        txtSumShipped = (TextView) findViewById(R.id.txtSumShipped_createCategory);
        btnLogout = (Button) findViewById(R.id.btn_logout_store__createCategory);
        imgCreateCategory = (ImageView) findViewById(R.id.imgCreateCategory);
        mData = FirebaseDatabase.getInstance().getReference();
        //List Category
        mManager = new LinearLayoutManager(this);
        recyclerCategory = (RecyclerView) findViewById(R.id.lvCategory);
        arrCategory = new ArrayList<>();
        adapter = new CategoryListAdapter(arrCategory, this);
        recyclerCategory.setAdapter(adapter);
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_create_category);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btn_logout_store__createCategory) {
            logOut();
        }
        if (view == R.id.imgCreateCategory) {
            createCategory();
        }
    }

    private void createCategory() {
        Intent intent = new Intent(CategoryListActivity.this, CreateCategory.class);
        intent.putExtra(Constain.ID_STORE, idStore);
        startActivity(intent);
    }

    private void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(CategoryListActivity.this);
        alert.setMessage("Do you want to logout?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CategoryListActivity.this, MainActivity.class));
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
