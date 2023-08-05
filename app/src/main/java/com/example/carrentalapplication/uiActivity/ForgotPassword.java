package com.example.carrentalapplication.uiActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carrentalapplication.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class ForgotPassword extends AppCompatActivity {
    //For interacting with design UI ID in XML file
    TextInputLayout Email;
    Button btnForgotPass , btnToLogin;
    ProgressBar progressBar;
    String emailReset;
    //For interacting with Firebase service
    FirebaseAuth firebaseAuth;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initialize UI element in the layout
        Email = findViewById(R.id.textInputEmail);
        btnForgotPass = findViewById(R.id.btn_forgot_pass);
        btnToLogin = findViewById(R.id.toSignIn);
        progressBar = findViewById(R.id.progressBarForgotPass);

        //Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //clickable button to reset password
        btnForgotPass.setOnClickListener(view -> {
            emailReset = Email.getEditText().getText().toString().trim();

            if(emailReset.isEmpty()) {
                Toast.makeText(ForgotPassword.this,"Please enter your registered email",Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                Timer time = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        counter ++;
                        progressBar.setProgress(counter);
                        if(counter == 100) {
                            time.cancel();
                            firebaseAuth.sendPasswordResetEmail(emailReset).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this, "Please check your email to reset password", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            });
                        }
                    }
                };
                time.schedule(timerTask, 50, 50);
            }
        });

        btnToLogin.setOnClickListener(view -> {
            Intent backToSignIn = new Intent(ForgotPassword.this, Login.class);
            startActivity(backToSignIn);
        });
    }
}