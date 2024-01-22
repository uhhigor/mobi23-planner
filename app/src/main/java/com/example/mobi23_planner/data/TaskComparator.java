package com.example.mobi23_planner.data;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if(o1.isDone() && !o2.isDone())
            return 1;
        else if(!o1.isDone() && o2.isDone())
            return -1;
        else
            return o1.getDateEnd().compareTo(o2.getDateEnd());
    }
}
