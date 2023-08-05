package com.example.carrentalapplication.uiActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.carrentalapplication.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    TextView SSText;
    LottieAnimationView animationView;
    Animation botAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //View
        SSText = findViewById(R.id.splash_screen_text);
        animationView = findViewById(R.id.lottie_animation);

        //Load Animation
        botAnimation = AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        //Set Animation for Text
        SSText.setAnimation(botAnimation);

        //modify transition between splash screen and login screen
        new Handler().postDelayed(() -> {
            Intent moveToLogin = new Intent(SplashScreen.this, Login.class);
            startActivity(moveToLogin);
        },3000);
    }
}