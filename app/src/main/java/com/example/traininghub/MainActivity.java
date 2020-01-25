package com.example.traininghub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_main);

        RecyclerView recyclerView=findViewById(R.id.coursesForYouRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        CoursesAdapter coursesAdapter=new CoursesAdapter(this);
        recyclerView.setAdapter(coursesAdapter);

    }
}
