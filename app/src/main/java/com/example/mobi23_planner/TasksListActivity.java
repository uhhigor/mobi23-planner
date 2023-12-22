package com.example.mobi23_planner;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
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
    Spinner spinner;
    List<Task> filteredTaskList;

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

        spinner = findViewById(R.id.sSpinner);
        List<String> groupsList = dm.getGroups();
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groupsList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                String selectedGroup = parent.getItemAtPosition(pos).toString();

                if (selectedGroup.equals("All")) {
                    filteredTaskList = dm.getTasks();
                } else {
                    filteredTaskList = dm.getTasksByGroup(selectedGroup);
                }
                adapter.setTaskList(filteredTaskList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


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
                if (e.getAction() == MotionEvent.ACTION_DOWN && childView != null) {
                    int position = rv.getChildAdapterPosition(childView);
                    Task task = dm.getTasks().get(position);

                    showContextMenu(childView, task);
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

            private void editTask(Task task) {
                Intent intent = new Intent(TasksListActivity.this, EditTaskActivity.class);
                intent.putExtra("oldTask", task);
                updateTaskActivity.launch(intent);
            }

            private void deleteTask(Task task) {
                dm.deleteTask(task.id);
                Toast.makeText(TasksListActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            }

            private void showContextMenu(View view, Task task) {
                PopupMenu popupMenu = new PopupMenu(TasksListActivity.this, view);
                popupMenu.inflate(R.menu.context_task_menu);
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_edit) {
                        editTask(task);
                        return true;
                    } else if (item.getItemId() == R.id.menu_delete) {
                        deleteTask(task);
                        return true;
                    } else {
                        return false;
                    }
                });
                popupMenu.show();
            }
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