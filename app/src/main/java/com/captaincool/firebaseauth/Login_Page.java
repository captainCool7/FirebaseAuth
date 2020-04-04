package com.captaincool.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login_Page extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,password;
    Button button,signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);
        email = findViewById(R.id.lemail);
        password = findViewById(R.id.lpassword);
        button = findViewById(R.id.login_btn);
        signUpButton = findViewById(R.id.sign_up_button);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            startActivity(new Intent(Login_Page.this,Homepage.class));
        }
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Page.this,MainActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        if (TextUtils.isEmpty(email.getText())) {
            email.setError("Username is required");
        } else if (TextUtils.isEmpty(password.getText())) {
            password.setError("Password is required");
        } else {
            String mail = email.getText().toString();
            String pass = password.getText().toString();
            mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("authApp", "createUserWithEmail:success");
                        startActivity(new Intent(Login_Page.this, Homepage.class));
                    } else {
                        Log.d("authApp", task.getResult().toString());
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
