package com.example.traininghub.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;
import com.example.traininghub.databinding.StudentCourseLayoutBinding;
import com.example.traininghub.models.StudentCourse;
import com.example.traininghub.models.StudentCourseRes;

public class StudentCoursesAdapter extends PagedListAdapter<StudentCourse, StudentCoursesAdapter.StudentCourseViewHolder> {


     public StudentCoursesAdapter() {
        super(StudentCourse.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public StudentCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StudentCourseLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
        , R.layout.student_course_layout,parent,false);
        return new StudentCourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCourseViewHolder holder, int position) {
        StudentCourse studentCourse =getItem(position);
        holder.binding.setStudentCourse(studentCourse);

    }

    static class StudentCourseViewHolder extends RecyclerView.ViewHolder{
        StudentCourseLayoutBinding binding;
        public StudentCourseViewHolder(@NonNull StudentCourseLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
