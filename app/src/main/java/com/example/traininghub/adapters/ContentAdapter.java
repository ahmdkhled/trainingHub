package com.example.traininghub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutCourseContentBinding;
import com.example.traininghub.models.Content;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

    private ArrayList<Content> content;

    public ContentAdapter(ArrayList<Content> content) {
        this.content = content;
    }

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCourseContentBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_course_content,parent,false);

        return new ContentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        Content c=content.get(position);
        holder.binding.setContent(c);
    }

    @Override
    public int getItemCount() {
        return content==null?0:content.size();
    }

    class ContentHolder extends RecyclerView.ViewHolder{

        LayoutCourseContentBinding binding;
        public ContentHolder(@NonNull LayoutCourseContentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.desc.getVisibility()==View.VISIBLE)
                        binding.desc.setVisibility(View.GONE);
                    else
                        binding.desc.setVisibility(View.VISIBLE);
                }
            });

        }
    }
}
