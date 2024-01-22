package com.example.mobi23_planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainPageActivity extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(v -> {
            startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
        });

        buttonRegister.setOnClickListener(v -> {
            startActivity(new Intent(MainPageActivity.this, RegisterActivity.class));
        });
    }
}
