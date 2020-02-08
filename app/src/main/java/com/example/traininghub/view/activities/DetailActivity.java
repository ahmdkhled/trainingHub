package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traininghub.R;
import com.example.traininghub.adapters.CourseMediaAdapter;
import com.example.traininghub.adapters.InstructorsAdapter;
import com.example.traininghub.models.Course;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    public static String  EXTRA_COURSE="extra_course";
    @BindView(R.id.course_name)
    TextView course_name;
    @BindView(R.id.course_media)
    ViewPager course_media;
    @BindView(R.id.course_rating)
    RatingBar course_rating;
    @BindView(R.id.course_desc)
    TextView course_desc;
    @BindView(R.id.course_duration)
    TextView course_duration;
    @BindView(R.id.instructors_recycler)
    RecyclerView instructors_recycler;
    @BindView(R.id.center_name)
    TextView center_name;
    @BindView(R.id.center_bio)
    TextView center_bio;
    @BindView(R.id.center_logo)
    CircleImageView center_logo;



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
        course_duration.setText(String.format(getString(R.string.course_hours),course.getDuration()));
        InstructorsAdapter instructorsAdapter=new InstructorsAdapter(this,course.getInstructors());
        instructors_recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        instructors_recycler.setAdapter(instructorsAdapter);

        center_name.setText(course.getCenter().getName());
        if (course.getCenter().getBio()!=null)
            center_bio.setText(course.getCenter().getBio());
        Glide.with(this)
                .load(course.getCenter().getLogo())
                .into(center_logo);
        Log.d("populateUi", "populateUi: "+course.getMedia().size());

        CourseMediaAdapter courseMediaAdapter=new CourseMediaAdapter(course.getMedia());
        course_media.setAdapter(courseMediaAdapter);
    }
}
