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
    private Center center;


    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        duration = in.readString();
        price = in.readString();
        description = in.readString();
        content = in.readString();
        rating = in.readFloat();
        instructors = in.createTypedArrayList(Instructor.CREATOR);
        center = in.readParcelable(Center.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(duration);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeFloat(rating);
        dest.writeTypedList(instructors);
        dest.writeParcelable(center, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Center getCenter() {
        return center;
    }
}
