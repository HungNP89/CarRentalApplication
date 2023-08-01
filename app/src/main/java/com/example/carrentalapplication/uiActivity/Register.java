package com.example.carrentalapplication.uiActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapplication.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Register extends AppCompatActivity {
    //For interacting with create id design in XML
    Button btnSignUp, btnToSignIn;
    String username, email, phone, password, address;
    TextInputLayout Username, Email, Phone, Password, Address;
    ProgressBar progressBar;

    //For interacting with Firebase service
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    //For controlling email input
    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //initialize UI elements in the layout
        btnSignUp = findViewById(R.id.btn_SignUp);
        btnToSignIn = findViewById(R.id.toSignIn);
        Username = findViewById(R.id.textInputUserNameRegister);
        Email = findViewById(R.id.textInputEmailRegister);
        Address = findViewById(R.id.textInputAddressRegister);
        Phone = findViewById(R.id.textInputPhoneRegister);
        Password = findViewById(R.id.textInputPasswordRegister);
        progressBar = findViewById(R.id.progressBar);

        //clickable button to move to Log In screen
        btnToSignIn.setOnClickListener(view -> {
            Intent moveToSignIn = new Intent(Register.this, Login.class);
            startActivity(moveToSignIn);
            finish();
        });

        //setting up reference to interact with Firebase services
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users"); //Create table users in Firebase if it is not exist
        firebaseAuth = FirebaseAuth.getInstance();

        //save user information to Firebase
        btnSignUp.setOnClickListener(view -> {
            //Retrieve text input from user
            username = Username.getEditText().getText().toString().trim();
            email = Email.getEditText().getText().toString().trim();
            phone = Phone.getEditText().getText().toString().trim();
            address = Address.getEditText().getText().toString().trim();
            password = Password.getEditText().getText().toString().trim();

            //check validation of input
            if (isValid()) {
                //show progress when validation is true
                progressBar.setVisibility(View.VISIBLE);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        counter++;
                        progressBar.setProgress(counter);
                        if (counter == 100) {
                            timer.cancel();
                            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                                //create User table with input information in Firebase if successfully
                                if (task.isSuccessful()) {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("Username", username);
                                    hashMap.put("Email", email);
                                    hashMap.put("Phone", phone);
                                    hashMap.put("Address", address);
                                    hashMap.put("Password", password);
                                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).setValue(hashMap).addOnCompleteListener(task1 -> {
                                        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        firebaseUser.sendEmailVerification().addOnCompleteListener(task12 -> {
                                            //Send email to user and create dialog to notify user to verify email
                                            if (task12.isSuccessful()) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                                builder.setMessage("Sign Up success!\nPlease verify your email to continue. ");
                                                builder.setCancelable(false);
                                                builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                                    Intent intent = new Intent(Register.this, Login.class);
                                                    startActivity(intent);
                                                    finish();
                                                });
                                                progressBar.setVisibility(View.GONE);
                                                AlertDialog alertDialog = builder.create();
                                                alertDialog.show();
                                            } else {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(Register.this, "Errors", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    });
                                }
                            });
                        }
                    }
                };

                timer.schedule(timerTask, 50, 50);
            }
        });
    }

    private boolean isValid() {
        //Set condition for each input in Register
        Username.setErrorEnabled(false);
        Username.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Phone.setErrorEnabled(false);
        Phone.setError("");
        Address.setErrorEnabled(false);
        Address.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");

        boolean isValid = false, isValidAddress = false, isValidUsername = false, isValidEmail = false, isValidPhone = false, isValidPassword = false;

        if (TextUtils.isEmpty(username)) {
            Username.setErrorEnabled(true);
            Username.setError("Please enter your username");
        } else {
            isValidUsername = true;
        }
        if (TextUtils.isEmpty(email)) {
            Email.setErrorEnabled(true);
            Email.setError("Please enter your email");
        } else {
            if (email.matches(pattern)) {
                isValidEmail = true;
            } else {
                Email.setErrorEnabled(true);
                Email.setError("Please enter valid email");
            }
        }
        if (TextUtils.isEmpty(phone)) {
            Phone.setErrorEnabled(true);
            Phone.setError("Please enter your phone number");
        } else {
            isValidPhone = true;
        }
        if (TextUtils.isEmpty(address)) {
            Address.setErrorEnabled(true);
            Address.setError("Please enter your address");
        } else {
            isValidAddress = true;
        }
        if (TextUtils.isEmpty(password)) {
            Password.setErrorEnabled(true);
            Password.setError("Please enter your password");
        } else {
            if (password.length() < 10) {
                Password.setErrorEnabled(true);
                Password.setError("Password requires 10 characters or more");
            } else {
                isValidPassword = true;
            }
        }

        isValid = isValidAddress && isValidUsername && isValidEmail && isValidPhone && isValidPassword;
        return isValid;
    }
}