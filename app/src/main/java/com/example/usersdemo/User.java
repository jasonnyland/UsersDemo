package com.example.usersdemo;

import java.util.ArrayList;

public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<User> createUserList(int count) {
        ArrayList<User> users = new ArrayList<User>();

        for (int i = 1; i <= count; i++) {
            users.add(new User("User Number " + i));
        }

        return users;
    }
}
