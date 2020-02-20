package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.traininghub.R;
import com.example.traininghub.adapters.CoursesAdapter;
import com.example.traininghub.databinding.ActivityAllCoursesBinding;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.viewModel.AllCoursesVM;

public class AllCoursesActivity extends AppCompatActivity {

    AllCoursesVM allCoursesVM;
    CoursesAdapter coursesAdapter;
    ActivityAllCoursesBinding binding;
    public static final String CATEGORY_ID="category_id";
    String category_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_all_courses);
        if (getIntent().hasExtra(CATEGORY_ID))
        category_id=String.valueOf(getIntent().getIntExtra(CATEGORY_ID,-1));
        Log.d("CATEGORRYY", "onCreate: "+category_id);

        allCoursesVM= ViewModelProviders.of(this).get(AllCoursesVM.class);
        coursesAdapter=new CoursesAdapter(this,null,false);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.coursesRecycler.setAdapter(coursesAdapter);
        binding.coursesRecycler.setLayoutManager(layoutManager);
        getCourses(null);


    }

    void getCourses(String page){
        allCoursesVM
                .getCourses(page,null,category_id)
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
