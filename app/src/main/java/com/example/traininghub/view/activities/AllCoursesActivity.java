package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.traininghub.R;
import com.example.traininghub.adapters.CoursesAdapter;
import com.example.traininghub.databinding.ActivityAllCoursesBinding;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.viewModel.AllCoursesVM;

public class AllCoursesActivity extends AppCompatActivity {

    AllCoursesVM allCoursesVM;
    CoursesAdapter coursesAdapter;
    ActivityAllCoursesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_all_courses);

        allCoursesVM= ViewModelProviders.of(this).get(AllCoursesVM.class);
        coursesAdapter=new CoursesAdapter(this,null,false);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.coursesRecycler.setAdapter(coursesAdapter);
        binding.coursesRecycler.setLayoutManager(layoutManager);
        binding.coursesRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        getCourses(null);


    }

    void getCourses(String page){
        allCoursesVM
                .getCourses(page,null)
                .observe(this, new Observer<CoursesResponse>() {
                    @Override
                    public void onChanged(CoursesResponse coursesResponse) {
                        coursesAdapter.addCourses(coursesResponse.getCourses());
                    }
                });

        allCoursesVM
                .getIsCoursesLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                    }
                });
        allCoursesVM
                .getCoursesLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {

                    }
                });

    }
}
