package com.example.win81version2.orderdrink.product.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.category.model.Category;
import com.example.win81version2.orderdrink.category.view.CategoryListFragment;
import com.example.win81version2.orderdrink.category.view.CreateCategory;
import com.example.win81version2.orderdrink.oop.BaseFragment;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.product.model.SpinnerAdapter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateProductFragment extends BaseFragment implements View.OnClickListener {

    private EditText edtProductName, edtDescribeProduct, edtPrice;
    private Button btnChooseimg, btnCreateProduct;
    private ImageView imgProduct;
    private Spinner spinnerCategory;

    private SpinnerAdapter adapter;
    private ArrayList<Category> arrCategory;
    private DatabaseReference mData;
    private String idStore, idCategory, idProduct;

    public CreateProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addControl();
        initInfo();
        addEvent();

    }

    private void initInfo() {
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            arrCategory.clear();
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                Category category = dt.getValue(Category.class);
                                arrCategory.add(category);
                                adapter.notifyDataSetChanged();
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
    }

    private void addEvent() {
        btnChooseimg.setOnClickListener(this);
        btnCreateProduct.setOnClickListener(this);
        spinnerCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idCategory = String.valueOf(position);
                if (idCategory.length() == 1) {
                    idCategory = "0" + idCategory;
                }
            }
        });
    }

    private void addControl() {
        edtProductName = (EditText) getActivity().findViewById(R.id.edtProductName);
        edtDescribeProduct = (EditText) getActivity().findViewById(R.id.edtDescribeProduct);
        edtPrice = (EditText) getActivity().findViewById(R.id.edtPrice);
        btnChooseimg = (Button) getActivity().findViewById(R.id.btnChooseimg);
        btnCreateProduct = (Button) getActivity().findViewById(R.id.btnCreateProduct);
        imgProduct = (ImageView) getActivity().findViewById(R.id.imgProduct);
        spinnerCategory = (Spinner) getActivity().findViewById(R.id.spinnerCategory);
        mData = FirebaseDatabase.getInstance().getReference();
        idStore = getActivity().getIntent().getStringExtra(Constain.ID_STORE);
        arrCategory = new ArrayList<>();
        adapter = new SpinnerAdapter(arrCategory, getActivity());
        spinnerCategory.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create__product_, container, false);
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btnChooseimg) {
            showImage();
        }
        if (view == R.id.btnCreateProduct) {
            createProduct();
        }
    }

    private void createProduct() {
        String flag_nameProduct = edtProductName.getText().toString();
        String flag_price = edtPrice.getText().toString();
        boolean isVail = true;
        if (TextUtils.isEmpty(flag_nameProduct)) {
            isVail = false;
            edtProductName.setError("Bắt Buộc");
        } else {
            edtProductName.setError(null);
        }
        if (TextUtils.isEmpty(flag_price)) {
            isVail = false;
            edtPrice.setError("Bắt Buộc");
        } else {
            edtPrice.setError(null);
        }


        if (isVail) {
            String[] tu = flag_nameProduct.trim().split(" ");
            String flag_nameProduct2 = "";
            for (int i = 0; i < tu.length; i++) {
                tu[i] = Character.toUpperCase(tu[i].charAt(0)) + tu[i].substring(1) + " ";
                flag_nameProduct2 += tu[i];
            }
            final String productName = flag_nameProduct2.trim();
            try {
                mData.child(Constain.STORES).child(idStore).child(Constain.PRODUCTS).child(idCategory).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue() != null) {
                                boolean isVail = true;
                                idProduct = String.valueOf(dataSnapshot.getChildrenCount());
                                if (idProduct.length() == 1){
                                    idProduct = "0" + idProduct;
                                }
                                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                    Product product = dt.getValue(Product.class);
                                    if (productName.equals(product.getProductName())) {
                                        isVail = false;
                                        showToast("Tên sản phẩm đã có,vui lòng thử lại");
                                        edtProductName.requestFocus();
                                    }
                                }
                                if (isVail) {

                                }
                            } else {
                                idProduct = "00";
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
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

    private void showImage() {

    }
}
