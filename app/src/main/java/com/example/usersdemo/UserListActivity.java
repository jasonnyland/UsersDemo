package com.example.usersdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserListAdapter.ListItemClickListener {

    private RecyclerView userRecycler;
    private FloatingActionButton logoutButton;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        users = new ArrayList<>();
        userRecycler = findViewById(R.id.userRecycler);
        logoutButton = findViewById(R.id.logoutButton);

        //onclick for logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("Email", null);
                ed.putString("Pass",null);
                ed.commit();
                SharedPreferences sp2 = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor ed2 = sp2.edit();
                ed2.putString("Email", null);
                ed2.putString("Name",null);
                ed2.commit();
                Intent intent = new Intent(UserListActivity.this, LoginActivity.class);
                startActivity(intent);
                UserListActivity.this.finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userRecycler.setLayoutManager(linearLayoutManager);
        volleyRequest();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    public void sendNotification() {
        String msg = "Please Come Back!";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(UserListActivity.this, "My Notification");
        builder.setContentTitle("UsersDemo");
        builder.setContentText(msg);
        builder.setSmallIcon(android.R.drawable.stat_notify_error);
        builder.setAutoCancel(true);
        Intent intent = new Intent(UserListActivity.this,
                UserListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(UserListActivity.this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(UserListActivity.this);
        managerCompat.notify(1, builder.build());
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
                        user.setEmail(userData.getString("email"));
                        users.add(user);
                    }
                    //check for logged in user details
                    SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
                    String userName = sp.getString("Name", null);
                    String userEmail = sp.getString("Email", null);
                    if (userName != null && !userName.equals("")) {
                        User userMe = new User();
                        userMe.setId(0);
                        userMe.setName(userName);
                        userMe.setEmail(userEmail);
                        users.add(userMe);
                    }
                    UserListAdapter adapter = new UserListAdapter(users,UserListActivity.this::onListItemClicK);
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

    @Override
    public void onListItemClicK(int position) {
        Intent intent = new Intent(UserListActivity.this, UserDetailsActivity.class);
        intent.putExtra("id", users.get(position).getId());
        intent.putExtra("name", users.get(position).getName());
        intent.putExtra("email", users.get(position).getEmail());
        intent.putExtra("avatar", users.get(position).getAvatar());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        sendNotification();
    }
}