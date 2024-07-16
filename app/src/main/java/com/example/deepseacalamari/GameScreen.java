package com.example.deepseacalamari;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

public class GameScreen extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    //Gameplay Variables
    private Calamari pet;
    private ProgressBar hungerProgressBar, hygieneProgressBar, funProgressBar, energyProgressBar;
    private Handler handler;
    private ImageView squid, food, bath, play, sleep, settings, overlayImage;
    private ToggleButton sleepToggle;
    private TextView levels;
    private FrameLayout rootLayout;

    //Sound FX
    MediaPlayer buttonSfx, buttonSfx2, eatingSfx, bathingSfx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        levels = findViewById(R.id.levels);
        buttonSfx = MediaPlayer.create(this,R.raw.button_sfx_2);
        buttonSfx2 = MediaPlayer.create(this,R.raw.button_sfx);
        eatingSfx = MediaPlayer.create(this,R.raw.squid_eat_sfx);
        bathingSfx = MediaPlayer.create(this,R.raw.squid_bath_sfx);
        overlayImage = findViewById(R.id.overlayImage);
        rootLayout = findViewById(R.id.overlay);

        //Tutorial Overlay
        overlayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootLayout.removeView(overlayImage);
                buttonSfx.start();
            }
        });

        //Options button
        settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(GameScreen.this,OptionsScreen.class);
            startActivity(intent);
            buttonSfx.start();
        }
    });

        //Login Database
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        //Pet Controller
        pet = new Calamari();
        hungerProgressBar = findViewById(R.id.hungerProgressBar);
        funProgressBar = findViewById(R.id.funProgressBar);
        hygieneProgressBar = findViewById(R.id.hygieneProgressBar);
        energyProgressBar = findViewById(R.id.energyProgressBar);

        squid = findViewById(R.id.squid);
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
                playAnimation(R.drawable.squid_eating);
                eatingSfx.start();
                updateProgress();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pet.play();
                playAnimation(R.drawable.squid_play);
                buttonSfx2.start();
                updateProgress();
            }
        });

        bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pet.bath();
                playAnimation(R.drawable.squid_bath);
                bathingSfx.start();
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
                    squid.setImageResource(R.drawable.squid_sleep);
                    setButtonsEnabled(false);
                    pet.sleep();
                    handler.post(sleepRunnable);
                } else {
                    sleep.setImageResource(R.drawable.ic_sleep);
                    squid.setImageResource(R.drawable.squid_idle);
                    setButtonsEnabled(true);
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
        levelUpIfNeeded();
    }

    //Levelling up when Maxed
    private void levelUpIfNeeded(){
        if (pet.isMaxLevel()) {
            Toast.makeText(this, "Congrats! You've reached the max level!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (hungerProgressBar.getProgress() == hungerProgressBar.getMax() &&
                hygieneProgressBar.getProgress() == hygieneProgressBar.getMax() &&
                funProgressBar.getProgress() == funProgressBar.getMax() &&
                energyProgressBar.getProgress() == energyProgressBar.getMax()) {
            pet.levelUp();
            Toast.makeText(this,"Calamari has grown up just a bit!", Toast.LENGTH_SHORT).show();

            if (pet.getLevel() > 1){
                scaleCalamariImage();
            }

            levels.setText("Level: " + pet.getLevel());
       }
    }

    private void scaleCalamariImage(){
        float scaleFactor = 2.0f +0.2f * (pet.getLevel() - 1);
        squid.setScaleX(scaleFactor);
        squid.setScaleY(scaleFactor);
    }

    //Needs decreasing by the timer

    private void startHungerTimer() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pet.decreaseHunger();
                updateProgress();
                levelUpIfNeeded();
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
                levelUpIfNeeded();
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
                levelUpIfNeeded();
                handler.postDelayed(this, 3000); //
            }
        }, 3000);
    }


    //Changing the GIFs when a button is played
    private void playAnimation(int gifResource) {
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), gifResource);
            squid.setImageDrawable(gifDrawable);

            setButtonsEnabled(false);

            final int ANIMATION_DURATION = 2000;

            handler.postDelayed(() -> {
                setButtonsEnabled(true);
                squid.setImageResource(R.drawable.squid_idle);
            }, ANIMATION_DURATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        food.setEnabled(enabled);
        bath.setEnabled(enabled);
        play.setEnabled(enabled);
        sleep.setEnabled(enabled);
        settings.setEnabled(enabled);
    }

}