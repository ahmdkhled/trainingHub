package com.example.traininghub;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.dagger.AppComponent;
import com.example.traininghub.dagger.DaggerAppComponent;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.models.NetworkState;

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
    private MutableLiveData<NetworkState> networkState=new MutableLiveData<>();


    private String limit;
    private String category;

    CoursesDataSource(String limit, String category) {
        this.limit = limit;
        this.category = category;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Course> callback) {
        Log.d(TAG, "loadInitial: "+category);
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
        networkState.postValue(new NetworkState(true,null,page));

        AppComponent appComponent=DaggerAppComponent.builder()
                .build();

        appComponent.getCoursesRepo()
                .getCourses(page,limit,category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CoursesResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<CoursesResponse> response) {
                        networkState.postValue(new NetworkState(false,null,page));
                        CoursesResponse coursesResponse=response.body();
                        if (response.isSuccessful()&&coursesResponse!=null&&coursesResponse.getCourses()!=null){
                            if (coursesResponse.getCourses().isEmpty()&&page.equals("1")){
                                networkState.postValue(new NetworkState(false,"there is no courses"
                                        ,NetworkState.BACK,"back",page));
                                return;
                            }


                            if (loadInitialCallback!=null)
                                loadInitialCallback.onResult(coursesResponse.getCourses(),null,2);
                            if (loadCallback!=null)
                                loadCallback.onResult(coursesResponse.getCourses(),page);


                        }else
                            networkState.postValue(new NetworkState(false,"Error loading Courses"
                                    ,NetworkState.BACK,"retry",page));


                    }

                    @Override
                    public void onError(Throwable e) {
                        networkState.postValue(new NetworkState(false,"Error loading Courses"
                                ,NetworkState.BACK,"retry",page));

                        Log.d("COURSESS", "onError: "+e.getMessage());
                    }
                });

    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
