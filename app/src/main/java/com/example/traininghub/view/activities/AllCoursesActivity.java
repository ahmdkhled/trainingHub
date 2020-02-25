package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.adapters.CoursesAdapter;
import com.example.traininghub.adapters.CoursesPagedAdapter;
import com.example.traininghub.databinding.ActivityAllCoursesBinding;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.models.Error;
import com.example.traininghub.viewModel.AllCoursesVM;

import java.util.ArrayList;

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
        binding.coursesRecycler.setLayoutManager(layoutManager);
        //getCourses(null);
        CoursesPagedAdapter coursesPagedAdapter=new CoursesPagedAdapter();
        binding.coursesRecycler.setAdapter(coursesPagedAdapter);


        allCoursesVM.getCoursesPagedList()
                .observe(this, new Observer<PagedList<Course>>() {
                    @Override
                    public void onChanged(PagedList<Course> courses) {
                        Log.d("CoursesDataSource", "onChanged: ");
                        coursesPagedAdapter.submitList(courses);

                    }
                });


    }

    void getCourses(String page){
        allCoursesVM
                .getCourses(page,null,category_id)
                .observe(this, new Observer<CoursesResponse>() {
                    @Override
                    public void onChanged(CoursesResponse coursesResponse) {

                        ArrayList<Course> courses=coursesResponse.getCourses();

                        if (courses!=null&&courses.isEmpty()){
                            binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                            binding.emptyView.setError(new Error("there is no courses in this section"
                                    ,"back to home",R.drawable.ic_sentiment_dissatisfied_black_24dp));
                            binding.emptyView.action
                                    .setOnClickListener(view -> {
                                        onBackPressed();
                                    });
                        }else if (courses==null){
                            binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                            binding.emptyView.setError(new Error(getString(R.string.server_error),getString(R.string.back_to_home)));

                            binding.emptyView.action
                                    .setOnClickListener(view -> {
                                        getCourses("1");
                                    });
                        }
                        coursesAdapter.addCourses(courses);
                    }
                });

        allCoursesVM
                .getIsCoursesLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean){
                            binding.shimmer.setVisibility(View.VISIBLE);
                        }else {
                            binding.shimmer.setVisibility(View.GONE);
                        }
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

    @Override
    protected void onResume() {
        super.onResume();
        binding.shimmer.startShimmer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.shimmer.stopShimmer();
    }
}
