package com.example.nebojsa.twofactsonefake;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

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
                Intent intent = new Intent("com.example.nebojsa.twofactsonefake.StatsActivity");
                startActivity(intent);
            }
        });
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent("com.example.nebojsa.twofactsonefake.Gameplay");
                startActivity(intent);
            }
        });
        achievementsButton = (ImageButton) findViewById(R.id.achievementsButton);
        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent("com.example.nebojsa.twofactsonefake.AchievementsActivity");
                startActivity(intent);
            }
        });
        aboutButton = (ImageButton) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent("com.example.nebojsa.twofactsonefake.AboutActivity");
                startActivity(intent);
            }
        });
        suggestionButton = (ImageButton) findViewById(R.id.suggestionButton);
        suggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent intent = new Intent("com.example.nebojsa.twofactsonefake.FeedBackActivity");
                startActivity(intent);
            }
        });
    }

    private void exitGame() {
        buttonSound.start();
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("Exit");
        ab.setMessage("Are you sure you want to exit?");
        ab.setPositiveButton(R.string.yesyes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });
        ab.setNegativeButton(R.string.nono, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert);
        ab.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        prefs = this.getSharedPreferences("tokensNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tokenNumber", tokenNumber);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        exitGame();
    }

    private void addAds() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
