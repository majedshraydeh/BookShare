package com.fikrabd.mehna_w_amal.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class DefaultData implements Parcelable {

    @SerializedName("id")
    private String id="";

    @SerializedName("title")
    private String title="";

    public DefaultData() {}

    protected DefaultData(Parcel in) {
        id = in.readString();
        title = in.readString();
    }

    public static final Creator<DefaultData> CREATOR = new Creator<DefaultData>() {
        @Override
        public DefaultData createFromParcel(Parcel in) {
            return new DefaultData(in);
        }

        @Override
        public DefaultData[] newArray(int size) {
            return new DefaultData[size];
        }
    };

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

    @Override
    public String toString() {
        return "DefaultData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
    }
}
