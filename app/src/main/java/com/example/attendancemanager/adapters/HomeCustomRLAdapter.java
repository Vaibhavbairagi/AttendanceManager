package com.example.attendancemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.attendancemanager.R;
import com.example.attendancemanager.database.Subjectdb;
import com.example.attendancemanager.pojos.ClassRecord;
import com.example.attendancemanager.pojos.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeCustomRLAdapter extends RecyclerView.Adapter<HomeCustomRLAdapter.HomeRLViewHolder>{

    private Context context;
    private ArrayList<Subject> subjects;

    public HomeCustomRLAdapter(ArrayList<Subject> subjects, Context context) {
        this.subjects=subjects;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_subject_card_item,parent,false);
        return new HomeRLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRLViewHolder holder, int position) {
        final String subName = subjects.get(position).getName();
        holder.subjectName.setText(subName);
        holder.attendanceProgress.setProgress(75);
        holder.attendanceProgress.setSecondaryProgress(25);

        holder.classAttendedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassRecord(subName,1);
            }
        });
        holder.classNotAttendedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassRecord(subName,0);
            }
        });
    }

    private void addClassRecord(String subName, int attendedStatus){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss:SSS a zzz");
        ClassRecord classRecord = new ClassRecord(subName,sdf.format(date).toString(),attendedStatus);
        Subjectdb db = new Subjectdb(context);
        db.addclassrecord(classRecord);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void update(ArrayList<Subject> data) {
        subjects.clear();
        subjects.addAll(data);
        notifyDataSetChanged();
    }

    static class HomeRLViewHolder extends RecyclerView.ViewHolder{
        private TextView subjectName,attendanceFraction,attendanceStatus,attendancePercent;
        private ProgressBar attendanceProgress;
        private ImageButton classAttendedBtn,classNotAttendedBtn;
        HomeRLViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
            attendanceFraction = itemView.findViewById(R.id.sub_attendance_fraction);
            attendanceStatus= itemView.findViewById(R.id.sub_attendance_status);
            attendanceProgress = itemView.findViewById(R.id.attendence_progress);
            classAttendedBtn= itemView.findViewById(R.id.class_attended_imgbtn);
            classNotAttendedBtn= itemView.findViewById(R.id.class_not_attended_imgbtn);
            attendancePercent = itemView.findViewById(R.id.sub_attendance_percent);
        }
    }
}
