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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailEditText, passwordEditText, nameEditText;
    private Button registerButton;

    private Button goToLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        nameEditText = findViewById(R.id.etName);
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);

        registerButton = findViewById(R.id.btRegister);
        goToLoginButton = findViewById(R.id.btGoToLogin);

        goToLoginButton.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(name.isEmpty()) {
                nameEditText.setError("Name is required");
                nameEditText.requestFocus();
                return;
            }
            else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Valid e-mail address is required");
                emailEditText.requestFocus();
                return;
            }
            else if(password.isEmpty()) {
                passwordEditText.setError("Password is required");
                passwordEditText.requestFocus();
                return;
            }

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();

                            FirebaseUser currentUser = auth.getCurrentUser();
                            if(currentUser == null) {
                                Toast.makeText(RegisterActivity.this, "Error - not logged in after register", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            DataManager.resetInstance();
                            DataManager.getInstance().createUserDocument();

                            startActivity(new Intent(RegisterActivity.this, TasksListActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register failure", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

}


