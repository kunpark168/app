package com.example.win81version2.orderdrink.search_user.model;

import com.example.win81version2.orderdrink.product.model.Product;

/**
 * Created by Win 8.1 Version 2 on 5/18/2017.
 */

public class SearchProduct  {
    private Product product;

    public SearchProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
