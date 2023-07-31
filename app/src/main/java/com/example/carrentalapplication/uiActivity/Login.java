package com.example.carrentalapplication.uiActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carrentalapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {
    //For interacting with created design ID in XML file
    Button btnToSignUp , btnSignIn, btnForgotPass, btnGoogle, btnFacebook;
    TextInputLayout Email, Password;
    ProgressBar progressBar;
    String email, password;

    //For interacting with Firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    GoogleSignInClient googleSignInClient;

    //For controlling Sign In input
    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int counter;

    int RC_SIGN_IN = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize UI elements in the layout
        btnToSignUp = findViewById(R.id.toSignUp);
        btnSignIn = findViewById(R.id.btn_SignIn);
        btnForgotPass = findViewById(R.id.btn_forgot);
        btnGoogle = findViewById(R.id.btn_google);
        btnFacebook = findViewById(R.id.btn_facebook);
        Email = findViewById(R.id.textInputEmail);
        Password = findViewById(R.id.textInputPassword);
        progressBar = findViewById(R.id.progressBarLogin);

        //Initialize firebase auth for Sign In
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("UsersGoogle");

        //clickable button to move to Sign Up Screen
        btnToSignUp.setOnClickListener(view -> {
            Intent moveToSignUp = new Intent(Login.this,Register.class);
            startActivity(moveToSignUp);
            finish();
        });

        //clickable button to move to Forgot Password Screen
        btnForgotPass.setOnClickListener(view -> {
            Intent moveToForgotPass = new Intent(Login.this, ForgotPassword.class);
            startActivity(moveToForgotPass);
            finish();
        });

        //Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();
        btnGoogle.setOnClickListener(view -> {
            Intent googleSignIn = googleSignInClient.getSignInIntent();
            startActivityForResult(googleSignIn,RC_SIGN_IN);
        });


        btnFacebook.setOnClickListener(view -> {

        });

        //clickable button to Sign In and move to Category Screen
        btnSignIn.setOnClickListener(view -> {
            email = Email.getEditText().getText().toString().trim();
            password = Password.getEditText().getText().toString().trim();

            //check validation of input
            if (isValid()) {
                //show progress when validation is true
                progressBar.setVisibility(View.VISIBLE);
                Timer time = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        counter++;
                        progressBar.setProgress(counter);
                        if(counter == 100) {
                            time.cancel();
                            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                                //check if user is already verify email or not
                                if(task.isSuccessful()) {
                                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                        builder.setMessage("Login Successfully");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                            Intent intent = new Intent(Login.this,Category.class);
                                            startActivity(intent);
                                            finish();
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    } else {
                                        Toast.makeText(Login.this,"Please verify your email first",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Login.this,"Wrong account",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                };
                time.schedule(timerTask, 50, 50);
            }
        });
    }

    private boolean isValid() {
        //Set condition for each input in Login
        Email.setErrorEnabled(false);
        Email.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");

        boolean isValid = false, isValidEmail = false, isValidPassword = false;
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
        if (TextUtils.isEmpty(password)) {
            Password.setErrorEnabled(true);
            Password.setError("Please enter your password");
        } else {
            isValidPassword = true;
        }

        isValid = isValidEmail && isValidPassword;
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseGoogleAuth(account.getIdToken());
            }  catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void firebaseGoogleAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Id",user.getUid());
                        map.put("Username",user.getDisplayName());
                        map.put("Email",user.getEmail());

                        reference.child(user.getUid()).setValue(map);

                        Handler handler = new Handler();
                        handler.postDelayed((Runnable) () -> {
                            Intent intent = new Intent(Login.this,Category.class);
                            startActivity(intent);
                            finish();
                        },2000);

                    } else {
                        Toast.makeText(Login.this,"Errors",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}