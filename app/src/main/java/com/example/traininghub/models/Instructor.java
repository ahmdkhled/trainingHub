package com.example.traininghub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Instructor implements Parcelable {

    private int id;
    private String nameAr;
    private String nameEn;
    private String bio;
    private String image;

    protected Instructor(Parcel in) {
        id = in.readInt();
        nameAr = in.readString();
        nameEn = in.readString();
        bio = in.readString();
        image = in.readString();
    }

    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
        @Override
        public Instructor createFromParcel(Parcel in) {
            return new Instructor(in);
        }

        @Override
        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nameAr);
        parcel.writeString(nameEn);
        parcel.writeString(bio);
        parcel.writeString(image);
    }
}
