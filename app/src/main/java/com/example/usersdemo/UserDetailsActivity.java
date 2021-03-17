package com.example.usersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {
    TextView detailsId;
    TextView detailsName;
    TextView detailsText;
    ImageView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        detailsId = (TextView) findViewById(R.id.detailsId);
        detailsName = (TextView) findViewById(R.id.detailsName);
        detailsText = (TextView) findViewById(R.id.detailsText);
        avatarView = (ImageView) findViewById(R.id.avatarView);

        Intent i = getIntent();

        Integer id = (Integer) i.getSerializableExtra("id");
        String name = (String) i.getSerializableExtra("name");
        String avatar = (String) i.getSerializableExtra("avatar");
        detailsId.setText("ID: " + id.toString());
        detailsName.setText(name);
        Picasso.get().load(avatar).into(avatarView);
    }
}