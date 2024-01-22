package com.example.mobi23_planner.data;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class Task implements Serializable {

    @DocumentId
    public String id;
    private String title;
    private String description;
    private String dateEnd;
    private int stepGoal;
    private int stepLengthMinutes;
    private String group;
    private boolean done;

    private int stepsDone = 0;

    public Task(String title, String description, String dateEnd, int stepGoal, int stepLengthMinutes, String group, boolean done) {
        this.title = title;
        this.description = description;
        this.dateEnd = dateEnd;
        this.stepGoal = stepGoal;
        this.stepLengthMinutes = stepLengthMinutes;
        this.group = group;
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


    public String getDateEnd() { return dateEnd; }

    public int getStepGoal() { return stepGoal; }

    public int getStepLengthMinutes() { return stepLengthMinutes; }

    public int getStepsDone() { return stepsDone; }

    public String getGroup() { return group; }

    public Boolean isDone() { return done; }

    public void setDone(Boolean done) { this.done = done; }

    public void incrementStepsDone() {
        this.stepsDone++;
        if (this.stepsDone >= this.stepGoal) {
            setDone(true);
        }
    }

    public void taskAction() {
        if (isDone()) {
            setDone(false);
            stepsDone = 0;
        } else {
            incrementStepsDone();
        }
    }

    public void setStepsDone(int stepsDone) {
        this.stepsDone = stepsDone;
    }
}
