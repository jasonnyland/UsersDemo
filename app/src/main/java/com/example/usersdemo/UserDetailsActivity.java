package com.example.usersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserDetailsActivity extends AppCompatActivity {
    private TextView detailsId;
    private TextView detailsName;
    private TextView detailsText;
    private ImageView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        detailsId = findViewById(R.id.detailsId);
        detailsName = findViewById(R.id.detailsName);
        detailsText = findViewById(R.id.detailsText);
        avatarView = findViewById(R.id.avatarView);
    }
}