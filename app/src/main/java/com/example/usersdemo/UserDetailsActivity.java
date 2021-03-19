package com.example.usersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {
    TextView detailsId;
    TextView detailsName;
    TextView detailsEmail;
    ImageView avatarView;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        detailsId = (TextView) findViewById(R.id.detailsId);
        detailsName = (TextView) findViewById(R.id.detailsName);
        detailsEmail = (TextView) findViewById(R.id.detailsEmail);
        avatarView = (ImageView) findViewById(R.id.avatarView);
        saveButton = (Button) findViewById(R.id.saveButton);

        Intent i = getIntent();

        Integer id = (Integer) i.getSerializableExtra("id");
        String name = (String) i.getSerializableExtra("name");
        String email = (String) i.getSerializableExtra("email");
        String avatar = (String) i.getSerializableExtra("avatar");
        detailsId.setText("ID: " + id.toString());
        detailsName.setText(name);
        detailsEmail.setText(email);
        Picasso.get().load(avatar).into(avatarView);

        if (id == 0) {
            saveButton.setEnabled(true);
            saveButton.setVisibility(View.VISIBLE);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("Name", detailsName.getText().toString());
                ed.putString("Email", detailsEmail.getText().toString());
                ed.commit();
                Toast.makeText(UserDetailsActivity.this, "Profile Updated",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}