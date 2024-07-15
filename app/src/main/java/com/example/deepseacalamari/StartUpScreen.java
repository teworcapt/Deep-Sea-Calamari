package com.example.deepseacalamari;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartUpScreen extends AppCompatActivity {

    Animation logoAnim;
    TextView logo,devName;

    private static final int SPLASH_SCREEN = 5000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoAnim = AnimationUtils.loadAnimation(this,R.anim.fade_animation);

        logo = findViewById(R.id.logo);
        devName = findViewById(R.id.dev_text);

        logo.setAnimation(logoAnim);
        devName.setAnimation(logoAnim);

        new Handler().postDelayed(() -> {
            Intent sharedIntent = new Intent(StartUpScreen.this,MainMenu.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this);
            startActivity(sharedIntent,options.toBundle());
        },SPLASH_SCREEN);
    }


}