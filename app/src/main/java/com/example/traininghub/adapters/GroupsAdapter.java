package com.example.traininghub.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutGroupItemBinding;
import com.example.traininghub.models.Group;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupHolder> {

    private ArrayList<Group> groups;

    public GroupsAdapter(ArrayList<Group> groups) {
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutGroupItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.layout_group_item,parent,false);
        return new GroupHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        holder.binding.setGroup(groups.get(position));
    }

    @Override
    public int getItemCount() {
        return groups==null?0:groups.size();
    }



     class GroupHolder extends RecyclerView.ViewHolder{

         LayoutGroupItemBinding binding;
        public GroupHolder(@NonNull LayoutGroupItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
