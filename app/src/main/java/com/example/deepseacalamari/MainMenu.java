package com.example.deepseacalamari;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    Animation topAnim, bottomAnim, logoAnim;
    ImageView top,bottom;
    Button login,options;
    MediaPlayer mediaPlayer, buttonSfx;
    AudioManager audioManager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        top = findViewById(R.id.topTentacle);
        bottom = findViewById(R.id.bottomTentacle);

        top.setAnimation(topAnim);
        bottom.setAnimation(bottomAnim);

        buttonSfx = MediaPlayer.create(this,R.raw.button_sfx_2);
        mediaPlayer = MediaPlayer.create(this, R.raw.imperfect);
        mediaPlayer.start();

        //buttons
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenu.this,Login.class);
                buttonSfx.start();
                startActivity(intent);
            }
        });
        options = findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenu.this,OptionsScreen.class);
                buttonSfx.start();
                startActivity(intent);
            }
        });

        }
    }
