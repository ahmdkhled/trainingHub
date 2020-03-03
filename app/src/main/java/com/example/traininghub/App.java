package com.example.traininghub;

import android.app.Application;

import com.example.traininghub.Repo.CategoriesRepo;
import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.Repo.GroupsRepo;
import com.example.traininghub.Repo.ReviewsRepo;
import com.example.traininghub.dagger.AppComponent;
import com.example.traininghub.Repo.LoginRepository;
import com.example.traininghub.dagger.DaggerAppComponent;
import com.example.traininghub.helpers.TokenManager;

public class App extends Application {

    private TokenManager tokenManager;
    private LoginRepository loginRepository;
    private CategoriesRepo categoriesRepo;
    private CoursesRepo coursesRepo;
    private ReviewsRepo reviewsRepo;
    private GroupsRepo groupsRepo;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder().context(getApplicationContext())
                                    .build();

        tokenManager = appComponent.getTokenManager();
        loginRepository = appComponent.getLoginRepository();
        categoriesRepo = appComponent.getCategoriesRepo();
        coursesRepo = appComponent.getCoursesRepo();
        reviewsRepo = appComponent.getReviewsRepo();
        groupsRepo=appComponent.getGroupsRepo();

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
}
