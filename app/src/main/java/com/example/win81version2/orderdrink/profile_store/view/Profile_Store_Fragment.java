package com.example.win81version2.orderdrink.profile_store.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;
import com.example.win81version2.orderdrink.main.view.MainStoreActivity;
import com.example.win81version2.orderdrink.oop.BaseFragment;
import com.example.win81version2.orderdrink.profile_store.model.Store;
import com.example.win81version2.orderdrink.profile_store.presenter.UpdateStorePresenter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

public class Profile_Store_Fragment extends BaseFragment implements View.OnClickListener {

    private String idStore, storeName, phoneNumber, address, email;
    private FrameLayout layoutChangeCover, layoutChangeAvata;
    private TextView txtSumOrdered, txtSumShipped, txtStoreName, txtPhoneNumberStore, txtEmailStore, txtAddressStore;
    private LinearLayout layoutEditStoreName, layoutEditEmailStore, layoutEditPhoneNumberStore, layoutEditAddressStore, layoutEditPasswordStore, layoutPassword;
    private ImageView imgCover, imgAvata, imgEditAvata, imgEditCover;
    private DatabaseReference mData;
    private boolean isStore;
    private UpdateStorePresenter presenter;
    private MainStoreActivity view;
    private Bitmap bitmapCover = null;
    private Bitmap bitmapAvata = null;
    public Profile_Store_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isStore = getArguments().getBoolean(Constain.IS_STORE);
        idStore = getArguments().getString(Constain.ID_STORE);
        return inflater.inflate(R.layout.fragment_profile__store_, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls ();
        initInfo ();
        addEvents ();
    }

    private void addEvents() {
        layoutChangeCover.setOnClickListener(this);
        layoutChangeAvata.setOnClickListener(this);
        layoutEditStoreName.setOnClickListener(this);
        layoutEditEmailStore.setOnClickListener(this);
        layoutEditPhoneNumberStore.setOnClickListener(this);
        layoutEditAddressStore.setOnClickListener(this);
        layoutEditPasswordStore.setOnClickListener(this);
    }

