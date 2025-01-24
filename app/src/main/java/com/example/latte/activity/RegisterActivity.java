package com.example.latte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.latte.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends BaseActivity {

    private EditText emailField, passwordField;
    private Button btnSignUp, btnGoBack;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // This is now inside the content_frame

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.btn_SignUp);
        btnGoBack = findViewById(R.id.btn_go_back); // Initialize Go Back button

        // Set up the SignUp button
        btnSignUp.setOnClickListener(view -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign-up success
                                Toast.makeText(RegisterActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish(); // End RegisterActivity to prevent returning
                            } else {
                                // Sign-up failure
                                Toast.makeText(RegisterActivity.this, "Authentication failed: " + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        // Set up the Go Back to Login button
        btnGoBack.setOnClickListener(view -> {
            // Navigate back to LoginActivity
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish(); // End RegisterActivity to prevent navigation issues
        });
    }
}
