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
import com.example.traininghub.models.NetworkState;
import com.example.traininghub.network.Network;
import com.example.traininghub.viewModel.AllCoursesVM;

import java.util.ArrayList;

public class AllCoursesActivity extends AppCompatActivity {

    AllCoursesVM allCoursesVM;
    CoursesAdapter coursesAdapter;
    CoursesPagedAdapter coursesPagedAdapter;
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
        coursesPagedAdapter=new CoursesPagedAdapter();
        binding.coursesRecycler.setAdapter(coursesPagedAdapter);

        //todo check for intenrnet connection before making any request


        getCourses();



    }

    private void getCourses(){

        if (!Network.isNetworkAvailable(this)){

            binding.emptyView.getRoot().setVisibility(View.VISIBLE);
            binding.emptyView.setError(new Error(getString(R.string.no_connection)
                    ,getString(R.string.retry),R.drawable.heart_no));
            binding.emptyView.action
                    .setOnClickListener(view -> {
                        getCourses();


                    });
            return;

        }

        allCoursesVM.init(null,category_id);

        if (!allCoursesVM.getCoursesPagedList().hasObservers())
            allCoursesVM.getCoursesPagedList()
                    .observe(this, new Observer<PagedList<Course>>() {
                        @Override
                        public void onChanged(PagedList<Course> courses) {
                            Log.d("CoursesDataSource", "get courses: "+courses.size());
                            coursesPagedAdapter.submitList(courses);

                        }
                    });

        if (!allCoursesVM.getNetworkState().hasObservers())
            allCoursesVM.getNetworkState()
                    .observe(this, networkState -> {
                        //Log.d("CoursesDataSource", "onChanged: "+networkState);

                        binding.shimmer.setVisibility(networkState.getVisibility());
                        if (networkState.getErrorMessage()==null){
                            binding.emptyView.getRoot().setVisibility(View.GONE);
                            return;
                        }
                        binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                        binding.emptyView.setError(new Error(networkState.getErrorMessage(),networkState.getActionMessage(),R.drawable.empty));

                        binding.emptyView.action
                                .setOnClickListener(view -> {
                                    if (networkState.getAction()==NetworkState.RETRY)
                                        allCoursesVM.invalidate();
                                    else
                                        onBackPressed();
                                });
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
