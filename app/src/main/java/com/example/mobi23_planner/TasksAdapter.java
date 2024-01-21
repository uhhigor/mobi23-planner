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

import java.util.List;

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

        TextView nameTextView = holder.tvName;
        nameTextView.setText(task.getTitle());

        TextView descriptionTextView = holder.tvDescription;
        descriptionTextView.setText(task.getDescription());

        TextView dateEndTextView = holder.tvDateEnd;
        dateEndTextView.setText(task.getDateEnd());

        ImageButton btTaskAction = holder.btTaskAction;

        if (task.isDone()) {
            btTaskAction.setImageResource(R.drawable.ic_task_done);
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

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDateEnd = itemView.findViewById(R.id.tvDateEnd);
            btTaskAction = itemView.findViewById(R.id.btTaskAction);

            btTaskAction.setOnClickListener(v -> {
                Task task = DataManager.getInstance().getTasks().get(getAdapterPosition());
                task.taskAction();
                DataManager.getInstance().updateTask(task.getId(), task);
            });
        }
    }
}

