package com.example.attendancemanager.pojos;

import java.util.Date;

public class ClassRecord
{
    private String name;
    private String date;
    private int attended;
    private String topics;

    public ClassRecord(String name, String date, int attended) {
        this.name = name;
        this.date = date;
        this.attended = attended;
        /*
        to add record directly in homescreen as well as
        to change attended status of a particular date and subject in subject view
        */
    }

    public ClassRecord(String name, String date, String topics) {
        this.name = name;
        this.date = date;
        this.topics = topics;
        /*
        to add topics in subject view
         */
    }

    public ClassRecord() {
        /*
        used in setting a record value using db values and fetching all records in subject view
         */
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

}
