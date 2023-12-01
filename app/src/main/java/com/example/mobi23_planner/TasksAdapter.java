package com.example.mobi23_planner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(task.getTitle());

        TextView descriptionTextView = holder.descriptionTextView;
        descriptionTextView.setText(task.getDescription());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.tvName);
            descriptionTextView = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}

