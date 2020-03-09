package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.App;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class DetailActivityVM extends AndroidViewModel {
    MutableLiveData<Boolean> isEnrolling=new MutableLiveData<>();
    public DetailActivityVM(@NonNull Application application) {
        super(application);
    }

    private static final String TAG = "DetailActivityVM";

    public MutableLiveData<ResponseBody> enrollToCourse(String student_id,String group_id){
        MutableLiveData<ResponseBody> enrollToCourse = new MutableLiveData<>();
        isEnrolling.setValue(true);
        ((App)getApplication()).getCoursesRepo()
                .enrollToCourse(student_id,group_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ResponseBody> response) {
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return enrollToCourse;

    }


}
