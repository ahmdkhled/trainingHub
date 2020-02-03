package com.example.traininghub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.traininghub.adapters.InstructorsAdapter;
import com.example.traininghub.models.Course;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static String  EXTRA_COURSE="extra_course";
    @BindView(R.id.course_name)
    TextView course_name;
    @BindView(R.id.course_rating)
    RatingBar course_rating;
    @BindView(R.id.course_desc)
    TextView course_desc;
    @BindView(R.id.course_duration)
    TextView course_duration;
    @BindView(R.id.instructors_recycler)
    RecyclerView instructors_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Course course=getIntent().getParcelableExtra(EXTRA_COURSE);
        if (course==null)
            return;
        populateUi(course);

    }

    private void populateUi(Course course){
        course_name.setText(course.getName());
        course_rating.setRating(course.getRating());
        course_desc.setText(course.getDescription());
        course_duration.setText(course.getDuration()+" hours");
        InstructorsAdapter instructorsAdapter=new InstructorsAdapter(this,course.getInstructors());
        instructors_recycler.setLayoutManager(new LinearLayoutManager(this));
        instructors_recycler.setAdapter(instructorsAdapter);
    }
}
