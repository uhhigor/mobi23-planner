package com.example.mobi23_planner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobi23_planner.data.DataManager;
import com.example.mobi23_planner.data.DataManagerListener;
import com.example.mobi23_planner.data.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> implements DataManagerListener {
    private List<Task> taskList;

    public TasksAdapter() {
        DataManager.getInstance().updateTasksList();
        this.taskList = DataManager.getInstance().getTasks();
        DataManager.getInstance().addListener(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.task_list_item, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);

        if(position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#F8F8F8"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2F2"));


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        boolean deadlineMissed = false;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateEnd = task.getDateEnd();
        try {
            Date date = format.parse(dateEnd);
            if (date != null) {
                calendar.setTime(date);

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                if (year < currentYear) {
                    deadlineMissed = true;
                } else if (year == currentYear) {
                    if (month < currentMonth) {
                        deadlineMissed = true;
                    } else if (month == currentMonth) {
                        if (day <= currentDay) {
                            deadlineMissed = true;
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (deadlineMissed) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFCDD2"));
        }

        TextView nameTextView = holder.tvName;
        nameTextView.setText(task.getTitle());

        TextView descriptionTextView = holder.tvDescription;
        descriptionTextView.setText(task.getDescription());

        TextView dateEndTextView = holder.tvDateEnd;
        dateEndTextView.setText(task.getDateEnd());

        ImageButton btTaskAction = holder.btTaskAction;

        TextView tvTime = holder.tvTime;
        int currentTime = task.getStepLengthMinutes() * task.getStepsDone();
        int totalTime = task.getStepLengthMinutes() * task.getStepGoal();
        tvTime.setText(currentTime + "/" + totalTime + " min");

        if (task.isDone()) {
            btTaskAction.setImageResource(R.drawable.ic_task_done);
            holder.itemView.setBackgroundColor(Color.parseColor("#C8E6C9"));
        } else {
            btTaskAction.setImageResource(R.drawable.ic_task_increment);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void tasksListChanged() {
        taskList = DataManager.getInstance().getTasks();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTaskList(List<Task> filteredTaskList) {
        this.taskList = filteredTaskList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvDateEnd;

        public ImageButton btTaskAction;

        public TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDateEnd = itemView.findViewById(R.id.tvDateEnd);
            btTaskAction = itemView.findViewById(R.id.btTaskAction);
            tvTime = itemView.findViewById(R.id.tvTime);

            btTaskAction.setOnClickListener(v -> {
                Task task = DataManager.getInstance().getTasks().get(getAdapterPosition());
                task.taskAction();
                DataManager.getInstance().updateTask(task.getId(), task);
            });
        }
    }
}

