package com.example.usersdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

//        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
//        String email = sp.getString("Email", null);
//        String pass = sp.getString("Pass", null);
//        if (email != null && pass != null) {
//            signIn(email, pass);
//        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                createAccount(email, password);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               String email = loginEmail.getText().toString();
               String password = loginPassword.getText().toString();
               signIn(email, password);
           }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Registration succeded.",
                                    Toast.LENGTH_SHORT).show();
                            saveCredentials(email,password);
                            Intent intent = new Intent(LoginActivity.this, UserListActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication succeded.",
                                    Toast.LENGTH_SHORT).show();
                            saveCredentials(email,password);
                            Intent intent = new Intent(LoginActivity.this, UserListActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void saveCredentials(String email, String pass) {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Email",email);
        ed.putString("Pass",pass);
        ed.commit();
    }
}