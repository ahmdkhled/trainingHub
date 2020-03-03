package com.example.traininghub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.traininghub.R;
import com.example.traininghub.databinding.LayoutCourseMediaBinding;
import com.example.traininghub.models.Media;

import java.util.ArrayList;

public class CourseMediaAdapter extends PagerAdapter {


    private ArrayList<Media> mediaList;

    public CourseMediaAdapter(ArrayList<Media> mediaList) {
        this.mediaList = mediaList;

    }



    @Override
    public int getCount() {
        if (mediaList==null||mediaList.isEmpty())return 1;
        return mediaList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutCourseMediaBinding binding=DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext())
                ,R.layout.layout_course_media,container,false);
        Media media=null;
        if (mediaList!=null&&!mediaList.isEmpty()){
            binding.setMedia(mediaList.get(position));
            container.addView(binding.getRoot());

        }else
        binding.setMedia(new Media());
        Log.d("populateUi", "instantiateItem: ");

        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

//    @BindingAdapter("android:src")
//    public static void setImage(ImageView imageView,String url){
//        Glide.with(imageView.getContext())
//                .load(url)
//                .placeholder(R.drawable.pl)
//                .into(imageView);
//    }
}
