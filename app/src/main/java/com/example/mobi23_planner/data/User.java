package com.example.mobi23_planner.data;

import java.io.Serializable;

public class User implements Serializable {

    private String uid;
    private String name;
    private String email;

    public User() {}

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() { return uid; }

    public String getName() { return name; }

    public String getEmail() { return email; }
}
