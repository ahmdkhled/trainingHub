package com.example.traininghub.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.adapters.CategoriesAdapter;
import com.example.traininghub.adapters.CoursesAdapter;
import com.example.traininghub.R;
import com.example.traininghub.databinding.FragmentMainBinding;
import com.example.traininghub.models.CategoriesResponse;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.view.activities.AllCoursesActivity;
import com.example.traininghub.view.activities.MainActivity;
import com.example.traininghub.viewModel.MainActivityVM;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    private MainActivityVM mainActivityVM;
    @BindView(R.id.coursesForYouRecycler)
    RecyclerView coursesForYouRecycler;
    @BindView(R.id.recentlyAdded_recycler)
    RecyclerView recentlyAdded_recycler;
    @BindView(R.id.categories_recycler)
    RecyclerView categories_recycler;
    private FragmentMainBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false);
        Log.d("FRAGG", "onCreateView: ");
        ButterKnife.bind(this,binding.getRoot());
        mainActivityVM= ViewModelProviders.of(this).get(MainActivityVM.class);

        binding.seeAllRecentlyAdded.setOnClickListener(view->{
            Intent intent=new Intent(getContext(), AllCoursesActivity.class);
            getContext().startActivity(intent);
        });

        binding.seeAllRecentlyAdded.setOnClickListener(view->{
            Intent intent=new Intent(getContext(), AllCoursesActivity.class);
            getContext().startActivity(intent);
        });

        getCourses();
        getCategories();



        return binding.getRoot();
    }

    private void getCourses(){
        mainActivityVM.getCourses(null,null,null)
                .observe(this, new Observer<CoursesResponse>() {
                    @Override
                    public void onChanged(CoursesResponse coursesResponse) {
                        //Log.d("COURSES", "onChanged: size "+coursesResponse.getCourses().get(1).getInstructors().get(0).getImage());
                        CoursesAdapter coursesAdapter=new CoursesAdapter(getContext(),coursesResponse.getCourses(),true);
                        coursesForYouRecycler.setAdapter(coursesAdapter);
                        coursesForYouRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                        coursesForYouRecycler.startLayoutAnimation();

                        recentlyAdded_recycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                        recentlyAdded_recycler.setAdapter(coursesAdapter);
                    }
                });


        mainActivityVM.getCoursesLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });

        mainActivityVM
                .getIsCoursesLoading()
                .observe(this, aBoolean -> {
                    if (aBoolean!=null&&aBoolean){
                        binding.coursesForYouShimmer.setVisibility(View.VISIBLE);
                        binding.recentlyAddedShimmer.setVisibility(View.VISIBLE);
                    }else {
                        binding.coursesForYouShimmer.setVisibility(View.GONE);
                        binding.recentlyAddedShimmer.setVisibility(View.GONE);
                    }
                });
    }



    private void getCategories(){
        mainActivityVM
                .getCategories(null,null)
                .observe(this, categoriesResponse -> {
                    Log.d("CATEGORRIIS", "onChanged: "+categoriesResponse.getCurrent_page());
                    categories_recycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    CategoriesAdapter categoriesAdapter=new CategoriesAdapter(getContext(),categoriesResponse.getCategories());
                    categories_recycler.setAdapter(categoriesAdapter);
                });

        mainActivityVM
                .getIsCategoriesLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean){
                            binding.categoriesShimmer.setVisibility(View.VISIBLE);
                        }else {
                            binding.categoriesShimmer.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.coursesForYouShimmer.startShimmer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.coursesForYouShimmer.stopShimmer();
    }


}
