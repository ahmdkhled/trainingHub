package com.example.traininghub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.traininghub.Repo.CategoriesRepo;
import com.example.traininghub.Repo.CoursesRepo;
import com.example.traininghub.models.CategoriesResponse;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.view.fragments.AccountFragment;
import com.example.traininghub.view.fragments.MainFragment;
import com.example.traininghub.view.fragments.MyCoursesFragment;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivityVM extends ViewModel {
    private MainFragment mainFragment;
    private MyCoursesFragment myCoursesFragment;
    private AccountFragment accountFragment;

    private MutableLiveData<CoursesResponse> courses;
    private MutableLiveData<Boolean> isCoursesLoading=new MutableLiveData<>();
    private MutableLiveData<String> coursesLoadingError=new MutableLiveData<>();

private MutableLiveData<CategoriesResponse> categories;
    private MutableLiveData<Boolean> isCategoriesLoading=new MutableLiveData<>();
    private MutableLiveData<String> categoriesLoadingError=new MutableLiveData<>();


    public MutableLiveData<CoursesResponse> getCourses(String page,String limit){
        isCoursesLoading.setValue(true);
        courses=new MutableLiveData<>();
        CoursesRepo
                .getInstance()
                .getCourses(page,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CoursesResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<CoursesResponse> response) {
                        CoursesResponse coursesResponse=response.body();
                        if (response.isSuccessful()&&coursesResponse!=null){
                            courses.setValue(coursesResponse);
                        }else
                            coursesLoadingError.setValue("Error Loading Courses");
                        isCoursesLoading.setValue(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        isCoursesLoading.setValue(false);
                        coursesLoadingError.setValue("Error Loading Courses");
                        Log.d("COURSESS", "onError: "+e.getMessage());
                    }
                });
        return courses;
    }


    public MutableLiveData<Boolean> getIsCoursesLoading() {
        return isCoursesLoading;
    }

    public MutableLiveData<String> getCoursesLoadingError() {
        return coursesLoadingError;
    }

    //_________________________categories_________________________________
    public MutableLiveData<CategoriesResponse> getCategories(String page, String limit){
        isCategoriesLoading.setValue(true);
        categories=new MutableLiveData<>();
        CategoriesRepo
                .getInstance()
                .getCategories(page,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoriesResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<CategoriesResponse> response) {
                        CategoriesResponse categoriesResponse=response.body();
                        if (categoriesResponse!=null&&response.isSuccessful()){
                            categories.setValue(categoriesResponse);
                        }else {
                            categoriesLoadingError.setValue("error loading categories");
                            Log.d("CATEGORRIIS", "onError >>>>>>>>>: ");

                        }
                        isCategoriesLoading.setValue(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("CATEGORRIIS", "onError: "+e.getMessage());
                        categoriesLoadingError.setValue("error loading categories");
                        isCategoriesLoading.setValue(false);
                    }
                });

        return categories;
    }

    public MutableLiveData<Boolean> getIsCategoriesLoading() {
        return isCategoriesLoading;
    }

    public MutableLiveData<String> getCategoriesLoadingError() {
        return categoriesLoadingError;
    }

    public MainFragment getMainFragment() {
        return mainFragment==null?mainFragment=new MainFragment():mainFragment;
    }



    public MyCoursesFragment getMyCoursesFragment() {
        return myCoursesFragment==null?myCoursesFragment=new MyCoursesFragment():myCoursesFragment;
    }

    public void setMyCoursesFragment(MyCoursesFragment myCoursesFragment) {
        this.myCoursesFragment = myCoursesFragment;
    }

    public AccountFragment getAccountFragment() {
        return accountFragment==null?accountFragment=new AccountFragment():accountFragment;
    }

    public void setAccountFragment(AccountFragment accountFragment) {
        this.accountFragment = accountFragment;
    }
}
