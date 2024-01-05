package com.example.mobi23_planner;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mobi23_planner.data.DataManager;
import com.example.mobi23_planner.data.DataManagerListener;

public class GroupSpinner implements DataManagerListener {

    Context context;
    Spinner spinner;
    public GroupSpinner(Context context, Spinner spinner) {
        this.context = context;
        this.spinner = spinner;
        DataManager.getInstance().addListener(this);
    }

    void setAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, DataManager.getInstance().getGroups());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void tasksListChanged() {
        setAdapter();
    }
}
