package com.example.traininghub.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.traininghub.R;
import com.example.traininghub.adapters.StudentCoursesAdapter;
import com.example.traininghub.databinding.FragmentMycoursesBinding;
import com.example.traininghub.viewModel.MyCoursesVM;

public class MyCoursesFragment extends Fragment {

    MyCoursesVM myCoursesVM;
    StudentCoursesAdapter adapter;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMycoursesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mycourses, container, false);

        myCoursesVM=new ViewModelProvider(getActivity()).get(MyCoursesVM.class);
        adapter=new StudentCoursesAdapter();

        binding.coursesRecycler.setLayoutManager(new GridLayoutManager(
                getContext(), 2));
        binding.coursesRecycler.setAdapter(adapter);

        getStudentCourse("12",null);

        return binding.getRoot();
    }

    public void getStudentCourse(String student_id, String limit){
        myCoursesVM.init(student_id,limit);
        myCoursesVM.getStudentCourses()
                .observe(getActivity(), studentCourses -> {
                    //coursesAdapter.addCourses(studentCourses);
                    adapter.submitList(studentCourses);
                    Log.d("GROUPSSS", "getStudentCourse: "+studentCourses.size());
                });
    }

}

