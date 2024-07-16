package com.example.deepseacalamari;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class StartUpScreen extends AppCompatActivity {

    Animation fadeUp, fadeIn;
    TextView funFact,devName;
    ImageView squidEgg;

    private static final int SPLASH_SCREEN = 5000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fadeUp = AnimationUtils.loadAnimation(this,R.anim.fade_animation);

        funFact = findViewById(R.id.funFact);
        devName = findViewById(R.id.dev_text);
        squidEgg = findViewById(R.id.squidEgg);

        funFact.setAnimation(fadeUp);
        devName.setAnimation(fadeUp);
        squidEgg.setAnimation(fadeUp);

        new Handler().postDelayed(() -> {
            Intent sharedIntent = new Intent(StartUpScreen.this,MainMenu.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this);
            startActivity(sharedIntent,options.toBundle());
            finish();
        },SPLASH_SCREEN);
    }


}