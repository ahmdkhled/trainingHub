package com.example.traininghub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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

public class GroupsBottomSheetVM extends AndroidViewModel {

    private GroupsDataFactory groupsDataFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Group>> groups;

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
}
