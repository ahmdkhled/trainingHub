package com.example.traininghub.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

public class Course implements Parcelable {

    private int id;
    private String name;
    private String duration;
    private String price;
    private String description;
    private ArrayList<Content> content;
    private float rating;
    private ArrayList<Instructor> instructors;
    private Center center;
    private ArrayList<Media> media;


    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        duration = in.readString();
        price = in.readString();
        description = in.readString();
        content = in.createTypedArrayList(Content.CREATOR);
        rating = in.readFloat();
        instructors = in.createTypedArrayList(Instructor.CREATOR);
        center = in.readParcelable(Center.class.getClassLoader());
        media = in.createTypedArrayList(Media.CREATOR);
    }

    public Course(int id, String name, String price, float rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(duration);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeTypedList(content);
        dest.writeFloat(rating);
        dest.writeTypedList(instructors);
        dest.writeParcelable(center, flags);
        dest.writeTypedList(media);
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

    public static DiffUtil.ItemCallback<Course> DIFF_CALLBACK = new DiffUtil.ItemCallback<Course>(){

        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.id==newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.equals(newItem);
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

    public ArrayList<Content> getContent() {
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

    public ArrayList<Media> getMedia() {
        return media;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj==null) return false;
        if (!(obj instanceof Course))return false;
        Course course= (Course) obj;
        return course.id==this.id;
    }
}
