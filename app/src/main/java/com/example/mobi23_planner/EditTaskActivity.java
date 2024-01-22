package com.example.mobi23_planner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobi23_planner.data.DataManager;
import com.example.mobi23_planner.data.Task;

import java.util.Calendar;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etDateEnd, etStepGoal, etStepLengthMinutes;

    AutoCompleteTextView etGroup;
    Button btSave;
    Button btCancel;

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
        btCancel = findViewById(R.id.btCancel);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, DataManager.getInstance().getGroups());
        etGroup.setAdapter(adapter);
        Task oldTask = (Task) getIntent().getSerializableExtra("oldTask");

        if(oldTask != null) {
            etTitle.setText(oldTask.getTitle());
            etDescription.setText(oldTask.getDescription());
            etStepGoal.setText(String.valueOf(oldTask.getStepGoal()));
            etDateEnd.setText(oldTask.getDateEnd());
            etStepLengthMinutes.setText(String.valueOf(oldTask.getStepLengthMinutes()));
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

        btCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED, getIntent());
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

        // Title
        if (etTitle.getText().toString().isEmpty()) {
            etTitle.setError("Title cannot be empty");
            valid = false;
        }
        else if (etTitle.getText().toString().length() < 3) {
            etTitle.setError("Title must be at least 3 characters long");
            valid = false;
        }
        else if (etTitle.getText().toString().length() > 20) {
            etTitle.setError("Title cannot be longer than 20 characters");
            valid = false;
        }

        // Description
        if (etDescription.getText().toString().isEmpty()) {
            etDescription.setError("Description cannot be empty");
            valid = false;
        }
        else if (etDescription.getText().toString().length() < 3) {
            etDescription.setError("Description must be at least 3 characters long");
            valid = false;
        }
        else if (etDescription.getText().toString().length() > 80) {
            etDescription.setError("Description cannot be longer than 80 characters");
            valid = false;
        }

        // Step goal
        if (etStepGoal.getText().toString().isEmpty()) {
            etStepGoal.setError("Step goal cannot be empty");
            valid = false;
        }
        else if (Integer.parseInt(etStepGoal.getText().toString()) < 1) {
            etStepGoal.setError("Step goal must be at least 1");
            valid = false;
        }
        else if (Integer.parseInt(etStepGoal.getText().toString()) > 100) {
            etStepGoal.setError("Step goal cannot be greater than 100");
            valid = false;
        }

        // Date end
        if (etDateEnd.getText().toString().isEmpty()) {
            etDateEnd.setError("Date end cannot be empty");
            valid = false;
        }

        // Step length minutes
        if (etStepLengthMinutes.getText().toString().isEmpty()) {
            etStepLengthMinutes.setError("Step length cannot be empty");
            valid = false;
        }
        else if (Integer.parseInt(etStepLengthMinutes.getText().toString()) < 1) {
            etStepLengthMinutes.setError("Step length must be at least 1");
            valid = false;
        }
        else if (Integer.parseInt(etStepLengthMinutes.getText().toString()) > 120) {
            etStepLengthMinutes.setError("Step length cannot be greater than 100");
            valid = false;
        }

        // Group
        if (etGroup.getText().toString().isEmpty()) {
            etGroup.setError("Group name cannot be empty");
            valid = false;
        }
        else if (etGroup.getText().toString().length() < 3) {
            etGroup.setError("Group name must be at least 3 characters long");
            valid = false;
        }
        else if (etGroup.getText().toString().length() > 10) {
            etGroup.setError("Group name cannot be longer than 10 characters");
            valid = false;
        }
        return valid;
    }
}