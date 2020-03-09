package com.example.traininghub.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.traininghub.Repo.CoursesRepo;

public class CoursesDataFactory extends DataSource.Factory {

    private CoursesDataSource dataSource;
    private MutableLiveData<CoursesDataSource> coursesDataSource;
    private String limit;
    private String category;
    private CoursesRepo coursesRepo;


    public CoursesDataFactory(String limit, String category, CoursesRepo coursesRepo) {
        this.limit = limit;
        this.category = category;
        this.coursesRepo=coursesRepo;
        this.coursesDataSource = new MutableLiveData<>();

    }

    public void invalidate(){
        dataSource.invalidate();
    }

    @NonNull
    @Override
    public DataSource create() {
        dataSource=new CoursesDataSource(limit,category,coursesRepo);
        coursesDataSource.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<CoursesDataSource> getCoursesDataSource() {
        return coursesDataSource;
    }
}