    private void initInfo() {
        if (isStore == false){
            imgEditCover.setVisibility(View.INVISIBLE);
            imgEditAvata.setVisibility(View.INVISIBLE);
            layoutPassword.setVisibility(View.INVISIBLE);
            layoutEditStoreName.setVisibility(View.INVISIBLE);
            layoutEditEmailStore.setVisibility(View.INVISIBLE);
            layoutEditPhoneNumberStore.setVisibility(View.INVISIBLE);
            layoutEditAddressStore.setVisibility(View.INVISIBLE);
            layoutEditPasswordStore.setVisibility(View.INVISIBLE);
        }
        //get Info Store
        try {
            mData.child(Constain.STORES).child(idStore).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                      try {
                          Store store = dataSnapshot.getValue(Store.class);
                          if (store.getStoreName() != null){
                              storeName = store.getStoreName();
                              txtStoreName.setText(storeName);
                          }
                          if (store.getPhoneNumber() != null){
                              phoneNumber = store.getPhoneNumber();
                              txtPhoneNumberStore.setText(phoneNumber);
                          }
                          if (store.getLocation() != null){
                              HashMap<String, Object> location = new HashMap<String, Object>();
                              location = store.getLocation();
                              address = (String) location.get(Constain.ADDRESS);
                              txtAddressStore.setText(address);
                          }
                          if (store.getEmail() != null){
                              email = store.getEmail();
                              txtEmailStore.setText(email);
                          }
                          if (!store.getLinkPhotoStore().equals("")){
                              Glide.with(getActivity())
                                      .load(store.getLinkPhotoStore())
                                      .fitCenter()
                                      .into(imgAvata);
                          }
                          if (!store.getLinkCoverStore().equals("")){
                              Glide.with(getActivity())
                                      .load(store.getLinkCoverStore())
                                      .fitCenter()
                                      .into(imgCover);
                          }
                          txtSumShipped.setText(String.valueOf(store.getSumShipped()));
                          txtSumOrdered.setText(String.valueOf(store.getSumOrdered()));

                      }
                      catch (Exception ex){
                          ex.printStackTrace();
                      }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addControls() {
        mData = FirebaseDatabase.getInstance().getReference();
        view = new MainStoreActivity();
        presenter = new UpdateStorePresenter(view);
        //Ánh xạ
        layoutChangeAvata = (FrameLayout) getActivity().findViewById(R.id.layoutChangeAvataStore);
        layoutChangeCover = (FrameLayout) getActivity().findViewById(R.id.layoutChangeCoverStore);
        layoutEditAddressStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditAdressStore);
        layoutEditEmailStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditEmailStore);
        layoutEditStoreName = (LinearLayout) getActivity().findViewById(R.id.layoutEditStoreName);
        layoutEditPasswordStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditPasswordStore);
        layoutPassword = (LinearLayout) getActivity().findViewById(R.id.layoutPasswordStore);
        layoutEditPhoneNumberStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditPhoneNumberStore);
        txtAddressStore = (TextView) getActivity().findViewById(R.id.txtAddress_profileStore);
        txtEmailStore = (TextView) getActivity().findViewById(R.id.txtEmail_profileStore);
        txtPhoneNumberStore = (TextView) getActivity().findViewById(R.id.txtPhoneNumber_profileStore);
        txtSumOrdered = (TextView) getActivity().findViewById(R.id.txtSumOredered_profileStore);
        txtSumShipped = (TextView) getActivity().findViewById(R.id.txtSumShipped_profileStore);
        txtStoreName = (TextView) getActivity().findViewById(R.id.txtStoreName_profileStore);
        imgCover = (ImageView) getActivity().findViewById(R.id.imgCoverStore);
        imgAvata = (ImageView) getActivity().findViewById(R.id.imgAvataStore);
        imgEditAvata = (ImageView) getActivity().findViewById(R.id.imgEditAvataStore);
        imgEditCover = (ImageView) getActivity().findViewById(R.id.imgEditCoverStore);
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.layoutChangeCoverStore){
            changeCover ();
        }
        if (view == R.id.layoutChangeAvataStore){
            changeAvata ();
        }
        if (view == R.id.layoutEditStoreName){
            updateStoreName ();
        }
        if (view == R.id.layoutEditEmailStore){
            updateEmailStore ();
        }
        if (view == R.id.layoutEditPhoneNumberStore){
            updatePhoneNumberStore ();
        }
        if (view == R.id.layoutEditAdressStore){
            updateAddressStore ();
        }
        if (view == R.id.layoutEditPasswordStore){
            updatePasswordStore ();
        }
    }

    private void updatePasswordStore() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtPassword = new EditText(getContext());
        final EditText edtConfirmPassword = new EditText(getContext());
        edtConfirmPassword.setHint("Confirm New Password");
        edtPassword.setHint("New Password ");
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 0, 10, 0);
        TextView txtOldPass = new TextView(getContext());
        txtOldPass.setText("Password hiện tại");
        txtOldPass.setTextColor(R.color.item_Active);
        txtOldPass.setTextSize(12);
        final EditText edtOldPass = new EditText(getContext());
        edtOldPass.setHint("Enter your current password");
        TextView txtNewPass = new TextView(getContext());
        txtNewPass.setText("Password mới");
        txtNewPass.setTextColor(R.color.item_Active);
        txtNewPass.setTextSize(12);
        layout.addView(txtOldPass);
        layout.addView(edtOldPass);
        layout.addView(txtNewPass);
        layout.addView(edtPassword);
        layout.addView(edtConfirmPassword);
        aler.setView(layout);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = true;
                String password = edtOldPass.getText().toString().trim();
                String passwrord = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();
                if (!passwrord.equals(confirmPassword)){
                    flag = false;
                    showToast("Mật khẩu không trùng khớp, vui lòng thử lại!");
                }
                if (passwrord.length() >0 && passwrord.length() < 6){
                    flag = false;
                    showToast("Mật khẩu phải ít nhất 6 ký, vui lòng thử lại!");
                }
                if (flag == true ){
                    presenter.updatePasswordStore(email, password, passwrord);
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void updateAddressStore() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtAddress = new EditText(getContext());
        edtAddress.setHint("Enter your Address store ");
        aler.setView(edtAddress);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtAddress.getText().toString())){
                    showToast("Address không được trống!");
                }
                else {
                    presenter.updateAddressStore(idStore, edtAddress.getText().toString());
                    txtAddressStore.setText(edtAddress.getText());
                    showToast("Cập nhật địa chỉ thành công!");
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void updatePhoneNumberStore() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtPhoneNumber = new EditText(getContext());
        edtPhoneNumber.setHint("Enter your phonenumber store ");
        aler.setView(edtPhoneNumber);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtPhoneNumber.getText().toString())){
                    showToast("Phonenumber không được trống!");
                }
                else {
                    presenter.updatephoneNumberStore(idStore, edtPhoneNumber.getText().toString());
                    txtPhoneNumberStore.setText(edtPhoneNumber.getText());
                    showToast("Cập nhật số điện thoại thành công!");
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void updateEmailStore() {
        final AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText edtEmail = new EditText(getContext());
        edtEmail.setHint("Enter your email store ");
        layout.addView(edtEmail);
        layout.setPadding(10, 0, 10, 0);
        aler.setView(layout);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = true;
                if(!isEmailVail(edtEmail.getText().toString())){
                   flag = false;
                    showToast("Email không hợp lệ ,vui lòng thử lại");
                }
                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    showToast("email không được trống!");
                }
                if (flag) {
                    try {
                        presenter.updateEmailStore(idStore, edtEmail.getText().toString());
                            showToast("Cập nhật Email khong thành công");
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void updateStoreName() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtStoreName = new EditText(getContext());
        edtStoreName.setHint("Enter your store name ");
        aler.setView(edtStoreName);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtStoreName.getText().toString())){
                    showToast("username không được trống!");
                }
                else {
                    presenter.updateStoreName(idStore, edtStoreName.getText().toString());
                    txtStoreName.setText(edtStoreName.getText());
                    showToast("Cập nhật Tên cửa hàng thành công!");
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void changeAvata() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_LOAD_IMAGE_AVATASTORE);
    }

    private void changeCover() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_LOAD_IMAGE_COVERSTORE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constain.REQUEST_CODE_LOAD_IMAGE_COVERSTORE && resultCode == getActivity().RESULT_OK ){
            if (data.getAction() != null){
                bitmapCover = (Bitmap) data.getExtras().get("data");
                presenter.updateCoverStore(bitmapCover, idStore);
                imgCover.setImageBitmap(bitmapCover);
            }
            else {
                Uri filePath = data.getData();
                try {
                    bitmapCover = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    presenter.updateCoverStore(bitmapCover, idStore);
                    imgCover.setImageBitmap(bitmapCover);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (requestCode == Constain.REQUEST_CODE_LOAD_IMAGE_AVATASTORE && resultCode == getActivity().RESULT_OK ){
            if (data.getAction() != null){
                bitmapAvata = (Bitmap) data.getExtras().get("data");
                bitmapAvata = cropImage(bitmapAvata);
                presenter.updateAvataStore(bitmapAvata, idStore);
                imgAvata.setImageBitmap(bitmapAvata);
            }
            else {
                Uri filePath = data.getData();
                try {
                    bitmapAvata = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    bitmapAvata = cropImage(bitmapAvata);
                    presenter.updateAvataStore(bitmapAvata, idStore);
                    imgAvata.setImageBitmap(bitmapAvata);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public Bitmap cropImage (Bitmap dstBmp) {
        Bitmap srcBmp = null;
        if (dstBmp.getWidth() >= dstBmp.getHeight()) {

            srcBmp = Bitmap.createBitmap(dstBmp, dstBmp.getWidth() / 2 - dstBmp.getHeight() / 2, 0, dstBmp.getHeight(), dstBmp.getHeight()
            );
        } else {
            srcBmp = Bitmap.createBitmap(dstBmp, 0, dstBmp.getHeight() / 2 - dstBmp.getWidth() / 2, dstBmp.getWidth(), dstBmp.getWidth()
            );
        }
        return  srcBmp;
    }
}
