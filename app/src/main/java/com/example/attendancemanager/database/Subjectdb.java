package com.example.attendancemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.attendancemanager.pojos.Subject;

public class Subjectdb extends SQLiteOpenHelper
{
    private static final int databaseversion=1;
    private static String databasename="attendmandb";
    private static String table_subjects="subjects";
    private static String subjects_name="subjectname";
    private static String subjects_techer="teachername";
    private static String subjects_credits="credits";

    public Subjectdb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databasename, null, databaseversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_subjects="create table "+table_subjects+"("+
                subjects_name+" varchar primary key,"+
                subjects_techer+" varchar,"+
                subjects_credits+" integer"+
                ");";

        db.execSQL(create_table_subjects);
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
            values.put(subjects_techer,subject.getTeacher());

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


}