package com.example.traininghub.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.traininghub.Repo.GroupsRepo;

public class GroupsDataFactory extends DataSource.Factory {

    private String course;
    private String limit;
    private MutableLiveData<GroupsDataSource> groupDataSource=new MutableLiveData<>();
    private GroupsRepo groupsRepo;



    public GroupsDataFactory(String course, String limit, GroupsRepo groupsRepo) {
        this.course = course;
        this.limit = limit;
        this.groupsRepo = groupsRepo;
    }

    @NonNull
    @Override
    public DataSource create() {
        GroupsDataSource dataSource= new GroupsDataSource(limit,course,groupsRepo);
        groupDataSource.postValue(dataSource);
        return dataSource;

    }

    public MutableLiveData<GroupsDataSource> getGroupDataSource() {
        return groupDataSource;
    }
}
