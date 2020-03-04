package com.example.traininghub.DataSource;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.traininghub.App;
import com.example.traininghub.Repo.GroupsRepo;
import com.example.traininghub.dagger.AppComponent;
import com.example.traininghub.dagger.DaggerAppComponent;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.Group;
import com.example.traininghub.models.NetworkState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GroupsDataSource extends PageKeyedDataSource<Integer, Group> {

    private static final String TAG = "GroupsDataSource";
    private MutableLiveData<NetworkState> networkState=new MutableLiveData<>();


    private String limit;
    private String category;
    private GroupsRepo groupsRepo;


    GroupsDataSource(String limit, String category) {
        this.limit = limit;
        this.category = category;
    }

    public GroupsDataSource(String limit, String category, GroupsRepo groupsRepo) {
        this.limit = limit;
        this.category = category;
        this.groupsRepo = groupsRepo;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Group> callback) {
        Log.d(TAG, "loadInitial: "+category);

        getGroups("1",limit,category,callback,null);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Group> callback) {
        Log.d(TAG, "loadBefore: ");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Group> callback) {
        Log.d(TAG, "loadAfter: "+params.key);

        getGroups(String.valueOf(params.key+1),limit,category,null,callback);

        //callback.onResult(courses,params.key+1);
    }

    private void getGroups(String page, String limit, String course,
                           LoadInitialCallback loadInitialCallback, LoadCallback<Integer, Group> loadCallback) {


        groupsRepo
                .getGroups(course,page,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ArrayList<Group>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ArrayList<Group>> response) {
                        networkState.postValue(new NetworkState(false,null,page));
                        ArrayList<Group> groups=response.body();
                        if (response.isSuccessful()&&groups!=null){
                            if (groups.isEmpty()&&page.equals("1")){
                                networkState.postValue(new NetworkState(false,"there is no courses"
                                        ,NetworkState.BACK,"back",page));
                                return;
                            }


                            if (loadInitialCallback!=null)
                                loadInitialCallback.onResult(groups,null,2);
                            if (loadCallback!=null)
                                loadCallback.onResult(groups, Integer.valueOf(page));


                        }else
                            networkState.postValue(new NetworkState(false,"Error loading Courses"
                                    ,NetworkState.BACK,"retry",page));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
