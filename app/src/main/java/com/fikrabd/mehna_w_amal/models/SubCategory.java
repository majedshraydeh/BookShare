package com.fikrabd.mehna_w_amal.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class SubCategory  {

    @SerializedName("id")
    private String id="";

    @SerializedName("title")
    private String title="";

    @SerializedName("price")
    private String price="";

    @SerializedName("currency")
    private String currency="";

    public SubCategory() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
