package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.ColorFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.traininghub.App;
import com.example.traininghub.R;
import com.example.traininghub.databinding.ActivityProfileBinding;
import com.example.traininghub.models.Student;
import com.example.traininghub.retrofit.RetrofitClient;
import com.example.traininghub.viewModel.ProfileActivityVM;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    ProfileActivityVM profileActivityVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        profileActivityVM=new ViewModelProvider(this).get(ProfileActivityVM.class);

        binding.register.setOnClickListener(view -> {
            Student student=getStudent();
            if (student!=null)
            registerStudent(student);
        });

        String[] stateList=getResources().getStringArray(R.array.stateList);
        ArrayAdapter<String> stateAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,stateList);
        binding.state.setAdapter(stateAdapter);


        String[] facultyList=getResources().getStringArray(R.array.facultyList);
        ArrayAdapter<String> facultyAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,facultyList);
        binding.faculty.setAdapter(facultyAdapter);


        String[] degreeList=getResources().getStringArray(R.array.degreeList);
        ArrayAdapter<String> degreeAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,degreeList);
        binding.degree.setAdapter(degreeAdapter);


    }

    void registerStudent(Student student){
        profileActivityVM
                .registerStudent(student)
                .observe(this,studentRes -> {
                    Toast.makeText(this, "Thanks for registering", Toast.LENGTH_SHORT).show();
                });

        profileActivityVM
                .getNetworkState()
                .observe(this,networkState -> {
                    Toast.makeText(this, networkState.getErrorMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    private Student getStudent(){
        TextInputLayout[] inputs={binding.nameAr,binding.nameEn,binding.email,binding.mobileNum,binding.city,binding.address,binding.idNumber};
        boolean error=false;

        for (int i = 0; i < inputs.length; i++) {
            if (TextUtils.isEmpty(inputs[i].getEditText().getText().toString())){
                error=true;
                inputs[i].setError(getString(R.string.required));
                inputs[i].getEditText().setBackgroundResource(R.drawable.edittext_bg);

            }else
                inputs[i].setError(null);

        }
        if (error) return null;
        return new Student(
                inputs[0].getEditText().getText().toString(), //name ar
                inputs[1].getEditText().getText().toString(), // name en
                inputs[2].getEditText().getText().toString(), //email
                inputs[3].getEditText().getText().toString(), // mobile num
                null, //sec mobile num
                "cairo",
                inputs[4].getEditText().getText().toString(),//city
                inputs[5].getEditText().getText().toString(), //address
                "هندسه",
                "حريج",
                null,
                "1234",
                inputs[6].getEditText().getText().toString()


        );


    }
}
