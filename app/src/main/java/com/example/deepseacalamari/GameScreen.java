package com.example.deepseacalamari;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameScreen extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    private AnimationDrawable blinkAnim;
    ImageView squidImage;

    private Calamari pet;
    private ProgressBar hungerProgressBar, hygieneProgressBar, funProgressBar, energyProgressBar;
    private Handler handler;
    private ImageView food, bath, play, sleep, settings;
    private ToggleButton sleepToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        squidImage =  findViewById(R.id.squid);

        squidImage.setAdjustViewBounds(true);
        squidImage.setScaleType(ImageView.ScaleType.CENTER);
        squidImage.setBackgroundResource(R.drawable.squid_blink_animation);
        blinkAnim = (AnimationDrawable)squidImage.getBackground();

        blinkAnim.start();

        //options button
        settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(GameScreen.this,OptionsScreen.class);
            startActivity(intent);
        }
    });

        //login database
        if (user == null){
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        }

        //pet database
        pet = new Calamari();
        hungerProgressBar = findViewById(R.id.hungerProgressBar);
        funProgressBar = findViewById(R.id.funProgressBar);
        hygieneProgressBar = findViewById(R.id.hygieneProgressBar);
        energyProgressBar = findViewById(R.id.energyProgressBar);
        food = findViewById(R.id.food);
        bath = findViewById(R.id.bath);
        play = findViewById(R.id.play);
        sleep = findViewById(R.id.sleep);
        sleepToggle = findViewById(R.id.sleepToggle);
        handler = new Handler();

        updateProgress();
        startHungerTimer();
        startHygieneTimer();
        startFunTimer();

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pet.feed();
                updateProgress();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pet.play();
                updateProgress();
            }
        });

        bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pet.bath();
                updateProgress();
            }
        });

        Runnable decreseEnergyRunnable = new Runnable() {
            @Override
            public void run() {
                if (!pet.isSleeping()) {
                    pet.decreaseEnergy();
                    updateProgress();
                    handler.postDelayed(this, 9000);
                }
            }
        };

        Runnable sleepRunnable = new Runnable() {
            @Override
            public void run() {
                if (pet.isSleeping()) {
                    pet.sleep();
                    updateProgress();
                    handler.postDelayed(this, 1000);
                }
            }
        };

        handler.post(decreseEnergyRunnable);

        sleepToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    sleep.setImageResource(R.drawable.ic_sleep_night);
                    pet.sleep();
                    handler.post(sleepRunnable);

                } else {
                    sleep.setImageResource(R.drawable.ic_sleep);
                    pet.WakeUp();
                    handler.removeCallbacks(sleepRunnable);
                }
                updateProgress();
            });
        }

    private void updateProgress() {
        hungerProgressBar.setProgress(pet.getHunger());
        hygieneProgressBar.setProgress(pet.getHygiene());
        funProgressBar.setProgress(pet.getFun());
        energyProgressBar.setProgress(pet.getEnergy());
    }

    private void startHungerTimer() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pet.decreaseHunger();
                updateProgress();
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }

    private void startHygieneTimer() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pet.decreaseHygiene();
                updateProgress();
                handler.postDelayed(this, 6000); //
            }
        }, 6000);
    }

    private void startFunTimer() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pet.decreaseFun();
                updateProgress();
                handler.postDelayed(this, 3000); //
            }
        }, 3000);
    }

}