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
import com.example.traininghub.DataSource.StudentCoursesDataFactory;
import com.example.traininghub.models.NetworkState;
import com.example.traininghub.models.StudentCourse;
import com.example.traininghub.models.StudentCourseRes;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyCoursesVM extends AndroidViewModel {

    private static final String TAG = "MyCoursesVM";
    private StudentCoursesDataFactory studentCoursesDataFactory;
    private LiveData<NetworkState> networkState=new MutableLiveData<>();
    private LiveData<PagedList<StudentCourse>> studentCourses;

    public MyCoursesVM(@NonNull Application application) {
        super(application);
    }

    public void init(String student_id,String limit){
        Executor executor= Executors.newFixedThreadPool(5);
        studentCoursesDataFactory =new StudentCoursesDataFactory(student_id,limit,((App)getApplication()).getCoursesRepo());
        networkState= Transformations.switchMap(studentCoursesDataFactory.getStudentCoursesDataSource(),
                (dataSource ->dataSource.getNetworkState())

        );
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10)
                        .build();

        studentCourses =new LivePagedListBuilder(studentCoursesDataFactory,pagedListConfig)
                .setFetchExecutor(executor)
                .build();


        Log.d("GROUPSSS", "init VM: "+studentCourses.getValue());
    }

    public LiveData<PagedList<StudentCourse>> getStudentCourses() {
        return studentCourses;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public void invalidate() {
        studentCoursesDataFactory.getStudentCoursesDataSource().getValue()
                .invalidate();
    }
}