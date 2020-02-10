package com.example.traininghub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traininghub.databinding.LayoutCourseBinding;
import com.example.traininghub.view.activities.DetailActivity;
import com.example.traininghub.R;
import com.example.traininghub.models.Course;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseHolder> {

    private Context context;
    private ArrayList<Course> courses;

    public CoursesAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCourseBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_course,parent,false);
        return new CourseHolder(binding);
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

    @BindingAdapter("android:src")
    public static void showImage(ImageView imageView,String url){
        Glide
             .with(imageView.getContext())
             .load(url)
             .into(imageView);
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        LayoutCourseBinding binding;
        CourseHolder(LayoutCourseBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            itemView.setOnClickListener(view -> {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_COURSE,courses.get(getAdapterPosition()));
                context.startActivity(intent);
            });

        }


    }
}
