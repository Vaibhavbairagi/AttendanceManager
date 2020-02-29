package com.example.attendancemanager.pojos;

public class Subject {
    private String name;
    private String teacher;
    private int credits;

    public Subject(String name, String teacher, int credits) {
        this.name = name;
        this.teacher = teacher;
        this.credits = credits;
    }

    public Subject(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public Subject(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public Subject(String name) {
        this.name = name;
    }
    public Subject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
