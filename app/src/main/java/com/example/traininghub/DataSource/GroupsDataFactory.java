package com.example.traininghub.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class GroupsDataFactory extends DataSource.Factory {

    private String course;
    private String limit;
    private MutableLiveData<GroupsDataSource> groupDataSource=new MutableLiveData<>();

    public GroupsDataFactory(String course, String limit) {
        this.course = course;
        this.limit = limit;
    }



    @NonNull
    @Override
    public DataSource create() {
        GroupsDataSource dataSource= new GroupsDataSource(limit,course);
        groupDataSource.postValue(dataSource);
        return dataSource;

    }

    public MutableLiveData<GroupsDataSource> getGroupDataSource() {
        return groupDataSource;
    }
}
