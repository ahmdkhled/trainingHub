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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.traininghub.R;
import com.example.traininghub.adapters.StudentCoursesAdapter;
import com.example.traininghub.databinding.FragmentMycoursesBinding;
import com.example.traininghub.models.Error;
import com.example.traininghub.models.NetworkState;
import com.example.traininghub.retrofit.Network;
import com.example.traininghub.viewModel.MyCoursesVM;

public class MyCoursesFragment extends Fragment {

    private MyCoursesVM myCoursesVM;
    private StudentCoursesAdapter adapter;
    private FragmentMycoursesBinding binding;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mycourses, container, false);

        myCoursesVM=new ViewModelProvider(getActivity()).get(MyCoursesVM.class);
        adapter=new StudentCoursesAdapter();

        binding.coursesRecycler.setLayoutManager(new GridLayoutManager(
                getContext(), 2));
        binding.coursesRecycler.setAdapter(adapter);

        getStudentCourse("12",null);

        return binding.getRoot();
    }

    public void getStudentCourse(String student_id, String limit){

        if (!Network.isNetworkAvailable(getContext())){

            binding.emptyView.getRoot().setVisibility(View.VISIBLE);
            binding.emptyView.setError(new Error(getString(R.string.no_connection)
                    ,getString(R.string.retry),R.drawable.heart_no));
            binding.emptyView.action
                    .setOnClickListener(view -> {
                        getStudentCourse(student_id,limit);


                    });
            return;

        }

        myCoursesVM.init(student_id,limit);
        if (!myCoursesVM.getStudentCourses().hasObservers())
            myCoursesVM.getStudentCourses()
                .observe(getActivity(), studentCourses -> {
                    adapter.submitList(studentCourses);
                    Log.d("GROUPSSS", "getStudentCourse: "+studentCourses.size());
                });

        if (!myCoursesVM.getNetworkState().hasObservers())
        myCoursesVM.getNetworkState()
                .observe(getActivity(), networkState -> {
                    binding.coursesProgress.setVisibility(networkState.getVisibility());
                    if (networkState.getErrorMessage()==null){
                        binding.emptyView.getRoot().setVisibility(View.GONE);
                        return;
                    }
                    binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                    binding.emptyView.setError(new Error(networkState.getErrorMessage()
                            ,networkState.getActionMessage(),R.drawable.empty));


                    binding.emptyView.action
                            .setOnClickListener(view -> {
                                if (networkState.getAction()==NetworkState.RETRY)
                                    myCoursesVM.invalidate();

                                    //todo handle action of no enrolled courses
                            });

                });
    }

}

