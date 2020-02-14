package com.example.traininghub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutReviewBinding;
import com.example.traininghub.models.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private Context context;
    private ArrayList<Review> reviews;

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        if (reviews==null)reviews=new ArrayList<>();
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutReviewBinding binding= DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.layout_review,parent,false);
        return new ReviewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review=reviews.get(position);
        holder.binding.setReview(review);

    }

    @Override
    public int getItemCount() {
        return reviews==null?0:reviews.size();
    }

    public void addReviews(ArrayList<Review> reviews){

        this.reviews.addAll(reviews);
        notifyDataSetChanged();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        LayoutReviewBinding binding;
        public ReviewHolder(@NonNull LayoutReviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
