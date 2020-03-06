package com.example.attendancemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import androidx.annotation.Nullable;

import com.example.attendancemanager.pojos.ClassRecord;
import com.example.attendancemanager.pojos.Subject;

import java.util.ArrayList;
import java.util.List;

public class Subjectdb extends SQLiteOpenHelper
{
    private static final int databaseversion=1;
    private static String databasename="attendmandb";

    private static String table_subjects="subjects";
    private static String subjects_name="subjectname";
    private static String subjects_teacher="teachername";
    private static String subjects_credits="credits";

    private static String table_dates="dates";
    private static String dates_subjectname="subjectname";
    private static String dates_date="date";
    private static String dates_attended="attended";
    private static String dates_topics="topics";


    public Subjectdb(@Nullable Context context) {
        super(context, databasename, null, databaseversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_subjects="create table "+table_subjects+"("+
                subjects_name+" varchar primary key,"+
                subjects_teacher+" varchar,"+
                subjects_credits+" integer"+
                ");";

        String create_table_dates="create table "+table_dates+"("+
                dates_subjectname+" varchar primary key,"+
                dates_date+" varchar,"+
                dates_attended+" integer,"+
                dates_topics+" text"+
                ");";

        db.execSQL(create_table_subjects);
        db.execSQL(create_table_dates);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*-------------------------------------------------------------------------*/

    public void addsubject(Subject subject)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(subjects_name,subject.getName());
        if(subject.getTeacher()!=null)
            values.put(subjects_teacher,subject.getTeacher());

        if(subject.getCredits()!=0)
            values.put(subjects_credits,subject.getCredits());

        db.insert(table_subjects,null,values);
    }

    public void deletesubject(Subject subject)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_subjects,subjects_name+"=?",new String[] {String.valueOf(subject.getName())});
        db.close();
    }

    public ArrayList<Subject> getAllSubjects()
    {
        ArrayList<Subject> subjectlist=new ArrayList<>();

        String query="select * from "+table_subjects;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do
            {
                Subject subject=new Subject();
                subject.setName(cursor.getString(0));
                subject.setTeacher(cursor.getString(1));
                subject.setCredits(cursor.getInt(2));

                subjectlist.add(subject);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return subjectlist;
    }

    /*-------------------------------------------------------------------*/

    public void addclassrecord(ClassRecord record)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(dates_subjectname,record.getName());
        values.put(dates_date,record.getDate());
        values.put(dates_attended,record.getAttended());

        if(record.getTopics()!=null)
            values.put(dates_topics,record.getTopics());

        db.insert(table_dates,null,values);
    }

    public void changeattendedstatus(ClassRecord record)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        if(record.getAttended()==1)
        {
            record.setAttended(0);
        }
        else
        {
            record.setAttended(1);
        }

        ContentValues values=new ContentValues();
        values.put(dates_attended,record.getAttended());

        db.update(table_dates,values,dates_subjectname+"=? AND "+dates_date+"=?",
                new String[] {String.valueOf(record.getName()), String.valueOf(record.getDate())});
    }

    public void modifytopics(ClassRecord record)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(dates_topics,record.getTopics());

        db.update(table_dates,values,dates_subjectname+"=? AND "+dates_date+"=?",
                new String[] {String.valueOf(record.getName()),String.valueOf(record.getDate())});
    }

    public List<ClassRecord> getAllClassRecords(String subjectname)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ClassRecord> classrecordlist=new ArrayList<>();

        String query="select * from "+table_dates+" where "+dates_subjectname+" =?";
        Cursor cursor=db.rawQuery(query,new String[] {subjectname});

        if(cursor.moveToFirst())
        {
            do
            {
                ClassRecord record=new ClassRecord();
                record.setName(cursor.getString(0));
                record.setDate(cursor.getString(1));;
                record.setAttended(cursor.getInt(2));
                record.setTopics(cursor.getString(3));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return classrecordlist;
    }

    /*-----------------------------------------------------------------------*/

    public Pair<Integer,Integer> getheldattended(String subjectname)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        String query="select * from "+table_dates+" where "+dates_subjectname+"=?";
        Cursor cursor=db.rawQuery(query,new String[] {subjectname});
        int held=cursor.getCount();

        int attended=0;
        if(cursor.moveToFirst())
        {
            do {
                if(cursor.getInt(2)==1)
                    attended++;

            }while (cursor.moveToNext());
        }

        cursor.close();
        return new Pair<>(held,attended);
    }
}