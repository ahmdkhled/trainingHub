package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.App;
import com.example.traininghub.R;
import com.example.traininghub.helpers.APIErrorUtil;
import com.example.traininghub.models.APIResponse;
import com.example.traininghub.models.NetworkState;
import com.example.traininghub.models.Student;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ProfileActivityVM extends AndroidViewModel {

    private static final String TAG = "ProfileActivityVM";
    MutableLiveData<NetworkState> networkState=new MutableLiveData<>();
    public ProfileActivityVM(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Student> registerStudent(RequestBody nameAr, RequestBody nameEn,
                                                    RequestBody email, RequestBody phoneNumber,
                                                    RequestBody phoneNumberSec, RequestBody state,
                                                    RequestBody city, RequestBody address,
                                                    RequestBody faculty, RequestBody degree,
                                                    RequestBody idNumber, RequestBody passportNumber, RequestBody skillCardNumber,
                                                    MultipartBody.Part idImage, MultipartBody.Part  image){
        MutableLiveData<Student> registerStudent=new MutableLiveData<>();
        networkState.setValue(new NetworkState(true,null));

        ((App)getApplication()).getStudentsRepo()
                .registerStudent(nameAr,
                        nameEn, email, phoneNumber, phoneNumberSec,
                        state, city, address, faculty, degree,
                        idNumber, passportNumber, skillCardNumber
                        ,idImage,image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Student>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Student> response) {
                        Student student=response.body();
                        if (response.isSuccessful()&&student!=null){
                            registerStudent.setValue(student);
                            networkState.setValue(new NetworkState(false,null));

                        }else {
                            APIResponse apiResponse= APIErrorUtil.parseError(((App)getApplication()).getStudentsRepo().getRetrofitClient(),response);
                            if (apiResponse==null)
                                networkState.setValue(new NetworkState(false,getApplication().getApplicationContext()
                                        .getString(R.string.error_registering_student)));

                            else
                            networkState.setValue(new NetworkState(false,apiResponse.getMessage()));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        networkState.setValue(new NetworkState(false,getApplication().getApplicationContext()
                        .getString(R.string.error_registering_student)));
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
        return registerStudent;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
