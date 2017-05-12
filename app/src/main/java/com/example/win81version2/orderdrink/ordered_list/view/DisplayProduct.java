package com.example.win81version2.orderdrink.ordered_list.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.my_order.model.MyOrder;
import com.example.win81version2.orderdrink.my_order.presenter.MyOrderPresenter;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.ordered_list.model.OrderList;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.utility.Constain;

import java.util.Calendar;

public class DisplayProduct extends BaseActivity {

    private ImageView imgPhotoProduct;
    private TextView txtProductName, txtDescribe, txtPrice;
    private Button btnBuyProduct;
    private Product product;
    private EditText edtCountProduct;
    private MyOrderPresenter prsenter;
    private String idUser, categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);
        addControls ();
        initInfo ();
        addEvents ();
    }

    private void addEvents() {
        btnBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToMyOrderList ();
            }
        });
    }

    private void addToMyOrderList() {
        if (TextUtils.isEmpty(edtCountProduct.getText().toString())){
            edtCountProduct.setError("Bắt buộc");
        }
        else {
            int countProduct = Integer.parseInt(edtCountProduct.getText().toString());
            float price = product.getPrice() * countProduct;
            Calendar now = Calendar.getInstance();
            int day = now.get(Calendar.DAY_OF_MONTH);
            int month = now.get(Calendar.MONTH);
            int year = now.get(Calendar.YEAR);
            int hour = now.get(Calendar.HOUR);
            int minute = now.get(Calendar.MINUTE);
            String timeOrder = hour + "h:" + minute + "p - " + day + "\\" + month + "\\" + year;
            edtCountProduct.setError("Bắt buộc");
            MyOrder myOrder = new MyOrder(categoryName, product.getIdProduct(), product.getProductName(), countProduct, timeOrder, 0, price);
            prsenter.createMyOrder(idUser, myOrder);
            showToast("Đặt hàng thành công!");
            finish();
        }
    }
    private void initInfo() {
        if (product != null) {
            txtProductName.setText(product.getProductName());
            txtDescribe.setText(product.getInfoProduct());
            txtPrice.setText(String.valueOf(product.getPrice()));
        }
    }

    private void addControls() {
        imgPhotoProduct = (ImageView) findViewById(R.id.imgPhotoProduct);
        txtProductName = (TextView) findViewById(R.id.txtProductName_displayproduct);
        txtDescribe = (TextView) findViewById(R.id.txtDescriber);
        txtPrice = (TextView) findViewById(R.id.txtPrice_displayproduct);
        btnBuyProduct = (Button) findViewById(R.id.btnBuyProduct);
        edtCountProduct = (EditText) findViewById(R.id.edtCountProduct);
        prsenter = new MyOrderPresenter();
        //get Data
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra(Constain.PRODUCTS);
        categoryName = intent.getStringExtra(Constain.CATEGORY_NAME);
        idUser = getIdUser();
    }
}
