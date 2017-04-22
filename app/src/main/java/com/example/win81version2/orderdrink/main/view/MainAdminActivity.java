package com.example.win81version2.orderdrink.main.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.profile_store.view.CreateStoreActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainAdminActivity extends BaseActivity {

     private Button btnCreateStore, btnUserList, btnStoreList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        addControls ();
        addEvents ();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {
        btnCreateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainAdminActivity.this, CreateStoreActivity.class));
            }
        });
    }

    private void addControls() {
        btnCreateStore = (Button) findViewById(R.id.btncreatestore);
        btnUserList = (Button) findViewById(R.id.btnlistuser);
        btnStoreList = (Button) findViewById(R.id.btnliststore);
    }
}
