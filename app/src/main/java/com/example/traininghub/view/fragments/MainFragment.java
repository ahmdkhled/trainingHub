package com.example.traininghub.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.adapters.CategoriesAdapter;
import com.example.traininghub.adapters.CoursesAdapter;
import com.example.traininghub.R;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);
        Log.d("FRAGG", "onCreateView: ");
        ButterKnife.bind(this,v);
        mainActivityVM= ViewModelProviders.of(this).get(MainActivityVM.class);


        getCourses();


        categories_recycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        CategoriesAdapter categoriesAdapter=new CategoriesAdapter(getContext());
        categories_recycler.setAdapter(categoriesAdapter);
        return v;
    }

    private void getCourses(){
        mainActivityVM.getCourses(null)
                .observe(this, new Observer<CoursesResponse>() {
                    @Override
                    public void onChanged(CoursesResponse coursesResponse) {
                        Log.d("COURSES", "onChanged: size "+coursesResponse.getCourses().get(1).getInstructors().get(0).getImage());
                        CoursesAdapter coursesAdapter=new CoursesAdapter(getContext(),coursesResponse.getCourses());
                        coursesForYouRecycler.setAdapter(coursesAdapter);
                        coursesForYouRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));


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
    }
}
