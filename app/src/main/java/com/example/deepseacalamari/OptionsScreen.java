package com.example.deepseacalamari;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OptionsScreen extends AppCompatActivity {

    //Log Out
    FirebaseAuth auth;
    Button button;
    TextView textview;
    FirebaseUser user;

    //Sound Control
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    AudioManager audioManager;
    MediaPlayer buttonSfx, buttonSfx2;

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        buttonSfx = MediaPlayer.create(this,R.raw.button_sfx_2);
        buttonSfx2 = MediaPlayer.create(this,R.raw.button_sfx);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.LogOutBtn);
        textview = findViewById(R.id.email);
        user = auth.getCurrentUser();

        seekBar = findViewById(R.id.OptionsSeek);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);

        //Log Out
        if (user != null){
            textview.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(OptionsScreen.this, Login.class);
                startActivity(intent);
                buttonSfx2.start();
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Back Button
        backBtn = findViewById(R.id.goback);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                buttonSfx.start();
            }

        });


    }
}