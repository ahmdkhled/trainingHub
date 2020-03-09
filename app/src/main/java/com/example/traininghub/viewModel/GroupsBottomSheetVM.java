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
import com.example.traininghub.DataSource.GroupsDataFactory;
import com.example.traininghub.R;
import com.example.traininghub.helpers.APIErrorUtil;
import com.example.traininghub.models.APIResponse;
import com.example.traininghub.models.CourseEnrollRes;
import com.example.traininghub.models.Group;
import com.example.traininghub.models.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class GroupsBottomSheetVM extends AndroidViewModel {

    private GroupsDataFactory groupsDataFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Group>> groups;


    private static final String TAG = "GroupsBottomSheetVM";

    //course enrollment
    MutableLiveData<Boolean> isEnrolling=new MutableLiveData<>();
    MutableLiveData<String> enrollingError=new MutableLiveData<>();


    public GroupsBottomSheetVM(@NonNull Application application) {
        super(application);
    }

    public void init(int course, String limit){
        Executor executor= Executors.newFixedThreadPool(5);
        groupsDataFactory=new GroupsDataFactory(String.valueOf(course),limit,((App) getApplication()).getGroupsRepo());
        networkState= Transformations.switchMap(groupsDataFactory.getGroupDataSource(),
                (dataSource ->dataSource.getNetworkState())

        );
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10)
                        .build();

        groups =new LivePagedListBuilder(groupsDataFactory,pagedListConfig)
                .setFetchExecutor(executor)
                .build();

    }

    public void validate(){
        groupsDataFactory.getGroupDataSource().getValue().invalidate();
        groupsDataFactory.getGroupDataSource().getValue().getNetworkState().setValue(new NetworkState(true,null,null));
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Group>> getGroups() {
        return groups;
    }

    //----------course enrollment -------------
    public MutableLiveData<CourseEnrollRes> enrollToCourse(String student_id, String group_id){
        MutableLiveData<CourseEnrollRes> enrollToCourse = new MutableLiveData<>();
        isEnrolling.setValue(true);
        ((App)getApplication()).getCoursesRepo()
                .enrollToCourse(student_id,group_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CourseEnrollRes>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<CourseEnrollRes> response) {
                        CourseEnrollRes courseEnrollRes=response.body();
                        if (response.isSuccessful()&&courseEnrollRes!=null){
                            enrollToCourse.setValue(courseEnrollRes);
                            Log.d(TAG, "onSuccess: "+courseEnrollRes.getMessage());
                        }

                        else {
                            enrollingError.setValue(getApplication().getApplicationContext()
                                    .getString(R.string.error_enrolling_to_course));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                        enrollingError.setValue(getApplication().getApplicationContext()
                        .getString(R.string.error_enrolling_to_course));


                    }
                });
        return enrollToCourse;

    }

    public MutableLiveData<String> getEnrollingError() {
        return enrollingError;
    }
}
