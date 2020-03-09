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
import com.example.traininghub.models.Group;
import com.example.traininghub.models.NetworkState;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class GroupsBottomSheetVM extends AndroidViewModel {

    private GroupsDataFactory groupsDataFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Group>> groups;


    private static final String TAG = "GroupsBottomSheetVM";

    //course enrollment
    MutableLiveData<Boolean> isEnrolling=new MutableLiveData<>();


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
    public MutableLiveData<ResponseBody> enrollToCourse(String student_id, String group_id){
        MutableLiveData<ResponseBody> enrollToCourse = new MutableLiveData<>();
        isEnrolling.setValue(true);
        ((App)getApplication()).getCoursesRepo()
                .enrollToCourse(student_id,group_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ResponseBody> response) {
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return enrollToCourse;

    }

}
