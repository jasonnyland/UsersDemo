package com.example.usersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText fullNameText;
    Button submitRegButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fullNameText = (EditText) findViewById(R.id.fullNameText);
        submitRegButton = (Button) findViewById(R.id.submitRegButton);

        submitRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user entered a name, throw toast and cancel if not
                String name = fullNameText.getText().toString();
                if (name == null || name.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Enter Your Details",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //get info from login details
                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                    String email = sp.getString("Email", null);
                    //save new details and change activity
                    SharedPreferences sp2 = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp2.edit();
                    ed.putString("Name", name);
                    ed.putString("Email", email);
                    ed.commit();
                    Intent intent = new Intent(RegistrationActivity.this, UserListActivity.class);
                    startActivity(intent);
                    RegistrationActivity.this.finish();
                }
            }
        });
    }
}