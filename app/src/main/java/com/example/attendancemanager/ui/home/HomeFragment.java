package com.example.attendancemanager.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancemanager.R;
import com.example.attendancemanager.adapters.HomeCustomRLAdapter;
import com.example.attendancemanager.pojos.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView subjectsRecyclerView;
    private FloatingActionButton addSubjectsFAB;
    private HomeCustomRLAdapter homeCustomRLAdapter;
    private ArrayList<Subject> subjects;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        subjectsRecyclerView = root.findViewById(R.id.subjects_recycler_view);
        addSubjectsFAB = root.findViewById(R.id.add_sub_fab_btn);
        handleAddSubjectButtonClick();
        fetchSubjectsData();
        homeCustomRLAdapter = new HomeCustomRLAdapter(subjects);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectsRecyclerView.setAdapter(homeCustomRLAdapter);
        homeCustomRLAdapter.notifyDataSetChanged();

//        homeViewModel.getSubjects().observe(this, new Observer<ArrayList<Subject>>() {
//            @Override
//            public void onChanged(ArrayList<Subject> subjects) {
//
//            }
//        });
        return root;
    }

    private void handleAddSubjectButtonClick() {
        addSubjectsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void fetchSubjectsData() {
        subjects = new ArrayList<>();
        subjects.add(new Subject("MPMC"));
        subjects.add(new Subject("DS"));
        subjects.add(new Subject("Algos"));
    }
}