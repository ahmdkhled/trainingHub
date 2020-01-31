package com.example.traininghub.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traininghub.DetailActivity;
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
        View view=LayoutInflater.from(context).inflate(R.layout.layout_course,parent,false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course course=courses.get(position);
        holder.name.setText(course.getName());
        holder.price.setText(course.getPrice());
        holder.ratingBar.setRating(course.getRating());


    }

    @Override
    public int getItemCount() {
        return courses==null?0:courses.size();
    }

    class CourseHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.course_name)
        TextView name;
        @BindView(R.id.course_price)
        TextView price;
        @BindView(R.id.course_thumbnail)
        ImageView thumbnail;
        @BindView(R.id.course_rating)
        AppCompatRatingBar ratingBar;
        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(view -> {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_COURSE,courses.get(getAdapterPosition()));
                context.startActivity(intent);
            });

        }


    }
}
