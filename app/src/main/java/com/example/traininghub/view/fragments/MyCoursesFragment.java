package com.example.traininghub.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;
import com.example.traininghub.adapters.CoursesAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCoursesFragment extends Fragment {

    @BindView(R.id.courses_recycler)
    RecyclerView courses_recycler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mycourses,container,false);

        ButterKnife.bind(this,v);

        courses_recycler.setLayoutManager(new GridLayoutManager(
                getContext(), 2));
        CoursesAdapter coursesAdapter=new CoursesAdapter(getContext(),null);
        courses_recycler.setAdapter(coursesAdapter);

        return v;
    }
}
