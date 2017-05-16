package com.example.win81version2.orderdrink.search_user.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Win 8.1 Version 2 on 5/6/2017.
 */

public class Search implements Parcelable {
    String linkPhoto;
    String name;
    double lo;
    double la;

    public Search(){
        super();
    }

    public Search(String linkPhoto, String name, double lo, double la) {
        this.linkPhoto = linkPhoto;
        this.name = name;
        this.lo = lo;
        this.la = la;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public double getLa() {
        return la;
    }

    public void setLa(double la) {
        this.la = la;
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
    public Search(Parcel parcel) {
        this.linkPhoto = parcel.readString();
        this.name = parcel.readString();
        this.lo = parcel.readDouble();
        this.la = parcel.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(linkPhoto);
        dest.writeString(name);
        dest.writeDouble(lo);
        dest.writeDouble(la);

    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel source) {
            return new Search(source);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
