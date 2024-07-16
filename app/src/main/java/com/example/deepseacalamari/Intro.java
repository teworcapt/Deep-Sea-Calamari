package com.example.deepseacalamari;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Intro extends AppCompatActivity {

    Animation fadeUp, topAnim;
    TextView intro_1, intro_2, help;
    ImageView squidEgg, top;
    MediaPlayer buttonSfx;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        fadeUp = AnimationUtils.loadAnimation(this, R.anim.fade_animation);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        buttonSfx = MediaPlayer.create(this, R.raw.button_sfx);

        intro_1 = findViewById(R.id.intro_1);
        intro_2 = findViewById(R.id.intro_2);
        help = findViewById(R.id.help);
        squidEgg = findViewById(R.id.squidEgg);
        top = findViewById(R.id.topTentacle);

        startSequentialAnimations();

        top.setAnimation(topAnim);
        squidEgg.setAnimation(fadeUp);

        squidEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intro.this, GameScreen.class);
                buttonSfx.start();
                startActivity(intent);
                finish();
            }
        });
    }

    private void startSequentialAnimations() {
        final long delayBetweenAnimations = 2000;

        animateWithDelay(intro_1, R.anim.fade_animation, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateWithDelay(intro_2, R.anim.fade_animation, 0);
            }
        }, delayBetweenAnimations);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateWithDelay(help, R.anim.fade_animation, 0);
            }
        }, delayBetweenAnimations * 2);
    }

    private void animateWithDelay(final TextView textView, int animationResId, long delayMillis) {

        Animation animation = AnimationUtils.loadAnimation(this, animationResId);

        animation.setStartOffset(delayMillis);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        textView.startAnimation(animation);

    }
}