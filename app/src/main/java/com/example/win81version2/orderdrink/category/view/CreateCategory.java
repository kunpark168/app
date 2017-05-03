package com.example.win81version2.orderdrink.category.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.model.Category;
import com.example.win81version2.orderdrink.category.presenter.CategoryPresenter;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CreateCategory extends BaseActivity {

    private Button btnCancel, btnOk;
    private EditText edtCategoryName;
    private String idStore;
    private DatabaseReference mData;
    private String idCategory, timeCreate;
    private CategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        addControls ();
        addEvents ();
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = edtCategoryName.getText().toString();
                if (!TextUtils.isEmpty(flag)){
                    AlertDialog.Builder alert = new AlertDialog.Builder(CreateCategory.this);
                    alert.setMessage("Bạn đang nhập dở,Thoát có thể làm mất dữ liệu?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setCancelable(false);
                    alert.show();
                }
                else {
                    finish();
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });
    }

    private void createCategory() {
        String flag_CategoryName1 = edtCategoryName.getText().toString();
        boolean isVail = true;
        if (TextUtils.isEmpty(flag_CategoryName1)){
            isVail = false;
            edtCategoryName.setError("Bắt Buộc");
        }
        else {
            edtCategoryName.setError(null);
        }
        if (isVail) {
            String [] tu = flag_CategoryName1.trim().split(" ");
            String flag_Category2 = "";
            for (int i = 0 ; i<tu.length ; i++){
                tu[i] = Character.toUpperCase(tu[i].charAt(0)) + tu[i].substring(1) + " ";
                flag_Category2 += tu[i];
            }
            final String categoryName = flag_Category2.trim();
            try {
                mData.child(Constain.STORES).child(idStore).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            idCategory = String.valueOf(dataSnapshot.getChildrenCount());
                            if (idCategory.length() == 1){
                                idCategory = "0" + idCategory;
                            }
                            boolean flag = true;
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                try {
                                    Category category = dt.getValue(Category.class);
                                    if (category.getCategoryName().equals(categoryName)){
                                        flag = false;
                                        Toast.makeText(CreateCategory.this, "Tên Thư Mục Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                                        edtCategoryName.requestFocus();
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if (flag) {
                                presenter.addCategoryOnData(idStore, idCategory, categoryName, 0, timeCreate);
                                setTitle("My Category");
                                CategoryListFragment categoryListFragment = new CategoryListFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_id_store, categoryListFragment).commit();
                                Toast.makeText(CreateCategory.this, "Tạo Thư Mục Thành Công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            idCategory = "00";
                            presenter.addCategoryOnData(idStore, idCategory, categoryName, 0, timeCreate);
                            Toast.makeText(CreateCategory.this, "Tạo Thư Mục Thành Công!", Toast.LENGTH_SHORT).show();
                            edtCategoryName.setText("");
                            edtCategoryName.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addControls() {
        btnCancel = (Button) findViewById(R.id.btnCancelCreateCategory);
        btnOk = (Button) findViewById(R.id.btnOkCreateCategory);
        edtCategoryName = (EditText) findViewById(R.id.edtCategoryName_createCategory);
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new CategoryPresenter();
        //get idStore
        Intent intent = getIntent();
        idStore = intent.getStringExtra(Constain.ID_STORE);
        //get Timenow
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        int mouth = now.get(Calendar.MONTH) + 1;
        int year = now.get(Calendar.YEAR);
        timeCreate = day + "\\" + mouth + "\\" + year;
    }

}
