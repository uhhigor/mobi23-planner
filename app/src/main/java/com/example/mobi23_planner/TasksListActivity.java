package com.example.mobi23_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mobi23_planner.data.DataManager;
import com.example.mobi23_planner.data.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TasksListActivity extends AppCompatActivity {

    RecyclerView rvTasks;
    FloatingActionButton fabAddTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);

        DataManager dm = DataManager.getInstance();
        TasksAdapter adapter = new TasksAdapter();

        rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(adapter);

        rvTasks.setOnClickListener(v -> {
            Toast.makeText(this, "Tasks list updated", Toast.LENGTH_SHORT).show();
            dm.updateTasksList();
        });

        fabAddTask = findViewById(R.id.fActionButton);
        fabAddTask.setOnClickListener(v -> {
            Task newTask = new Task("New task" + Math.round(Math.random() * 100), "New task description", "2021-01-01", "12:00", "High", "To do");
            dm.addTask(newTask);
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        });
    }
}