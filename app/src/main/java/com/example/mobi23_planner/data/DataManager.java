package com.example.mobi23_planner.data;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager INSTANCE;
    private FirebaseFirestore db;

    private List<Task> tasksList = new ArrayList<>();
    private User currentUser;

    private DocumentReference userDocument;
    private CollectionReference tasksCollection;

    private List<DataManagerListener> listeners = new ArrayList<>();

    public void addListener(DataManagerListener toAdd) {
        listeners.add(toAdd);
    }
    private DataManager() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Log.w(TAG, "User not logged in");
            return;
        }
        currentUser = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
        db = FirebaseFirestore.getInstance();
        userDocument = db.collection("users/").document(currentUser.getUid());
        tasksCollection = userDocument.collection("tasks");
    }

    public static DataManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public static void resetInstance() {
        INSTANCE = null;
        INSTANCE = new DataManager();
    }

    public void addTask(Task t) {
        t.id = String.valueOf(tasksList.size());
        tasksCollection.add(t).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Log.d(TAG,"Task added successfully");
                updateTasksList();
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

    public void createUserDocument() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            Log.w(TAG,"User not logged in");
            return;
        }
        String name = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        String uid = currentUser.getUid();

        User user = new User(uid, name, email);

        db.collection("users/").document(uid).set(user).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Log.d(TAG,"User document created successfully");
            }
            else {
                Log.w(TAG,"User document creation failure");
            }
        });

    }

    public void updateTask(String oldTaskId, Task newTask) {
        newTask.id = oldTaskId;
        tasksCollection.document(oldTaskId).set(newTask).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Log.d(TAG,"Task added successfully");
                updateTasksList();
            }
            else {
                Log.w(TAG,"Task adding failure");
            }
        });
    }
}
