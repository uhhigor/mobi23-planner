package com.example.mobi23_planner;

import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobi23_planner.data.Task;

public class EditTaskActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etDateStart, etDateEnd, etTimeStart, etTimeEnd, etGroup;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDateStart = findViewById(R.id.etDateStart);
        etDateStart.setOnClickListener(v -> showDatePickerDialog(etDateStart));
        etDateEnd = findViewById(R.id.etDateEnd);
        etDateEnd.setOnClickListener(v -> showDatePickerDialog(etDateEnd));
        etTimeStart = findViewById(R.id.etTimeStart);
        etTimeStart.setOnClickListener(v -> showTimePickerDialog(etTimeStart));
        etTimeEnd = findViewById(R.id.etTimeEnd);
        etTimeEnd.setOnClickListener(v -> showTimePickerDialog(etTimeEnd));
        etGroup = findViewById(R.id.etGroup);

        btSave = findViewById(R.id.btSave);

        Task oldTask = (Task) getIntent().getSerializableExtra("oldTask");

        if(oldTask != null) {
            etTitle.setText(oldTask.getTitle());
            etDescription.setText(oldTask.getDescription());
            etDateStart.setText(oldTask.getDateStart());
            etDateEnd.setText(oldTask.getDateEnd());
            etTimeStart.setText(oldTask.getTimeStart());
            etTimeEnd.setText(oldTask.getTimeEnd());
            etGroup.setText(oldTask.getGroup());

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
                    etGroup.getText().toString(),
                    10, false
            );
            if (!isDateValid(etDateStart, etDateEnd, etTimeStart, etTimeEnd)) {
                return;
            }
            getIntent().putExtra("newTask", newTask);
            setResult(RESULT_OK, getIntent());
            finish();
        });
    }

    private boolean isDateValid(TextView startDate, TextView endDate, TextView startTime, TextView endTime) {
        if (endDate.getText().toString().compareTo(startDate.getText().toString()) < 0) {
            endDate.setError("");
            Toast.makeText(EditTaskActivity.this, "End date must be after start date", Toast.LENGTH_LONG).show();
            endDate.requestFocus();
            return false;
        }
        if(endDate.getText().toString().compareTo(startDate.getText().toString()) == 0) {
            if (endTime.getText().toString().compareTo(startTime.getText().toString()) < 0) {
                endTime.setError("");
                Toast.makeText(EditTaskActivity.this, "End time must be after start time", Toast.LENGTH_LONG).show();
                endTime.requestFocus();
                return false;
            }
        }
        return true;
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

    private void showTimePickerDialog(EditText text) {
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                EditTaskActivity.this,
                (view, hourOfDay, minuteOfTask) -> {
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfTask);
                    text.setText(selectedTime);
                },
                hour, minute, true);

        timePickerDialog.show();
    }
}