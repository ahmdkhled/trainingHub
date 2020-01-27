package com.example.traininghub.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.CategoriesAdapter;
import com.example.traininghub.CoursesAdapter;
import com.example.traininghub.R;

public class MyCoursesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mycourses,container,false);

        RecyclerView recyclerView=v.findViewById(R.id.coursesForYouRecycler);
        RecyclerView categoriesRecycler=v.findViewById(R.id.categories_recycler);
        RecyclerView recentlyAdded_recycler=v.findViewById(R.id.recentlyAdded_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        CoursesAdapter coursesAdapter=new CoursesAdapter(getContext());
        recyclerView.setAdapter(coursesAdapter);

        recentlyAdded_recycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recentlyAdded_recycler.setAdapter(coursesAdapter);

        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        CategoriesAdapter categoriesAdapter=new CategoriesAdapter(getContext());
        categoriesRecycler.setAdapter(categoriesAdapter);

        return v;
    }
}
