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
    private String group;
    private int priority;
    private boolean done;

    public Task(String title, String description, String dateStart, String dateEnd, String timeStart, String timeEnd, String group, int priority, boolean done) {
        this.title = title;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.group = group;
        this.priority = priority;
        this.done = done;
    }

    public Task() {
    }

    public String getId() { return id; }


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

    public String getGroup() { return group; }

    public Boolean isDone() { return done; }

    public void setDone(Boolean done) { this.done = done; }
}
