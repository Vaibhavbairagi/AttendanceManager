package com.example.attendancemanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
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
import com.example.attendancemanager.subrec;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeCustomRLAdapter extends RecyclerView.Adapter<HomeCustomRLAdapter.HomeRLViewHolder>{

    private Context context;
    private ArrayList<Subject> subjects;
    private Subjectdb db;

    public HomeCustomRLAdapter(ArrayList<Subject> subjects, Context context) {
        this.subjects=subjects;
        this.context = context;
        db = new Subjectdb(context);
    }

    @NonNull
    @Override
    public HomeRLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_subject_card_item,parent,false);
        return new HomeRLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeRLViewHolder holder, int position) {
        final String subName = subjects.get(position).getName();
        holder.subjectName.setText(subName);

        //Intent added by shobhit--------------------------
        holder.subjectName.setOnClickListener(new TextView.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, subrec.class);
                intent.putExtra("subjectname",subName);
                context.startActivity(intent);
            }
        });
        //---------------------------------------------------

        updateAttendedanceViews(subName,holder);

        holder.classAttendedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassRecord(subName,1);
                updateAttendedanceViews(subName,holder);
            }
        });
        holder.classNotAttendedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassRecord(subName,0);
                updateAttendedanceViews(subName,holder);
            }
        });
    }

    private void updateAttendedanceViews(String subName,HomeRLViewHolder holder){
        Pair<Integer,Integer> attendesStatus=db.getheldattended(subName);
        int classHeld = attendesStatus.first;
        int classAttended = attendesStatus.second;
        double attendedPercent;
        if(classHeld==0)
            attendedPercent=0;
        else
            attendedPercent=(double)((double)classAttended/(double)classHeld)*100;
        holder.attendanceProgress.setProgress((int)attendedPercent);
        holder.attendanceProgress.setSecondaryProgress((int)(100 - (int)attendedPercent));
        holder.attendancePercent.setText(""+String.format("%.2f",attendedPercent));
        holder.attendanceFraction.setText(classAttended+"/"+classHeld);
        int canleave=((4*classAttended/3)-classHeld);
        if(canleave>0)
        {
            holder.attendanceStatus.setText("You may leave next "+canleave+" classes");
        }
        else
        {
            holder.attendanceStatus.setText("You cannot leave any classes");
        }
    }

    private void addClassRecord(String subName, int attendedStatus){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss:SSS a zzz");
        ClassRecord classRecord = new ClassRecord(subName,sdf.format(date).toString(),attendedStatus);

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
