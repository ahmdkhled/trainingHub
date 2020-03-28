package com.example.traininghub.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.databinding.ActivityProfileBinding;
import com.example.traininghub.helpers.FileUtils;
import com.example.traininghub.models.Student;
import com.example.traininghub.viewModel.ProfileActivityVM;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    ProfileActivityVM profileActivityVM;
    public static final int PERMISSION_CODE=113;
    private static final int ID_IMAGE_REQUEST_CODE=114;
    private static final int PROFILE_IMAGE_REQUEST_CODE=115;
    private int currentRequestCode;
    private MultipartBody.Part idImage;
    private MultipartBody.Part profileImage;
    private static final String TAG = "ProfileActivity";

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
            else
                Toast.makeText(this, "complete all fields", Toast.LENGTH_SHORT).show();
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

        binding.idImg
                .setOnClickListener(view -> {
                    grantPermission(ID_IMAGE_REQUEST_CODE);
                });

        binding.profileImg
                .setOnClickListener(view -> {
                    grantPermission(PROFILE_IMAGE_REQUEST_CODE);
                });

    }


    void registerStudent(Student student){
        profileActivityVM
                .registerStudent(toRequestBody(student.getNameAr()),toRequestBody(student.getNameEn()),toRequestBody(student.getEmail()),
                        toRequestBody(student.getPhoneNumber()),toRequestBody(student.getPhoneNumberSec()),toRequestBody(student.getState()),
                        toRequestBody(student.getCity()),toRequestBody(student.getAddress()),toRequestBody(student.getFaculty()),
                        toRequestBody(student.getDegree()),toRequestBody(student.getIdNumber()),toRequestBody(student.getPassportNumber()),
                        toRequestBody(student.getSkillCardNumber())
                        ,idImage,profileImage)
                .observe(this,studentRes -> {
                    Toast.makeText(this, "Thanks for registering", Toast.LENGTH_SHORT).show();
                    clearFields();
                });

        profileActivityVM
                .getNetworkState()
                .observe(this,networkState -> {
                    binding.setNetwork(networkState);
                    Log.d(TAG, "getNetworkState: "+networkState.isLoading());
                    if (networkState.getErrorMessage()!=null)
                    Toast.makeText(this,  networkState.getErrorMessage(), Toast.LENGTH_SHORT).show();
                });

    }


    private Student getStudent(){
        TextInputLayout[] inputs={binding.nameAr,binding.nameEn,binding.email,binding.mobileNum,binding.city,binding.address,binding.idNumber};
        boolean error=false;

        for (int i = 0; i < inputs.length; i++) {
            if (TextUtils.isEmpty(inputs[i].getEditText().getText().toString())){
                error=true;
                inputs[i].setError(getString(R.string.required));
                Log.d("ERROORRR", "field : "+i+inputs[i]);

            }else
                inputs[i].setError(null);

        }
        if (error) return null;
        if (idImage==null){
            Toast.makeText(this, "please upload your id Image", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new Student(
                inputs[0].getEditText().getText().toString(), //name ar
                inputs[1].getEditText().getText().toString(), // name en
                inputs[2].getEditText().getText().toString(), //email
                inputs[3].getEditText().getText().toString(), // mobile num
                null, //sec mobile num
                binding.state.getText().toString(),
                inputs[4].getEditText().getText().toString(),//city
                inputs[5].getEditText().getText().toString() //address"
                 ,binding.faculty.getText().toString()
                ,(String) binding.degree.getSelectedItem(),
                null,
                "1234598985",
                inputs[6].getEditText().getText().toString()
        );
    }

    private void grantPermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                pickImage(requestCode);
            }else {
                currentRequestCode=requestCode;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_CODE);
            }
        }else
            pickImage(requestCode);
    }

    private void pickImage(int requestCode){
        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ID_IMAGE_REQUEST_CODE&&resultCode== Activity.RESULT_OK){
            binding.idImg.setImageURI(data.getData());
            File file=new File(FileUtils.getRealPathFromURI(data.getData(),this));
            RequestBody requestBody = RequestBody.create(file,MediaType.parse(getContentResolver().getType(data.getData())));
            idImage=MultipartBody.Part.createFormData("idImage", file.getName(), requestBody);
        }
        if (requestCode==PROFILE_IMAGE_REQUEST_CODE&&resultCode== Activity.RESULT_OK){
            binding.profileImg.setImageURI(data.getData());
            File file=new File(FileUtils.getRealPathFromURI(data.getData(),this));
            RequestBody requestBody = RequestBody.create(file,MediaType.parse(getContentResolver().getType(data.getData())));
            profileImage=MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_CODE&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            pickImage(currentRequestCode);
        }
    }

    private RequestBody toRequestBody(String field){
        return field==null?null: RequestBody.create(field,MediaType.parse("text/plain"));
    }

    private void clearFields(){
        for (int i = 0; i< binding.container.getChildCount(); i++){
            if (binding.container.getChildAt(i) instanceof TextInputLayout)
                ((TextInputLayout) binding.container.getChildAt(i)).getEditText().setText("");
        }
    }
}
