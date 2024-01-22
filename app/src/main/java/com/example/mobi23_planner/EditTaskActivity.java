package com.example.mobi23_planner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobi23_planner.data.Task;

import java.util.Calendar;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etDateEnd, etStepGoal, etStepLengthMinutes, etGroup;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etStepGoal = findViewById(R.id.etStepGoal);
        etDateEnd = findViewById(R.id.etDateEnd);
        etDateEnd.setOnClickListener(v -> showDatePickerDialog(etDateEnd));
        etStepLengthMinutes = findViewById(R.id.etStepLengthMinutes);
        etGroup = findViewById(R.id.etGroup);

        btSave = findViewById(R.id.btSave);

        Task oldTask = (Task) getIntent().getSerializableExtra("oldTask");

        if(oldTask != null) {
            etTitle.setText(oldTask.getTitle());
            etDescription.setText(oldTask.getDescription());
            etStepGoal.setText(oldTask.getStepGoal());
            etDateEnd.setText(oldTask.getDateEnd());
            etStepLengthMinutes.setText(oldTask.getStepLengthMinutes());
            etGroup.setText(oldTask.getGroup());

            getIntent().putExtra("oldTaskId", oldTask.id);
        }

        btSave.setOnClickListener(v -> {
            if(!validateInput())
                return;
            Task newTask = new Task(
                    etTitle.getText().toString(),
                    etDescription.getText().toString(),
                    etDateEnd.getText().toString(),
                    Integer.parseInt(etStepGoal.getText().toString()),
                    Integer.parseInt(etStepLengthMinutes.getText().toString()),
                    etGroup.getText().toString(),
                    false
            );
            getIntent().putExtra("newTask", newTask);
            setResult(RESULT_OK, getIntent());
            finish();
        });
    }
    private void showDatePickerDialog(EditText text) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                EditTaskActivity.this,
                (view, yearOfTask, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, monthOfYear + 1, yearOfTask);
                    text.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private boolean validateInput() {
        boolean valid = true;
        if (etTitle.getText().toString().isEmpty()) {
            etTitle.setError("Title cannot be empty");
            valid = false;
        }
        if (etDescription.getText().toString().isEmpty()) {
            etDescription.setError("Description cannot be empty");
            valid = false;
        }
        if (etStepGoal.getText().toString().isEmpty()) {
            etStepGoal.setError("Step goal cannot be empty");
            valid = false;
        }
        if (etDateEnd.getText().toString().isEmpty()) {
            etDateEnd.setError("Date end cannot be empty");
            valid = false;
        }
        if (etStepLengthMinutes.getText().toString().isEmpty()) {
            etStepLengthMinutes.setError("Step length cannot be empty");
            valid = false;
        }
        if (etGroup.getText().toString().isEmpty()) {
            etGroup.setError("Group cannot be empty");
            valid = false;
        }
        return valid;
    }
}