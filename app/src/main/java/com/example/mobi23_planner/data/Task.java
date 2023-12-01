package com.example.mobi23_planner.data;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class Task implements Serializable {

    @DocumentId
    public String id;
    private String title;
    private String description;
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;
    private int priority;
    private Boolean status;

    public Task(String title, String description, String dateStart, String dateEnd, String timeStart, String timeEnd, int priority, Boolean status) {
        this.title = title;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
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

    public String getDateStart() { return dateStart; }

    public String getDateEnd() { return dateEnd; }

    public String getTimeStart() { return timeStart; }

    public String getTimeEnd() { return timeEnd; }

    public int getPriority() { return priority; }

    public Boolean getStatus() { return status; }


    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setDateStart(String dateStart) { this.dateStart = dateStart; }

    public void setDateEnd(String dateEnd) { this.dateEnd = dateEnd; }

    public void setTimeStart(String timeStart) { this.timeStart = timeStart; }

    public void setTimeEnd(String timeEnd) { this.timeEnd = timeEnd; }

    public void setPriority(int priority) { this.priority = priority; }

    public void setStatus(Boolean status) { this.status = status; }
}
