package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.view.fragments.AccountFragment;
import com.example.traininghub.view.fragments.MainFragment;
import com.example.traininghub.view.fragments.MyCoursesFragment;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivityVM extends ViewModel {
    private MainFragment mainFragment;
    private MyCoursesFragment myCoursesFragment;
    private AccountFragment accountFragment;

    private MutableLiveData<CoursesResponse> courses;
    private MutableLiveData<Boolean> isCoursesLoading=new MutableLiveData<>();
    private MutableLiveData<String> coursesLoadingError=new MutableLiveData<>();


    public MutableLiveData<CoursesResponse> getCourses(String limit){
        isCoursesLoading.setValue(true);
        courses=new MutableLiveData<>();
        CoursesRepo
                .getInstance()
                .getCourses(limit)
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
                        isCoursesLoading.setValue(true);

                    }

                    @Override
                    public void onError(Throwable e) {
                        isCoursesLoading.setValue(false);
                        coursesLoadingError.setValue("Error Loading Courses");
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

    public MainFragment getMainFragment() {
        return mainFragment==null?mainFragment=new MainFragment():mainFragment;
    }



    public MyCoursesFragment getMyCoursesFragment() {
        return myCoursesFragment==null?myCoursesFragment=new MyCoursesFragment():myCoursesFragment;
    }

    public void setMyCoursesFragment(MyCoursesFragment myCoursesFragment) {
        this.myCoursesFragment = myCoursesFragment;
    }

    public AccountFragment getAccountFragment() {
        return accountFragment==null?accountFragment=new AccountFragment():accountFragment;
    }

    public void setAccountFragment(AccountFragment accountFragment) {
        this.accountFragment = accountFragment;
    }
}
