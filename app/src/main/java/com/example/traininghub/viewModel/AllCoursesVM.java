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
    private LiveData<PagedList<Course>> coursesPagedList;


    public AllCoursesVM(@NonNull Application application) {
        super(application);
    }



    public void init(String limit,String category){
        Executor executor= Executors.newFixedThreadPool(5);
        coursesDataFactory=new CoursesDataFactory(limit,category);
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
