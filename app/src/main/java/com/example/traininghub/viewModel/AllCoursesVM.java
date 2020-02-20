package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.models.CoursesResponse;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AllCoursesVM extends AndroidViewModel {

    private MutableLiveData<CoursesResponse> courses;
    private MutableLiveData<Boolean> isCoursesLoading=new MutableLiveData<>();
    private MutableLiveData<String> coursesLoadingError=new MutableLiveData<>();

    public AllCoursesVM(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CoursesResponse> getCourses(String page,String limit,String category){
        isCoursesLoading.setValue(true);
        courses=new MutableLiveData<>();
        CoursesRepo
                .getInstance()
                .getCourses(page,limit,category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CoursesResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<CoursesResponse> response) {
                        CoursesResponse coursesResponse=response.body();
                        if (response.isSuccessful()&&coursesResponse!=null){
                            courses.setValue(coursesResponse);
                        }else
                            coursesLoadingError.setValue("Error Loading Courses");
                        isCoursesLoading.setValue(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        isCoursesLoading.setValue(false);
                        coursesLoadingError.setValue("Error Loading Courses");
                        Log.d("COURSESS", "onError: "+e.getMessage());
                    }
                });
        return courses;
    }

    public MutableLiveData<Boolean> getIsCoursesLoading() {
        return isCoursesLoading;
    }

    public MutableLiveData<String> getCoursesLoadingError() {
        return coursesLoadingError;
    }
}
