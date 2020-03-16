package com.example.traininghub.DataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.models.NetworkState;
import com.example.traininghub.models.StudentCourse;
import com.example.traininghub.models.StudentCourseRes;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public  class StudentCoursesDataSource extends PageKeyedDataSource <Integer, StudentCourseRes>{

    public static final String TAG= StudentCoursesDataSource.class.getSimpleName();
    private MutableLiveData<NetworkState> networkState=new MutableLiveData<>();


    private String student_id;
    private String limit;
    private CoursesRepo coursesRepo;



    StudentCoursesDataSource(String student_id,String limit, CoursesRepo coursesRepo) {
        this.student_id=student_id;
        this.limit = limit;
        this.coursesRepo=coursesRepo;

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, StudentCourseRes> callback) {
        getStudentCourses(student_id,"1",limit,callback,null);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, StudentCourseRes> callback) {
        Log.d(TAG, "loadBefore: ");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, StudentCourseRes> callback) {

        //Integer page= params.key;
            Log.d(TAG, " > "+params.key);
            int page=Integer.parseInt(String.valueOf(params.key));


        try {
            Integer x=params.key;

        }catch (Exception e){
            Log.d(TAG, "loadAfter: "+e.getMessage());
        }
        getStudentCourses(
                student_id,
                String.valueOf(page+1)
                ,limit
                ,null,callback);

        //callback.onResult(courses,params.key+1);
    }

    private void getStudentCourses(String student_id,String page,String limit
            ,LoadInitialCallback loadInitialCallback,LoadCallback loadCallback){
        networkState.postValue(new NetworkState(true,null,page));


                coursesRepo
                .getStudentCourse(student_id,page,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StudentCourseRes>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<StudentCourseRes> response) {
                        networkState.postValue(new NetworkState(false,null,page));
                        StudentCourseRes studentCoursRes =response.body();
                        if (response.isSuccessful()&& studentCoursRes !=null){
                            Log.d("GROUPSSS", "onSuccess: "+ studentCoursRes.getStudentCourses().size());
                            if (studentCoursRes.getStudentCourses().isEmpty()&&page.equals("1")){
                                networkState.postValue(new NetworkState(false,"there is no courses"
                                        ,NetworkState.BACK,"back",page));
                                return;
                            }


                            if (loadInitialCallback!=null)
                                loadInitialCallback.onResult(studentCoursRes.getStudentCourses(),null,2);
                            if (loadCallback!=null)
                                loadCallback.onResult(studentCoursRes.getStudentCourses(),page);


                        }else{
                            networkState.postValue(new NetworkState(false,"Error loading Courses"
                                    ,NetworkState.RETRY,"retry",page));
                            try {
                                Log.d("GROUPSSS","erooooooooor "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        networkState.postValue(new NetworkState(false,"Error loading Courses"
                                ,NetworkState.RETRY,"retry",page));

                        Log.d("COURSESS", "onError: "+e.getMessage());
                    }
                });

    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
