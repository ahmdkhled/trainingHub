package com.example.traininghub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutCategoryBinding;
import com.example.traininghub.models.Category;
import com.example.traininghub.view.activities.AllCoursesActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private Context context;
    private ArrayList<Category> categories;


    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCategoryBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context)
                ,R.layout.layout_category,parent,false);
        return new CategoriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Category category=categories.get(position);
        holder.binding.setCategory(category);
//        Glide
//                .with(context)
//                .load(categories.get(position).getImage())
//                .into(holder.category_image);

    }

    @Override
    public int getItemCount() {
        return categories==null?0:categories.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder{
        LayoutCategoryBinding  binding;
        public CategoriesViewHolder(@NonNull LayoutCategoryBinding  binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot()
                    .setOnClickListener(view->{
                        Intent intent=new Intent(context, AllCoursesActivity.class);
                        context.startActivity(intent);
                    });
        }
    }
}
