package com.example.win81version2.orderdrink.search_user.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Win 8.1 Version 2 on 5/6/2017.
 */

public class SearchStore implements Parcelable {
    String linkPhoto;
    String name;
    int sumProduct;

    public SearchStore(){
        super();
    }

    public SearchStore(String linkPhoto, String name, int sumProduct) {
        this.linkPhoto = linkPhoto;
        this.name = name;
        this.sumProduct = sumProduct;
    }

    public int getSumProduct() {
        return sumProduct;
    }

    public void setSumProduct(int sumProduct) {
        this.sumProduct = sumProduct;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Parceble
    public SearchStore(Parcel parcel) {
        this.linkPhoto = parcel.readString();
        this.name = parcel.readString();
        this.sumProduct = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(linkPhoto);
        dest.writeString(name);
        dest.writeInt(sumProduct);

    }

    public static final Creator<SearchStore> CREATOR = new Creator<SearchStore>() {
        @Override
        public SearchStore createFromParcel(Parcel source) {
            return new SearchStore(source);
        }

        @Override
        public SearchStore[] newArray(int size) {
            return new SearchStore[size];
        }
    };
}
