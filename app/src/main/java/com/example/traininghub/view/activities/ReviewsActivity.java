package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.traininghub.R;
import com.example.traininghub.adapters.ReviewsAdapter;
import com.example.traininghub.databinding.ActivityReviewsBinding;
import com.example.traininghub.models.Review;
import com.example.traininghub.viewModel.ReviewsVM;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    ReviewsVM reviewsVM;
    ReviewsAdapter reviewsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        ActivityReviewsBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_reviews);

        reviewsVM= ViewModelProviders.of(this).get(ReviewsVM.class);
        reviewsAdapter=new ReviewsAdapter(this,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.reviewsRecycler.setAdapter(reviewsAdapter);
        binding.reviewsRecycler.setLayoutManager(layoutManager);

        observeReviews("1",null);


    }

    private void observeReviews(String page,String limit){
        reviewsVM
                .getReviews(page,limit)
                .observe(this, reviews -> {
                    reviewsAdapter.addReviews(reviews);
                });
    }


}
