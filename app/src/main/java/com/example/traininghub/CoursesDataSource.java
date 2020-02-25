package com.example.traininghub;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CoursesDataSource extends PageKeyedDataSource <Integer, Course>{

    public static final String TAG= CoursesDataSource.class.getSimpleName();
    private MutableLiveData<Boolean> isCoursesLoading=new MutableLiveData<>();
    private MutableLiveData<String> coursesLoadingError=new MutableLiveData<>();

    String limit;
    String category;

    public CoursesDataSource(String limit, String category) {
        this.limit = limit;
        this.category = category;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Course> callback) {
        Log.d(TAG, "loadInitial: ");
        List<Course> courses=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Course course=new Course(i,"course "+i,"100",5);
            courses.add(course);
        }
        getCourses("1",limit,category,callback,null);
        //callback.onResult(courses,0,2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Course> callback) {
        Log.d(TAG, "loadBefore: ");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Course> callback) {
        Log.d(TAG, "loadAfter: "+params.key);
        List<Course> courses=new ArrayList<>();
        for (int i = params.key*10; i < params.key*10+10; i++) {
            Course course=new Course(i,"course "+i,"100",5);
            courses.add(course);
        }
        getCourses(String.valueOf(params.key+1),limit,category,null,callback);

        //callback.onResult(courses,params.key+1);
    }

    private void getCourses(String page,String limit,String category
            ,LoadInitialCallback loadInitialCallback,LoadCallback loadCallback){
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
                            if (loadInitialCallback!=null)
                                loadInitialCallback.onResult(coursesResponse.getCourses(),0,2);
                            if (loadCallback!=null)
                                loadCallback.onResult(coursesResponse.getCourses(),page);


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

    }
}
