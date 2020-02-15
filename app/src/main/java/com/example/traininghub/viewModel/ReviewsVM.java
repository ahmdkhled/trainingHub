package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.R;
import com.example.traininghub.Repo.ReviewsRepo;
import com.example.traininghub.models.Review;
import com.example.traininghub.models.ReviewsResponse;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class ReviewsVM extends AndroidViewModel {

    private MutableLiveData<ArrayList<Review>> reviews;
    private MutableLiveData<Boolean> isReviewsLoading=new MutableLiveData<>();
    private MutableLiveData<String> reviewsLoadingError=new MutableLiveData<>();

    public ReviewsVM(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Review>> getReviews(String course,String page,String limit){
        reviews=new MutableLiveData<>();
        ReviewsRepo
                .getInstance()
                .getReviews(course,page,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ReviewsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ReviewsResponse> response) {
                        ReviewsResponse reviewsResponse=response.body();
                        if (response.isSuccessful()&&reviewsResponse!=null){
                            ArrayList<Review> reviewsList=reviewsResponse.getReviews();
                            reviews.setValue(reviewsList);

                        }else {
                            reviewsLoadingError.setValue(
                                    getApplication().getApplicationContext()
                                    .getString(R.string.server_error)
                            );
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("REVIEWWW", "onError: "+e.getMessage());
                        reviewsLoadingError.setValue(
                                getApplication().getApplicationContext()
                                        .getString(R.string.server_error)
                        );
                    }
                });
        return reviews;

    }

    public MutableLiveData<Boolean> getIsReviewsLoading() {
        return isReviewsLoading;
    }

    public MutableLiveData<String> getReviewsLoadingError() {
        return reviewsLoadingError;
    }
}
