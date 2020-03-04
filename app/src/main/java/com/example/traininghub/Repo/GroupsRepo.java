package com.example.traininghub.Repo;

import android.util.Log;

import com.example.traininghub.models.Group;
import com.example.traininghub.retrofit.RetrofitClient;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class GroupsRepo {
    private static final String TAG = "GroupsRepo";

    private RetrofitClient retrofitClient;
    private static GroupsRepo groupsRepo;

    @Inject
    public GroupsRepo(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        Log.d(TAG, "GroupsRepo: ");
    }

    public static GroupsRepo getInstance() {
        return groupsRepo==null?groupsRepo=new GroupsRepo():groupsRepo;
    }

    private GroupsRepo(){

    }


    public Single<Response<ArrayList<Group>>> getGroups (String course, String page, String limit){
        return retrofitClient
                .getApiService()
                .getGroups(course,page,limit);
    }

}
