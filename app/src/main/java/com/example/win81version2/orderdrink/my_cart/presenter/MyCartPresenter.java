package com.example.win81version2.orderdrink.my_cart.presenter;

import com.example.win81version2.orderdrink.my_cart.model.MyCart;
import com.example.win81version2.orderdrink.my_cart.model.MyCartSubmitter;
import com.example.win81version2.orderdrink.utility.Constain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Win 8.1 Version 2 on 5/12/2017.
 */

public class MyCartPresenter {
    private DatabaseReference mData;
    private MyCartSubmitter submitter;

    public MyCartPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new MyCartSubmitter(mData);
    }
    public void addProductToCart (String idUser, String idMyCart, MyCart myCart){
       submitter.addProductToCart(idUser, idMyCart, myCart);
    }
    public void deleteProductOrder (String idUser, String idMyCart){
        submitter.deleteProductOrder(idUser, idMyCart);
       }
    public void updateCountProduct (String idUser, String idMyCart, int count){
        submitter.updateCountProduct(idUser, idMyCart, count);
    }
    public void updatePrice (String idUser, String idMyCart, float price){
        submitter.updatePrice(idUser, idMyCart, price);}
}
