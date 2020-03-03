package com.example.traininghub.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class Group {
    private String title;
    private String start;
    private String end;

    public Group(String title, String start) {
        this.title = title;
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }


    public static DiffUtil.ItemCallback<Group> DIFF_CALLBACK=new DiffUtil.ItemCallback<Group>() {
        @Override
        public boolean areItemsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj==null)return false;
        if (!(obj instanceof Group)) return false;
        Group group= (Group) obj;
        return group.title.equals(title)&&group.start.equals(start);
    }
}
