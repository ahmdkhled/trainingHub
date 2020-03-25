package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.traininghub.App;
import com.example.traininghub.DataSource.CoursesDataFactory;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.models.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        coursesDataFactory=new CoursesDataFactory(limit,category,((App)getApplication()).getCoursesRepo());
        networkState=Transformations.switchMap(coursesDataFactory.getCoursesDataSource(),
                (dataSource ->dataSource.getNetworkState() )

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

        Log.d("CoursesDataSource", "init: "+coursesPagedList.getValue());

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
