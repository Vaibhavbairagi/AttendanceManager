package com.example.attendancemanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancemanager.R;
import com.example.attendancemanager.database.Subjectdb;
import com.example.attendancemanager.pojos.ClassRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class RecViewCustomRLAdapter extends RecyclerView.Adapter<RecViewCustomRLAdapter.ViewHolder>
{
    private String subjectname;
    private Context context;
    private Subjectdb db;
    ArrayList<ClassRecord> recordlist;

    public RecViewCustomRLAdapter(String subjectname, Context context)
    {
        this.subjectname=subjectname;
        this.context=context;
        db=new Subjectdb(context);
        recordlist=new ArrayList<>(db.getAllClassRecords(subjectname));
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rec_date,rec_topic;
        Switch rec_attended;

        public ViewHolder(View view)
        {
            super(view);
            this.rec_date=view.findViewById(R.id.rec_date);
            this.rec_attended=view.findViewById(R.id.rec_attended);
            this.rec_topic=view.findViewById(R.id.rec_topic);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.subrec_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        final ClassRecord record=recordlist.get(position);

        String datestring=record.getDate();
        SimpleDateFormat format=new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss:SSS a zzz");
        Date date = null;
        try {
            date = format.parse(datestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        datestring=dateFormat.format(date);
        holder.rec_date.setText(datestring);

        boolean bool=(record.getAttended()==1);
        holder.rec_attended.setChecked(bool);
        holder.rec_attended.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Switch s= (Switch) v;
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setCancelable(false);
                builder.setTitle("ATTENDANCE");
                if(record.getAttended()==1)
                {
                    builder.setMessage("Are you sure you want to change this class to NOT ATTENDED?");
                }
                else if(record.getAttended()==0)
                {
                    builder.setMessage("Are you sure you want to change this class to ATTENDED?");
                }
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(record.getAttended()==0)
                        {
                            s.setChecked(true);
                        }
                        else if(record.getAttended()==1)
                        {
                            s.setChecked(false);
                        }
                        db.changeattendedstatus(record);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(record.getAttended()==0)
                        {
                            s.setChecked(false);
                        }
                        else if(record.getAttended()==1)
                        {
                            s.setChecked(true);
                        }
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        holder.rec_topic.setText(record.getTopics());
    }

    @Override
    public int getItemCount() {
        return recordlist.size();
    }
}
