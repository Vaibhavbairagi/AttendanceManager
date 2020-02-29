package com.example.attendancemanager.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.attendancemanager.pojos.Subject;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Subject>> subjects;

    public HomeViewModel() {
        subjects = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Subject>> getSubjects() {
        return subjects;
    }

    public void setSubjects(MutableLiveData<ArrayList<Subject>> subjects) {
        this.subjects = subjects;
    }
}