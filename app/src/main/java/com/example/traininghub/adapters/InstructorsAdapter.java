package com.example.traininghub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traininghub.R;
import com.example.traininghub.models.Instructor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class InstructorsAdapter extends RecyclerView.Adapter<InstructorsAdapter.InstructorsHolder> {

    private Context context;
    private ArrayList<Instructor> instructors;

    public InstructorsAdapter(Context context, ArrayList<Instructor> instructors) {
        this.context = context;
        this.instructors = instructors;
    }

    @NonNull
    @Override
    public InstructorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_instructor,parent,false);
        return new InstructorsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorsHolder holder, int position) {
        Glide.with(context)
                .load(instructors.get(position).getImage())
                .into(holder.instructor_img);
    }

    @Override
    public int getItemCount() {
        return instructors==null?0:instructors.size();
    }

    class InstructorsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.instructor_name)
        TextView instructor_name;
        @BindView(R.id.instructor_img)
        CircleImageView instructor_img;
        public InstructorsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
