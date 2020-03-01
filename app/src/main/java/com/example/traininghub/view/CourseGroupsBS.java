package com.example.traininghub.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.traininghub.R;
import com.example.traininghub.adapters.GroupsAdapter;
import com.example.traininghub.databinding.LayoutCourseGroupsBinding;
import com.example.traininghub.models.Group;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class CourseGroupsBS extends BottomSheetDialogFragment {

    private LayoutCourseGroupsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.layout_course_groups,container,false);

        GroupsAdapter adapter=new GroupsAdapter(getGroups());
        binding.groupsRecycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        binding.groupsRecycler.setLayoutManager(gridLayoutManager);

        return binding.getRoot();
    }

    ArrayList<Group> getGroups(){
        ArrayList<Group> groups=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            groups.add(new Group("php course ","21/12/2020"));
        }
        return groups;
    }
}
