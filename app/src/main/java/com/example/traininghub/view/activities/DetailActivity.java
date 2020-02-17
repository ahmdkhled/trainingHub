package com.example.traininghub.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.traininghub.R;
import com.example.traininghub.adapters.ContentAdapter;
import com.example.traininghub.adapters.CourseMediaAdapter;
import com.example.traininghub.adapters.InstructorsAdapter;
import com.example.traininghub.databinding.ActivityDetailBinding;
import com.example.traininghub.helpers.ContentParser;
import com.example.traininghub.models.Content;
import com.example.traininghub.models.Course;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static String  EXTRA_COURSE="extra_course";

    ActivityDetailBinding binding;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        ButterKnife.bind(this);
        course=getIntent().getParcelableExtra(EXTRA_COURSE);


        binding.setCourse(course);
        populateUi(course);

    }

    private void populateUi(Course course){


        RecyclerView instructors_recycler=binding.instructorsRecycler;
        InstructorsAdapter instructorsAdapter=new InstructorsAdapter(this,course.getInstructors());
        instructors_recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        binding.instructorsRecycler.setAdapter(instructorsAdapter);


        ArrayList<Content> content =ContentParser.getCourseContent(course.getContent());
        ContentAdapter contentAdapter=new ContentAdapter(content);
        binding.syllabusRecycler.setAdapter(contentAdapter);
        binding.syllabusRecycler.setLayoutManager(new LinearLayoutManager(this));


        CourseMediaAdapter courseMediaAdapter=new CourseMediaAdapter(course.getMedia());

        binding.courseMedia.setAdapter(courseMediaAdapter);
        PageIndicatorView pageIndicatorView=binding.courseMediaIndicator;
        pageIndicatorView.setCount(course.getMedia().size());
        pageIndicatorView.setAnimationType(AnimationType.DROP);
        pageIndicatorView.setAnimationDuration(1500);

        binding.courseMedia.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.seeAllReviews.setOnClickListener(view ->{
            Intent intent=new Intent(this,ReviewsActivity.class);
            intent.putExtra(EXTRA_COURSE,course);
            startActivity(intent);
        });



    }

    @BindingAdapter("android:src")
    public static void bindImage(ImageView imageView,String url){
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.bg)
                .into(imageView);
    }
}
