package com.example.traininghub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseHolder> {

    private Context context;

    public CoursesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_course,parent,false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class CourseHolder extends RecyclerView.ViewHolder{

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
