package com.example.win81version2.orderdrink.products.model;

import com.example.win81version2.orderdrink.utility.Constain;

import java.util.HashMap;

/**
 * Created by Win 8.1 Version 2 on 4/25/2017.
 */

public class Category {
    private String idategory;
    private String categoryName;

    public Category(String idategory, String categoryName) {
        this.idategory = idategory;
        this.categoryName = categoryName;
    }

    public HashMap<String, Object> putMap (){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_CATEGORY, idategory);
        myMap.put(Constain.CATEGORY_NAME, categoryName);
        return myMap;
    }

    public String getIdategory() {
        return idategory;
    }

    public void setIdategory(String idategory) {
        this.idategory = idategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
