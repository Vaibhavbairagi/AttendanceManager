package com.example.attendancemanager.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancemanager.R;
import com.example.attendancemanager.adapters.HomeCustomRLAdapter;
import com.example.attendancemanager.database.Subjectdb;
import com.example.attendancemanager.pojos.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView subjectsRecyclerView;
    private FloatingActionButton addSubjectsFAB;
    private HomeCustomRLAdapter homeCustomRLAdapter;
    private ArrayList<Subject> subjects = new ArrayList<>();
    private Subjectdb db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        subjectsRecyclerView = root.findViewById(R.id.subjects_recycler_view);
        addSubjectsFAB = root.findViewById(R.id.add_sub_fab_btn);
        db = new Subjectdb(getContext());
        fetchSubjectsData();
        handleAddSubjectButtonClick(getContext());
        homeCustomRLAdapter = new HomeCustomRLAdapter(subjects,getContext());
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectsRecyclerView.setAdapter(homeCustomRLAdapter);
        homeCustomRLAdapter.notifyDataSetChanged();
        return root;
    }

    private void handleAddSubjectButtonClick(final Context context) {
        addSubjectsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.subject_details_popup_dialogue);
                final EditText subjectNameET = dialog.findViewById(R.id.popup_subject_name_entry);
                final EditText subjectTeacherET = dialog.findViewById(R.id.popup_subject_teacher_entry);
                final EditText subjectCreditsET = dialog.findViewById(R.id.popup_subject_credits_entry);
                Button cancelBtn = dialog.findViewById(R.id.popup_cancel_btn);
                Button saveBtn= dialog.findViewById(R.id.popup_save_btn);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(subjectNameET.getText().toString().equals("")){
                            Toast.makeText(context,"Enter the subject name",Toast.LENGTH_LONG).show();
                        } else {
                            String creds=subjectCreditsET.getText().toString();
                            int credits;
                            if (creds.equals(""))
                                credits=0;
                            else
                                credits= Integer.parseInt(subjectCreditsET.getText().toString());
                            db.addsubject(new Subject(subjectNameET.getText().toString(),subjectTeacherET.getText().toString(),credits));
                            homeCustomRLAdapter.update(db.getAllSubjects());
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void fetchSubjectsData() {
        subjects = db.getAllSubjects();
    }
}