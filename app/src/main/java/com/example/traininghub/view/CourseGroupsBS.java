package com.example.traininghub.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.traininghub.R;
import com.example.traininghub.adapters.GroupsAdapter;
import com.example.traininghub.databinding.LayoutCourseGroupsBinding;
import com.example.traininghub.helpers.SnackBarHelper;
import com.example.traininghub.models.Error;
import com.example.traininghub.models.Group;
import com.example.traininghub.models.NetworkState;
import com.example.traininghub.viewModel.GroupsBottomSheetVM;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CourseGroupsBS extends BottomSheetDialogFragment implements GroupsAdapter.OnGroupClicked{

    private static final String TAG = "CourseGroupsBS";
    private LayoutCourseGroupsBinding binding;
    private GroupsBottomSheetVM groupsBottomSheetVM;
    private GroupsAdapter adapter;
    private int id;
    public CourseGroupsBS(int id) {
        this.id=id;
    }
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.layout_course_groups,container,false);
        groupsBottomSheetVM= new ViewModelProvider(getActivity()).get(GroupsBottomSheetVM.class);
        adapter=new GroupsAdapter(this);
        binding.groupsRecycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        binding.groupsRecycler.setLayoutManager(gridLayoutManager);

        groupsBottomSheetVM.init(id,null);
        getGroups();

        return binding.getRoot();
    }

    private void getGroups(){
        groupsBottomSheetVM.getGroups()
                .observe(getActivity(), groups -> {
                    adapter.submitList(groups);
                });

        groupsBottomSheetVM
                .getNetworkState()
                .observe(getActivity(),networkState -> {
                    binding.progressbar
                            .setVisibility(networkState.getVisibility());
                    Log.d("LOADINGG", "getGroups: "+networkState.getErrorMessage());
                    binding.emptyView.getRoot().setVisibility(networkState.getErrorViewVisibility());

                    if (networkState.getErrorMessage()==null)return;
                        binding.emptyView.setError(new Error(
                                networkState.getErrorMessage(),
                                networkState.getActionMessage(),R.drawable.empty));

                        binding.emptyView.action
                                .setOnClickListener(view -> {
                                    if (networkState.getAction()== NetworkState.BACK)
                                        dismiss();
                                    else
                                        groupsBottomSheetVM.validate();
                                });

                });
    }

    ArrayList<Group> getGroupss(){
        ArrayList<Group> groups=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            groups.add(new Group("php course ","21/12/2020"));
        }
        return groups;
    }

    @Override
    public void onGroupClicked(int groupId) {

        groupsBottomSheetVM.enrollToCourse("10", String.valueOf(groupId))
        .observe(getActivity(), courseEnrollRes -> {
            Log.d(TAG, "onGroupClicked: "+courseEnrollRes.getMessage());
            //todo add snackBar instead of Toast
            Toast.makeText(context, ""+courseEnrollRes.getMessage(), Toast.LENGTH_SHORT).show();
        });
        groupsBottomSheetVM
                .getEnrollingError()
                .observe(getActivity(),error->{
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                });

        dismiss();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
