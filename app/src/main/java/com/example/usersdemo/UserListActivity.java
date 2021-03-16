package com.example.usersdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView userRecycler;
    ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userRecycler = findViewById(R.id.userRecycler);

        users = User.createUserList(20);
        UserListAdapter adapter = new UserListAdapter(users);
        userRecycler.setAdapter(adapter);
        userRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}