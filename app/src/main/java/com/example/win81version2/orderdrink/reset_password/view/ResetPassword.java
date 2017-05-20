package com.example.win81version2.orderdrink.reset_password.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends BaseActivity {

    private EditText edtEmailReset;
    private Button btnResetEmail;
    private Button btnCancelReset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        addControls();
        addEvent();
    }

    private void addEvent() {
        btnCancelReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEmail();
            }
        });
    }

    private void resetEmail() {
        showProgressDialog();
        String email = edtEmailReset.getText().toString().trim();
        if (!isEmailVail(email)){
            hideProgressDialog();
            hideSoftKeyboard(ResetPassword.this);
            showToast("Email không hợp lệ, vui lòng thử lại!");
            edtEmailReset.requestFocus();
        }
        else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        hideProgressDialog();
                        hideSoftKeyboard(ResetPassword.this);
                        showToast("Một Email đã gửi tới hòm thư của bạn, vui lòng kiểm tra để thay đổi password!");
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    hideProgressDialog();
                    hideSoftKeyboard(ResetPassword.this);
                    showToast("Email chưa được đăng kí hoặc tài khoản đã bị xóa,vui lòng thử lại!");
                    edtEmailReset.requestFocus();
                }
            });
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void addControls() {
        edtEmailReset = (EditText) findViewById(R.id.edtEmailReset);
        btnResetEmail = (Button) findViewById(R.id.btnResetEmail);
        btnCancelReset = (Button) findViewById(R.id.btnCancelReset);
        mAuth = FirebaseAuth.getInstance();
    }
}
