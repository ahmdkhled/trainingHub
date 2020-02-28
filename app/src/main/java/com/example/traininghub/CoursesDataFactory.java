package com.example.traininghub;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class CoursesDataFactory extends DataSource.Factory {

    private CoursesDataSource dataSource;
    private MutableLiveData<CoursesDataSource> coursesDataSource;
    private String limit;
    private String category;

    public CoursesDataFactory() {
        this.coursesDataSource = new MutableLiveData<>();
    }

    public CoursesDataFactory(String limit, String category) {
        this.limit = limit;
        this.category = category;
        this.coursesDataSource = new MutableLiveData<>();

    }

    public void invalidate(){
        dataSource.invalidate();
    }

    @NonNull
    @Override
    public DataSource create() {
        dataSource=new CoursesDataSource(limit,category);
        coursesDataSource.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<CoursesDataSource> getCoursesDataSource() {
        return coursesDataSource;
    }
}
