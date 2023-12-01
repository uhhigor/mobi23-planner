package com.example.mobi23_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobi23_planner.data.Task;

public class EditTaskActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etDateStart, etDateEnd, etTimeStart, etTimeEnd;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDateStart = findViewById(R.id.etDateStart);
        etDateEnd = findViewById(R.id.etDateEnd);
        etTimeStart = findViewById(R.id.etTimeStart);
        etTimeEnd = findViewById(R.id.etTimeEnd);

        btSave = findViewById(R.id.btSave);

        Task oldTask = (Task) getIntent().getSerializableExtra("oldTask");

        if(oldTask != null) {
            etTitle.setText(oldTask.getTitle());
            etDescription.setText(oldTask.getDescription());
            etDateStart.setText(oldTask.getDateStart());
            etDateEnd.setText(oldTask.getDateEnd());
            etTimeStart.setText(oldTask.getTimeStart());
            etTimeEnd.setText(oldTask.getTimeEnd());

            getIntent().putExtra("oldTaskId", oldTask.id);
        }

        btSave.setOnClickListener(v -> {
            Task newTask = new Task(
                    etTitle.getText().toString(),
                    etDescription.getText().toString(),
                    etDateStart.getText().toString(),
                    etDateEnd.getText().toString(),
                    etTimeStart.getText().toString(),
                    etTimeEnd.getText().toString(),
                    10, false
            );
            getIntent().putExtra("newTask", newTask);
            setResult(RESULT_OK, getIntent());
            finish();
        });

    }
}