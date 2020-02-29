package com.example.attendancemanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.attendancemanager.R;
import com.example.attendancemanager.pojos.Subject;

import java.util.ArrayList;

public class HomeCustomRLAdapter extends RecyclerView.Adapter<HomeCustomRLAdapter.HomeRLViewHolder>{

    private ArrayList<Subject> subjects;

    public HomeCustomRLAdapter(ArrayList<Subject> subjects) {
        this.subjects=subjects;
    }

    @NonNull
    @Override
    public HomeRLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_subject_card_item,parent,false);
        return new HomeRLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRLViewHolder holder, int position) {
        holder.subjectName.setText(subjects.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class HomeRLViewHolder extends RecyclerView.ViewHolder{
        private TextView subjectName;
        HomeRLViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
        }
    }
}
