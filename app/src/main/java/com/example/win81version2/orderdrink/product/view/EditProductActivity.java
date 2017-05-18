package com.example.win81version2.orderdrink.product.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.main.view.MainStoreActivity;
import com.example.win81version2.orderdrink.oop.BaseActivity;
import com.example.win81version2.orderdrink.product.model.Product;
import com.example.win81version2.orderdrink.product.presenter.ProductPresenter;
import com.example.win81version2.orderdrink.utility.Constain;

import java.io.IOException;

public class EditProductActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout layoutEditProductName, layoutEditDescribe, layoutEditPrice;
    private FrameLayout layoutChangePhoto;
    private TextView txtProductName, txtDescribe, txtPrice;
    private ImageView imgProduct;
    private ProductPresenter presenter;
    private MainStoreActivity view;
    private Product product;
    private String idStore;
    private SwitchCompat statusProduct;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        addControls();
        initInfo ();
        addEvents();
    }

    private void initInfo() {
        txtProductName.setText(product.getProductName());
        txtDescribe.setText(product.getInfoProduct());
        txtPrice.setText(Math.round(product.getPrice())+ " VND");
        String linkPhotoProduct = "";
        linkPhotoProduct = product.getLinkPhotoProduct();
        if (!linkPhotoProduct.equals("")) {
            Glide.with(EditProductActivity.this)
                    .load(linkPhotoProduct)
                    .fitCenter()
                    .into(imgProduct);
        }
    }

    private void addEvents() {
        layoutEditProductName.setOnClickListener(this);
        layoutEditDescribe.setOnClickListener(this);
        layoutEditPrice.setOnClickListener(this);
        layoutChangePhoto.setOnClickListener(this);
        statusProduct.setOnClickListener(this);
    }

    private void addControls() {
        layoutEditProductName = (LinearLayout) findViewById(R.id.layoutEditProductName);
        layoutEditDescribe = (LinearLayout) findViewById(R.id.layoutEditDescribe);
        layoutEditPrice = (LinearLayout) findViewById(R.id.layoutEditPrice);
        layoutChangePhoto = (FrameLayout) findViewById(R.id.layoutChangePhotoProduct);
        txtProductName = (TextView) findViewById(R.id.txtProductName_editProduct);
        txtDescribe = (TextView) findViewById(R.id.txtDescribe_editProduct);
        txtPrice = (TextView) findViewById(R.id.txtPrice_editProduct);
        imgProduct = (ImageView) findViewById(R.id.imgProduct_editProduct);
        statusProduct = (SwitchCompat) findViewById(R.id.switchCompat_StatusProduct);
        view = new MainStoreActivity();
        presenter = new ProductPresenter(view);
        //get Product
        product = (Product) getIntent().getSerializableExtra(Constain.PRODUCTS);
        idStore = getIntent().getStringExtra(Constain.ID_STORE);
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.layoutEditProductName) {
            updateProductName();
        }
        if (view == R.id.layoutEditDescribe) {
            updateDescribe();
        }
        if (view == R.id.layoutEditPrice) {
            updatePrice();
        }
        if (view == R.id.layoutChangePhotoProduct) {
            changePhoto();
        }
        if (view == R.id.switchCompat_StatusProduct) {
            updateStatusProduct();
        }
    }

    private void updateStatusProduct() {
        boolean status = statusProduct.isChecked();
        presenter.updateStatusProduct(idStore, product.getIdCategory(), product.getIdProduct(), status);
        showToast("Cập nhật thành công");
    }

    private void changePhoto() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_CHANGE_IMAGE_PRODUCT);
    }

    private void updatePrice() {
        AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setTitle("Cập nhật thông tin");
        final EditText edtPrice = new EditText(this);
        edtPrice.setHint("Enter your price");
        aler.setView(edtPrice);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String price = edtPrice.getText().toString().trim();
                if (TextUtils.isEmpty(price)){
                    showToast("Giá sản phẩm không được trống!");
                }
                else {
                    presenter.updatePrice(idStore, product.getIdCategory(), product.getIdProduct(), price);
                    showToast("Cập nhật thành công");
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.show();
    }

    private void updateDescribe() {
        AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setTitle("Cập nhật thông tin");
        final EditText edtDescribe = new EditText(this);
        edtDescribe.setHint("Enter your describe");
        aler.setView(edtDescribe);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String describe = edtDescribe.getText().toString().trim();
                if (TextUtils.isEmpty(describe)){
                    showToast("TMô tả không được trống!");
                }
                else {
                    presenter.updateDescribe(idStore, product.getIdCategory(), product.getIdProduct(), describe);
                    showToast("Cập nhật thành công");
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.show();
    }

    private void updateProductName() {
        AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setTitle("Cập nhật thông tin");
        final EditText edtProductName = new EditText(this);
        edtProductName.setHint("Enter your product name");
        aler.setView(edtProductName);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String productName = edtProductName.getText().toString().trim();
                 if (TextUtils.isEmpty(productName)){
                     showToast("Tên sản phẩm không được trống!");
                 }
                 else {
                     presenter.updateProductName(idStore, product.getIdCategory(), product.getIdProduct(), productName);
                     showToast("Cập nhật thành công");
                 }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constain.REQUEST_CODE_LOAD_IMAGE && resultCode == RESULT_OK ){
            if (data.getAction() != null){
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = getResizedBitmap(bitmap, 190, 140);
                presenter.updatePhotoProduct(idStore, product.getIdCategory(), product.getIdProduct(), bitmap);
                imgProduct.setImageBitmap(bitmap);
            }
            else {
                Uri filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    bitmap = getResizedBitmap(bitmap, 190, 140);
                    presenter.updatePhotoProduct(idStore, product.getIdCategory(), product.getIdProduct(), bitmap);
                    imgProduct.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
