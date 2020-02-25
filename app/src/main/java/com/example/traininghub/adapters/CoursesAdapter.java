package com.example.traininghub.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutCourse2Binding;
import com.example.traininghub.databinding.LayoutCourseBinding;
import com.example.traininghub.models.Course;
import com.example.traininghub.view.activities.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseHolder> {

    private Context context;
    private ArrayList<Course> courses;
    private boolean isMain;

    public CoursesAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        if (courses==null) courses=new ArrayList<>();
        this.courses = courses;
    }

    public CoursesAdapter(Context context, ArrayList<Course> courses, boolean isMain) {
        this.context = context;
        if (courses==null) courses=new ArrayList<>();
        this.courses = courses;
        this.isMain = isMain;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCourse2Binding binding= DataBindingUtil
                .inflate(LayoutInflater.from(context),R.layout.layout_course2,parent,false);
        View itemView=binding.getRoot();
        if (isMain){
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(600, RecyclerView.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,5,5,5);
            itemView.setLayoutParams(params);
        }

        return new CourseHolder(binding,itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course course=courses.get(position);
        holder.binding.setCourse(course);

    }

    @Override
    public int getItemCount() {
        return courses==null?0:courses.size();
    }

    public void addCourses(ArrayList<Course> courses){
        this.courses.addAll(courses);
        notifyDataSetChanged();
    }
    public void addCourses(List<Course> courses){
        this.courses.addAll(courses);
        notifyDataSetChanged();
    }

    @BindingAdapter("android:src")
    public static void bindImage(ImageView imageView,String url){
        Log.d("BINDDDD", "bindImage: ");
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.bg)
                .into(imageView);
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        LayoutCourse2Binding binding;
        CourseHolder(LayoutCourse2Binding binding,View itemView) {
            super(itemView);
            this.binding=binding;
            itemView.setOnClickListener(view -> {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_COURSE,courses.get(getAdapterPosition()));
                context.startActivity(intent);
            });

        }


    }
}
