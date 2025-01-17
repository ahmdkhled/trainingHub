package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.adapters.ReviewsAdapter;
import com.example.traininghub.databinding.ActivityReviewsBinding;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.Review;
import com.example.traininghub.viewModel.ReviewsVM;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    ReviewsVM reviewsVM;
    ReviewsAdapter reviewsAdapter;
    ActivityReviewsBinding binding;
    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_reviews);

        reviewsVM= ViewModelProviders.of(this).get(ReviewsVM.class);
        reviewsAdapter=new ReviewsAdapter(this,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.reviewsRecycler.setAdapter(reviewsAdapter);
        binding.reviewsRecycler.setLayoutManager(layoutManager);
        binding.reviewsRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        course=getIntent().getParcelableExtra(DetailActivity.EXTRA_COURSE);

        observeReviews(String.valueOf(course.getId()),"1",null);


    }

    private void observeReviews(String course,String page,String limit){
        reviewsVM
                .getReviews(course,page,limit)
                .observe(this, reviews -> {
                    if (reviews.isEmpty()){
                        binding.emptyView.setVisibility(View.VISIBLE);
                        binding.emptyLayout.action.setOnClickListener(view -> {
                            finish();
                        });
                        return;
                    }
                    binding.emptyView.setVisibility(View.GONE);

                    reviewsAdapter.addReviews(reviews);
                });
        observeReviewsLoading(page);
        observeError(page);
    }
    private void observeReviewsLoading(String page){
        reviewsVM.getIsReviewsLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Log.d("REVIEEEEEWW", "onChanged: "+aBoolean);
                        if (aBoolean!=null&&aBoolean){
                            if (page.equals("1")){
                                binding.shimmer.setVisibility(View.VISIBLE);
                                binding.shimmer.startShimmer();

                            }
                        }else {
                            binding.shimmer.setVisibility(View.GONE);
                            binding.shimmer.stopShimmer();
                        }

                    }
                });
    }

    private void observeError(String page){
        reviewsVM
                .getReviewsLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        binding.emptyView.setVisibility(View.VISIBLE);
                        binding.emptyLayout.message.setText(s);
                        binding.emptyLayout.action.setText(R.string.retry);
                        Toast.makeText(ReviewsActivity.this, "error", Toast.LENGTH_SHORT).show();

                        binding.emptyLayout.message
                                .setOnClickListener(view->{
                                    if (page.equals("1"))observeReviews(String.valueOf(course.getId()),"1",null);
                                });
                        //todo handle retry button

                    }
                });
    }


}
