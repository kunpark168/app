package com.example.win81version2.orderdrink.profile_store.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.win81version2.orderdrink.main.view.MainStoreActivity;
import com.example.win81version2.orderdrink.product_list.view.ProductListFragment;
import com.example.win81version2.orderdrink.profile_store.view.Profile_Store_Fragment;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Win 8.1 Version 2 on 5/8/2017.
 */

public class UpdateStoreSubmitter {
    private DatabaseReference mData;
    private MainStoreActivity view;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;

    public UpdateStoreSubmitter(DatabaseReference mData, MainStoreActivity view, FirebaseAuth mAuth, StorageReference mStorage) {
        this.mData = mData;
        this.view = view;
        this.mAuth = mAuth;
        this.mStorage = mStorage;
    }

    public void updateStatusStore(final String idStore, final int isOpen) {
        mData.child(Constain.STORES).child(idStore).child(Constain.IS_OPEN).setValue(isOpen).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (isOpen == 0) {
                        view.showToast("Mở cửa");
                    } else if (isOpen == 1){
                        view.showToast("Đóng cửa");
                    }
                }

            }
        });
    }

    public void updateStoreName(String idStore, String storeName) {
        mData.child(Constain.STORES).child(idStore).child(Constain.STORE_NAME).setValue(storeName);
    }

    public void updatephoneNumberStore(String idStore, String phoneNumber) {
        mData.child(Constain.STORES).child(idStore).child(Constain.PHONENUMBER).setValue(phoneNumber);
    }

    public void updateAddressStore(String idStore, String addressStore) {
        mData.child(Constain.STORES).child(idStore).child(Constain.LOCATION).child(Constain.ADDRESS).setValue(addressStore);
    }

    public void updateEmailStore(final String idStore, final String emailStore) {
        mData.child(Constain.STORES).child(idStore).child(Constain.EMAIL).setValue(emailStore);
    }

    public void updatePasswordStore(String email, String password, final String newPassword) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                task.getResult().getUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(view, "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view, "Cập nhật mật khẩu không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void updateCoverStore(Bitmap bitmap, final String idStore) {
        StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(Constain.LINKCOVERSTORE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String linkCoverStore = String.valueOf(downloadUrl);
                if (!linkCoverStore.equals("")) {
                    updateLinkCoverStore(idStore, linkCoverStore);
                }
            }
        });
    }

    public void updateLinkCoverStore(String idStore, String linkCoverStore) {
        mData.child(Constain.STORES).child(idStore).child(Constain.LINKCOVERSTORE).setValue(linkCoverStore);
    }

    public void updateAvataStore(Bitmap bitmap, final String idStore) {
        StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(Constain.LINKPHOTOSTORE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String linkPhotoStore = String.valueOf(downloadUrl);
                if (!linkPhotoStore.equals("")) {
                    updateLinkPhotoStore(idStore, linkPhotoStore);
                }
            }
        });
    }

    public void updateLinkPhotoStore(String idStore, String linkPhotoStore) {
        mData.child(Constain.STORES).child(idStore).child(Constain.LINKPHOTOSTORE).setValue(linkPhotoStore);
    }
    public void updateSumOrderedStore (String idStore, int sumOrdered){
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_ORDERED).setValue(sumOrdered);
    }
    public void updateSumShippedStore (String idStore, int sumShipped){
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_SHIPPED).setValue(sumShipped);
    }
    public void updateSumProduct (String idStore, int sumProduct) {
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_PRODUCT).setValue(sumProduct);
    }

}
