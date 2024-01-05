package com.example.mobi23_planner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobi23_planner.data.DataManager;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private Button goToRegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);

        loginButton = findViewById(R.id.btLogin);
        goToRegisterButton = findViewById(R.id.btGoToRegister);

        goToRegisterButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Valid e-mail address is required");
                    emailEditText.requestFocus();
                } else {
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                }
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            DataManager.resetInstance();
                            startActivity(new Intent(LoginActivity.this, TasksListActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failure", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

}


