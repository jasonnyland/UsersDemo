package com.example.usersdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        // check for saved login information; if it exists, sign in automatically
        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        String email = sp.getString("Email", null);
        String pass = sp.getString("Pass", null);
        if (email != null && pass != null) {
            signIn(email, pass);
        } else {
            goToLogin();
        }
    }

    // sign in function
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SplashActivity.this, "Authentication succeded.",
                                    Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
                            String userName = sp.getString("Name", null);
                            if (userName == null || userName.equals("")) {
                                Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                                startActivity(intent);
                                SplashActivity.this.finish();
                            } else {
                                Intent intent = new Intent(SplashActivity.this, UserListActivity.class);
                                startActivity(intent);
                                SplashActivity.this.finish();
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            goToLogin();
                        }
                    }
                });
    }

    private void goToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}