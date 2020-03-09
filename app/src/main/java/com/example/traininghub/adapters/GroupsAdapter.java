package com.example.traininghub.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutGroupItemBinding;
import com.example.traininghub.models.Group;

public class GroupsAdapter extends PagedListAdapter<Group, GroupsAdapter.GroupHolder> {

    private OnGroupClicked onGroupClicked;


    public GroupsAdapter( OnGroupClicked onGroupClicked) {
        super(Group.DIFF_CALLBACK);
        this.onGroupClicked = onGroupClicked;
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
        Group group=getItem(position);
        holder.binding.setGroup(group);
    }


     class GroupHolder extends RecyclerView.ViewHolder{
        LayoutGroupItemBinding binding;
        public GroupHolder(@NonNull LayoutGroupItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(view -> {
                onGroupClicked.onGroupClicked(getItem(getAdapterPosition()).getId());

            } );
        }
    }

    public interface OnGroupClicked{
         void onGroupClicked(int groupId);
    }
}
