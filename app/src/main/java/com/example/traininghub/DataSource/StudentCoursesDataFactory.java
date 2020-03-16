package com.example.traininghub.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.Repo.GroupsRepo;

public class StudentCoursesDataFactory extends DataSource.Factory {

    private String student_id;
    private String limit;
    private MutableLiveData<StudentCoursesDataSource> studentCoursesDataSource=new MutableLiveData<>();
    private CoursesRepo coursesRepo;



    public StudentCoursesDataFactory(String student_id,String limit, CoursesRepo coursesRepo) {
        this.student_id=student_id;
        this.limit = limit;
        this.coursesRepo=coursesRepo;

    }

    @NonNull
    @Override
    public DataSource create() {
        StudentCoursesDataSource dataSource= new StudentCoursesDataSource(student_id,limit,coursesRepo);
        studentCoursesDataSource.postValue(dataSource);
        return dataSource;

    }

    public MutableLiveData<StudentCoursesDataSource> getStudentCoursesDataSource() {
        return studentCoursesDataSource;
    }
}
