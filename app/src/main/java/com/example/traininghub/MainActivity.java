package com.example.traininghub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        RecyclerView recyclerView=findViewById(R.id.coursesForYouRecycler);
//        RecyclerView categoriesRecycler=findViewById(R.id.categories_recycler);
//        RecyclerView recentlyAdded_recycler=findViewById(R.id.recentlyAdded_recycler);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
//        CoursesAdapter coursesAdapter=new CoursesAdapter(this);
//        recyclerView.setAdapter(coursesAdapter);
//
//        recentlyAdded_recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
//        recentlyAdded_recycler.setAdapter(coursesAdapter);
//
//        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
//        CategoriesAdapter categoriesAdapter=new CategoriesAdapter(this);
//        categoriesRecycler.setAdapter(categoriesAdapter);
    }
}
