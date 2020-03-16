package com.example.traininghub.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class StudentCourse {

    private Course course;
    private Group group;


    public Course getCourse() {
        return course;
    }

    public Group getGroup() {
        return group;
    }

    public static DiffUtil.ItemCallback<StudentCourse> DIFF_CALLBACK=new DiffUtil.ItemCallback<StudentCourse>() {
        @Override
        public boolean areItemsTheSame(@NonNull StudentCourse oldItem, @NonNull StudentCourse newItem) {
            return oldItem.course.getId()==newItem.course.getId()&&oldItem.group.getId()==newItem.group.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StudentCourse oldItem, @NonNull StudentCourse newItem) {
            return oldItem.equals(newItem);
        }
    };



    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj==null) return false;
        if (!(obj instanceof StudentCourseRes))return false;
        StudentCourse studentCourse = (StudentCourse) obj;

        return studentCourse.getCourse().getId()==course.getId()
                && studentCourse.getGroup().getId()==group.getId();
    }
}
