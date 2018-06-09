package com.example.nebojsa.twofactsonefake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static MediaPlayer buttonSound;
    SharedPreferences prefs;
    private ImageButton playButton, statsButton, achievementsButton, aboutButton, suggestionButton;
    private TextView tokenNumberTextView;
    private int tokenNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tokenNumberTextView = (TextView) findViewById(R.id.tokenNumberTextView);
        buttonSound = MediaPlayer.create(getApplicationContext(), R.raw.correctsound);
        try {
            addOnClickListeners();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getTokens();
        addAds();

        if (getIntent().getBooleanExtra("EXIT", false))
            finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTokens();
    }

    private void getTokens() {
        prefs = this.getSharedPreferences("tokenNumber", Context.MODE_PRIVATE);
        tokenNumber = prefs.getInt("tokenNumber", 300);
        tokenNumberTextView.setText("" + tokenNumber);
    }

    private void addOnClickListeners() throws IOException {
//        exitButton=(Button)findViewById(R.id.exitGameButton);
//        exitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                         exitGame();
//            }
//        });
        statsButton = (ImageButton) findViewById(R.id.statsButton);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent(MainActivity.this, Gameplay.class);
                startActivity(intent);
            }
        });
        achievementsButton = (ImageButton) findViewById(R.id.achievementsButton);
        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
                startActivity(intent);
            }
        });
        aboutButton = (ImageButton) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        suggestionButton = (ImageButton) findViewById(R.id.suggestionButton);
        suggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent(MainActivity.this, FeedBackActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        prefs = this.getSharedPreferences("tokensNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tokenNumber", tokenNumber);
        editor.apply();
    }

    private void addAds() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
