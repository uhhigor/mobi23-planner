package com.example.mobi23_planner;

import java.util.ArrayList;

public class Task {
    private String title;
    private String description;
    private String date;
    private String time;
    private String priority;
    private String status;

    public Task(String title, String description, String date, String time, String priority, String status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.status = status;
    }

    public Task() {
        this.title = "";
        this.description = "";
        this.date = "";
        this.time = "";
        this.priority = "";
        this.status = "";
    }

    // Test function to create a list of tasks
    public static ArrayList<Task> createTaskList(int num) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        for (int i = 1; i <= num; i++) {
            tasks.add(new Task("Task " + i, "Description " + i, "Date " + i, "Time " + i, "Priority " + i, "Status " + i));
        }

        return tasks;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public String getPriority() { return priority; }

    public String getStatus() { return status; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }

    public void setPriority(String priority) { this.priority = priority; }

    public void setStatus(String status) { this.status = status; }
}
