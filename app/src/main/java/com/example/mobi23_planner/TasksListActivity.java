package com.example.mobi23_planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobi23_planner.data.DataManager;
import com.example.mobi23_planner.data.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class TasksListActivity extends AppCompatActivity {

    RecyclerView rvTasks;
    FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(TasksListActivity.this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_tasks_list);

        DataManager dm = DataManager.getInstance();
        TasksAdapter adapter = new TasksAdapter();

        rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(adapter);

        ActivityResultLauncher<Intent> updateTaskActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null) return;

                        dm.updateTask(data.getStringExtra("oldTaskId"), (Task) data.getSerializableExtra("newTask"));
                        Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
                    }
                });
        rvTasks.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if(childView != null) {
                    int position = rv.getChildAdapterPosition(childView);
                    Task task = dm.getTasks().get(position);
                    Intent intent = new Intent(TasksListActivity.this, EditTaskActivity.class);
                    intent.putExtra("oldTask", task);
                    updateTaskActivity.launch(intent);
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });

        ActivityResultLauncher<Intent> addTaskActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null) return;
                        dm.addTask((Task) data.getSerializableExtra("newTask"));
                        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                    }
                });

        fabAddTask = findViewById(R.id.fActionButton);
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TasksListActivity.this, EditTaskActivity.class);
            addTaskActivity.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            DataManager.getInstance().updateTasksList();
            Toast.makeText(this, "Tasks list updated", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            DataManager.resetInstance();
            startActivity(new Intent(TasksListActivity.this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}