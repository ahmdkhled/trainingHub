package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.traininghub.CoursesDataFactory;
import com.example.traininghub.CoursesDataSource;
import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.models.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AllCoursesVM extends AndroidViewModel {

    private MutableLiveData<CoursesResponse> courses;
    private MutableLiveData<Boolean> isCoursesLoading=new MutableLiveData<>();
    private MutableLiveData<String> coursesLoadingError=new MutableLiveData<>();
    private LiveData<NetworkState> networkState=new MutableLiveData<>();

    private CoursesDataFactory coursesDataFactory;

    public AllCoursesVM(@NonNull Application application) {
        super(application);
        init();
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


    private LiveData<PagedList<Course>> coursesPagedList;
    public void init(){
        Executor executor= Executors.newFixedThreadPool(5);
        coursesDataFactory=new CoursesDataFactory(null,null);
        networkState=Transformations.switchMap(coursesDataFactory.getCoursesDataSource(),
                (dataSource ->dataSource.getNetworkState())

        );




        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10)
                        .build();

        coursesPagedList=new LivePagedListBuilder(coursesDataFactory,pagedListConfig)
                .setFetchExecutor(executor)
                .build();

    }

    public void invalidate(){
        coursesDataFactory.getCoursesDataSource().getValue().invalidate();

    }

    public LiveData<PagedList<Course>> getCoursesPagedList() {
        return coursesPagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
