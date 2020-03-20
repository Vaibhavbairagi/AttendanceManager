package com.example.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.attendancemanager.adapters.RecViewCustomRLAdapter;

public class subrec extends AppCompatActivity {

    String subjectname;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subrec);

        Intent intent=getIntent();
        subjectname=intent.getStringExtra("subjectname");

        recyclerView=findViewById(R.id.subrec_rv);
        RecViewCustomRLAdapter adapter=new RecViewCustomRLAdapter(subjectname,this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
