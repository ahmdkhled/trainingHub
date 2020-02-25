package com.example.traininghub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutCourse2Binding;
import com.example.traininghub.models.Course;
import com.example.traininghub.view.activities.DetailActivity;

public class CoursesPagedAdapter extends PagedListAdapter<Course, CoursesPagedAdapter.CourseHolder> {

    private Context context;


    public CoursesPagedAdapter(){
        super(Course.DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutCourse2Binding binding= DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.layout_course2,parent,false);
        View itemView=binding.getRoot();
        return new CourseHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course course=getItem(position);
        holder.binding.setCourse(course);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        LayoutCourse2Binding binding;
        CourseHolder(LayoutCourse2Binding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(view -> {
                Intent intent=new Intent(context, DetailActivity.class);
                //intent.putExtra(DetailActivity.EXTRA_COURSE,courses.get(getAdapterPosition()));
                context.startActivity(intent);
            });

        }


    }
}
