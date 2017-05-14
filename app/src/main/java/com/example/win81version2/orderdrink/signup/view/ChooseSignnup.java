package com.example.win81version2.orderdrink.signup.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.win81version2.orderdrink.Login.view.LoginActivity;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.profile_store.view.CreateStoreActivity;

public class ChooseSignnup extends AppCompatActivity {

    private Button btnChooseUser;
    private Button btnChooseShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_signnup);
        btnChooseShop = (Button) findViewById(R.id.btnChooseShop);
        btnChooseUser = (Button) findViewById(R.id.btnChooseUser);
        btnChooseShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseSignnup.this, CreateStoreActivity.class));
            }
        });

        btnChooseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseSignnup.this, SignupUserActivity.class));
            }
        });
    }
}
