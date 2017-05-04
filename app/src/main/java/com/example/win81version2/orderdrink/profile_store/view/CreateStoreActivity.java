package com.example.win81version2.orderdrink.profile_store.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.profile_store.presenter.CreateStorePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class CreateStoreActivity extends BaseActivity implements View.OnClickListener{

    private EditText edtStoreName, edtEmail, edtPhoneNumber, edtPassword, edtAddress;
    private TextView txtFrom, txtTo;
    private Button btnCreateStore;
    private CreateStorePresenter presenter;
    private FirebaseAuth mAuth;
    private String startTime, endTime;
    private int hourStart = 0, hourEnd = 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        addControls ();
        addEvents ();
    }

    private void addEvents() {
        btnCreateStore.setOnClickListener(this);
        txtFrom.setOnClickListener(this);
        txtTo.setOnClickListener(this);
    }

    private void createStore() {
        boolean isVaild = true;
        String storeName = (edtStoreName.getText().toString()).trim();
        String email = (edtEmail.getText().toString()).trim ();
        String phoneNumber = (edtPhoneNumber.getText().toString()).trim();
        String password = (edtPassword.getText().toString()).trim();
        String adddress = (edtAddress.getText().toString()).trim();
        String from = (txtFrom.getText().toString()).trim();
        String to = (txtTo.getText().toString()).trim();
        if (from.length() == 0 || to.length() ==  0){
            isVaild = false;
            showToast("Bạn chưa chọn thời gian là việc!");
        }
        if (TextUtils.isEmpty(storeName)){
              isVaild = false;
            edtStoreName.setError("Bắt Buộc");
        }
        else {
            edtStoreName.setError(null);
        }
        if (TextUtils.isEmpty(email)){
            isVaild = false;
            edtEmail.setError("Bắt Buộc");
        }
        else {
            edtEmail.setError(null);
        }
        if (!isEmailVail(email)){
            isVaild = false;
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
        }
        if (TextUtils.isEmpty(phoneNumber)){
            isVaild = false;
            edtPhoneNumber.setError("Bắt Buộc");
        }
        else {
            edtPhoneNumber.setError(null);
        }
        if (TextUtils.isEmpty(adddress)){
            isVaild = false;
            edtAddress.setError("Bắt Buộc");
        }
        else {
            edtAddress.setError(null);
        }
        if (TextUtils.isEmpty(password)){
            isVaild = false;
            edtPassword.setError("Bắt Buộc");
        }
        else {
            edtPassword.setError(null);
        }
        if (password.length() < 6 && password.length() > 0){
            isVaild = false;
            showToast("Mật khẩu phải lớn hơn 6 ký tự!");
            edtPassword.setText("");
            edtPassword.requestFocus();
        }
        if (isVaild){
            //Create new store
            showProgressDialog();
            presenter.createNewStore(email, password, storeName, phoneNumber, adddress, from, to);

        }
    }
    private void addControls() {
        //EditText
        edtStoreName = (EditText) findViewById(R.id.edtstorename);
        edtEmail = (EditText) findViewById(R.id.edtemail_createstore);
        edtPhoneNumber = (EditText) findViewById(R.id.edtphonenumber_createstore);
        edtPassword = (EditText) findViewById(R.id.edtpassword_createstore);
        //TextView
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        txtFrom = (TextView) findViewById(R.id.txtFrom);
        txtTo = (TextView) findViewById(R.id.txtTo);
        //Button
        btnCreateStore = (Button) findViewById(R.id.btncreatestor);
        //presenter
        mAuth = FirebaseAuth.getInstance();
        presenter = new CreateStorePresenter(this, mAuth);
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btncreatestor ){
            createStore();
        }
        if (view == R.id.txtFrom) {
            showTimeStartPicker ();
        }
        if (view == R.id.txtTo ){
            showTimeEndPicker ();
        }
    }

    private void showTimeEndPicker() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                endTime = hourOfDay + "h" ;
                hourEnd = hourOfDay;
                txtTo.setText(endTime);
            }
        };
        TimePickerDialog timePicker = TimePickerDialog.newInstance(callBack, hour, minute, second, true);
        timePicker.setMinTime(hourStart, 0, 0);
        timePicker.show(getFragmentManager(), "TimeEndPickerDialog");
    }

    private void showTimeStartPicker() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                startTime = hourOfDay + "h";
                hourStart = hourOfDay;
                txtFrom.setText(startTime);
            }
        };
        TimePickerDialog timePicker = TimePickerDialog.newInstance(callBack, hour, minute, second, true);
        timePicker.setMaxTime(hourEnd, 59, 59);
        timePicker.show(getFragmentManager(), "TimeStartPickerDialog");
    }
}
