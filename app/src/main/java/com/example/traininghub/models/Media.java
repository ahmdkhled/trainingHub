package com.example.traininghub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Media implements Parcelable {

    private int id;
    private String url;


    protected Media(Parcel in) {
        id = in.readInt();
        url = in.readString();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(url);
    }
}
