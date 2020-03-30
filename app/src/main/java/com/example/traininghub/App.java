package com.example.traininghub;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.traininghub.Repo.CategoriesRepo;
import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.Repo.GroupsRepo;
import com.example.traininghub.Repo.ReviewsRepo;
import com.example.traininghub.Repo.StudentsRepo;
import com.example.traininghub.dagger.AppComponent;
import com.example.traininghub.Repo.LoginRepository;
import com.example.traininghub.dagger.DaggerAppComponent;
import com.example.traininghub.helpers.TokenManager;

public class App extends MultiDexApplication {
    private static final String TAG = "AppPPP";
    private TokenManager tokenManager;
    private LoginRepository loginRepository;
    private CategoriesRepo categoriesRepo;
    private CoursesRepo coursesRepo;
    private ReviewsRepo reviewsRepo;
    private GroupsRepo groupsRepo;
    private StudentsRepo studentsRepo;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        AppComponent appComponent = DaggerAppComponent.builder().context(getApplicationContext())
                                    .build();

        tokenManager = appComponent.getTokenManager();
        loginRepository = appComponent.getLoginRepository();
        categoriesRepo = appComponent.getCategoriesRepo();
        coursesRepo = appComponent.getCoursesRepo();
        reviewsRepo = appComponent.getReviewsRepo();
        groupsRepo=appComponent.getGroupsRepo();
        studentsRepo=appComponent.getStudentsRepo();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public TokenManager getTokenManager(){
        return tokenManager;
    }
    public LoginRepository getLoginRepository(){
        return loginRepository;
    }

    public CategoriesRepo getCategoriesRepo() {
        return categoriesRepo;
    }

    public CoursesRepo getCoursesRepo() {
        return coursesRepo;
    }

    public ReviewsRepo getReviewsRepo() {
        return reviewsRepo;
    }

    public GroupsRepo getGroupsRepo() {
        return groupsRepo;
    }

    public StudentsRepo getStudentsRepo() {
        return studentsRepo;
    }
}
