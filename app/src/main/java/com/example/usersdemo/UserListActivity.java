package com.example.usersdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView userRecycler;
    List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        users = new ArrayList<>();
        userRecycler = findViewById(R.id.userRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userRecycler.setLayoutManager(linearLayoutManager);
        volleyRequest();

    }


    public void volleyRequest() {
        users = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/users", null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray dataArray) {
                try {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject userData = dataArray.getJSONObject(i);
                        User user = new User();
                        user.setId(userData.getInt("id"));
                        user.setName(userData.getString("name"));
                        users.add(user);
                    }
                    UserListAdapter adapter = new UserListAdapter(users);
                    userRecycler.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserListActivity.this, "Error: "+error.networkResponse, Toast.LENGTH_SHORT).show();
                Log.d("ULA", error.toString());
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}