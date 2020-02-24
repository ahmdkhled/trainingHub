package com.example.traininghub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Center implements Parcelable {
    private int id;
    private String name;
    private String logo;
    private String bio;

    protected Center(Parcel in) {
        id = in.readInt();
        name = in.readString();
        logo = in.readString();
        bio = in.readString();
    }

    public static final Creator<Center> CREATOR = new Creator<Center>() {
        @Override
        public Center createFromParcel(Parcel in) {
            return new Center(in);
        }

        @Override
        public Center[] newArray(int size) {
            return new Center[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(logo);
        parcel.writeString(bio);
    }
}
