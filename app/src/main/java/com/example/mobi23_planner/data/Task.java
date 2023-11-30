package com.example.mobi23_planner.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable {
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
