package com.example.win81version2.orderdrink.signup.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.signup.presenter.SignUpActivityPresenter;
import com.example.win81version2.orderdrink.utility.Constain;

import java.util.HashMap;

public class SignupActivity extends BaseActivity {

    private EditText edtEmail, edtPassword, edtConfirmPassword, edtPhoneNumber, edtUserName;
    private Button btnSignup;
    private SignUpActivityPresenter presenter;
    private double lo = 0.0;
    private double la = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addControls ();
        addEvents ();
    }

    private void addEvents() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp ();

            }
        });
    }

    private void signUp() {
        boolean isVail = true;
        String email = (edtEmail.getText().toString()).trim();
        String password = (edtPassword.getText().toString()).trim();
        String confirmPassword = (edtConfirmPassword.getText().toString()).trim();
        String phoneNumber = (edtPhoneNumber.getText().toString()).trim();
        String userName = (edtUserName.getText().toString()).trim();
        if (TextUtils.isEmpty(userName)){
            isVail = false;
            edtUserName.setError("Bắt Buộc");
        }
        else {
            edtUserName.setError(null);
        }
        if (TextUtils.isEmpty(email)){
            isVail = false;
            edtEmail.setError("Bắt Buộc");
        }
        else {
            edtEmail.setError(null);
        }
        if (!isEmailVail(email)){
            isVail = false;
            edtEmail.setError("Email không hợp lệ!");
        }
        else {
            edtEmail.setError(null);
        }
        if (TextUtils.isEmpty(phoneNumber)){
            isVail = false;
            edtPhoneNumber.setError("Bắt Buộc");
        }
        else {
            edtPhoneNumber.setError(null);
        }
        if (password.length() > 0 && password.length() <6){
            isVail = false;
            edtPassword.setText("");
            edtPassword.requestFocus();
            showToast("Mật khẩu phải ít nhất 6 ký tự");
        }
        if (!confirmPassword.equals(password)){
            isVail = false;
            edtConfirmPassword.setError("Mật khẩu không khớp");
        }
        else {
            edtConfirmPassword.setError(null);
        }
        if (isVail) {
            showProgressDialog();
            HashMap<String, Object> favorite_drink = new HashMap<>();
            HashMap<String , Object> location = new HashMap<>();
            location.put(Constain.ADDRESS, "");
            location.put(Constain.LO, lo);
            location.put(Constain.LA, la);
            presenter.signUpWithEmail(password, userName, email, phoneNumber, "", "", false, location, favorite_drink);
            //presenter.signUp(email, password);
        }
    }

    private void addControls() {
        //EditText
        edtEmail = (EditText) findViewById(R.id.edtemail_signup);
        edtPhoneNumber = (EditText) findViewById(R.id.edtphonenumber_signup);
        edtPassword = (EditText) findViewById(R.id.edtpassword_signup);
        edtConfirmPassword = (EditText) findViewById(R.id.edtconfirmpassword_signup);
        edtUserName = (EditText) findViewById(R.id.edtusername_signup);
        //Button
        btnSignup = (Button) findViewById(R.id.btnsignup);
        //presenter
        presenter = new SignUpActivityPresenter(this);
    }
}
