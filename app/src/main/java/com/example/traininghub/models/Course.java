package com.example.traininghub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Course implements Parcelable {

    private int id;
    private String name;
    private String duration;
    private String price;
    private String description;
    private String content;
    private float rating;
    private ArrayList<Instructor> instructors;


    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        duration = in.readString();
        price = in.readString();
        description = in.readString();
        content = in.readString();
        rating = in.readFloat();
        instructors=in.readArrayList(this.getClass().getClassLoader());
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public float getRating() {
        return rating;
    }

    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(duration);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeString(content);
        parcel.writeFloat(rating);
        parcel.writeList(instructors);
    }
}
