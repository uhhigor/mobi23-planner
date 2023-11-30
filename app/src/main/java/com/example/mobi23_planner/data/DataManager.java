package com.example.mobi23_planner.data;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataManager {
    private static DataManager INSTANCE;
    private FirebaseFirestore db;

    private List<Task> tasksList = new ArrayList<>();

    private DocumentReference userDocument;
    private CollectionReference tasksCollection;

    private List<DataManagerListener> listeners = new ArrayList<DataManagerListener>();

    public void addListener(DataManagerListener toAdd) {
        listeners.add(toAdd);
    }
    private DataManager() {

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Log.w(TAG,"User not logged in");
            return;
        }
        db = FirebaseFirestore.getInstance();
        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDocument = db.collection("users/").document(userUID);
        tasksCollection = userDocument.collection("tasks");
        updateTasksList();
    }

    public static DataManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public void addTask(Task task) {
        tasksCollection.add(task).addOnCompleteListener(task1 -> {
            if(task1.isSuccessful()) {
                Log.d(TAG,"Task added successfully");
            }
            else {
                Log.w(TAG,"Task adding failure");
            }
        });
    }

    public void updateTasksList() {
        tasksCollection.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                tasksList = new ArrayList<>();
                for(QueryDocumentSnapshot document : task.getResult()) {
                    Task t = document.toObject(Task.class);
                    tasksList.add(t);
                }
                notify_tasksListChanged();
            }
            else {
                Log.w(TAG,"Error getting documents", task.getException());
            }
        });
    }

    public void notify_tasksListChanged() {
        for (DataManagerListener listener : listeners) {
            listener.tasksListChanged();
        }
    }

    public List<Task> getTasks() {
        return tasksList;
    }
}
