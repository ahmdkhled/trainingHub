package com.example.traininghub.dagger;


import android.content.Context;

import com.example.traininghub.Repo.CategoriesRepo;
import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.Repo.GroupsRepo;
import com.example.traininghub.Repo.LoginRepository;
import com.example.traininghub.Repo.ReviewsRepo;
import com.example.traininghub.helpers.TokenManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component
public interface AppComponent {
    TokenManager getTokenManager();
    LoginRepository getLoginRepository();
    CategoriesRepo getCategoriesRepo();
    CoursesRepo getCoursesRepo();
    ReviewsRepo getReviewsRepo();
    GroupsRepo getGroupsRepo();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }
}
