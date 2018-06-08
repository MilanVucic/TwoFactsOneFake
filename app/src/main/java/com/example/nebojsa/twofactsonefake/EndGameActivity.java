package com.example.nebojsa.twofactsonefake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class EndGameActivity extends AppCompatActivity {

    private TextView pointsWonTextView,highScoreTextView,newHighScoreTextView;
    private Button playAgainButton, mainMenuButton;
    private int highScore;
    private int pointsWon, gamesPlayed, helpsUsed;
    private static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        newHighScoreTextView=(TextView)findViewById(R.id.newHighScoreTextView);
        pointsWonTextView=(TextView)findViewById(R.id.pointsWonTextView);
        highScoreTextView=(TextView)findViewById(R.id.highScoreTextView);
        playAgainButton=(Button)findViewById(R.id.playAgainButton);
        mainMenuButton=(Button)findViewById(R.id.mainMenuButton);

        SharedPreferences prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
        highScore = prefs.getInt("highScore", 0);
        pointsWon = getIntent().getIntExtra("Points", 0);
        helpsUsed=getIntent().getIntExtra("HelpsUsedThisGame",0);
        if (updateHighScore())
            newHighScoreTextView.setVisibility(View.VISIBLE);

        pointsWonTextView.setText("Points Won: " + pointsWon);
        highScoreTextView.setText("High Score: " + highScore);

        prefs = this.getSharedPreferences("gamesPlayed", Context.MODE_PRIVATE);
        gamesPlayed=prefs.getInt("gamesPlayed", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("gamesPlayed", ++gamesPlayed);
        editor.apply();

        AddOnClickListeners();
        addTokens();
        addAds();
        prefs = this.getSharedPreferences("Achievements", Context.MODE_PRIVATE);
        Log.e("Usao",""+helpsUsed+" "+pointsWon+" "+!prefs.getBoolean("achievementHelplessBronze", false));
        checkAchievements();
    }

    private void AddOnClickListeners()
    {
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(EndGameActivity.this, Gameplay.class);
                startActivity(intent);
            }
        });
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean updateHighScore()
    {
        if (pointsWon>highScore)
        {
            highScore=pointsWon;
            prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", highScore);
            editor.commit();
            return true;
        }
        return false;

    }
    @Override
    protected void onStop(){
        super.onStop();
        prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highScore", highScore);
        editor.commit();

    }
    private void addTokens()
    {
        prefs = this.getSharedPreferences("tokenNumber", Context.MODE_PRIVATE);
        int tokenNumber = prefs.getInt("tokenNumber", 0);
        tokenNumber+=(pointsWon*3);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tokenNumber", tokenNumber);
        editor.commit();
    }
    private void addAds()
    {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private void checkAchievements()
    {
        prefs = this.getSharedPreferences("Achievements", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if ((helpsUsed == 0) && (pointsWon>9) && (!prefs.getBoolean("achievementHelplessBronze", false))) {
            editor.putBoolean("achievementStreakBronze", true);
            editor.apply();
        }
        else {
            if ((helpsUsed == 0) && (pointsWon > 19) && (!prefs.getBoolean("achievementHelplessSilver", false))) {
                editor.putBoolean("achievementStreakSilver", true);
                editor.apply();
            }
            else if ((helpsUsed == 0) && (pointsWon > 29) && (!prefs.getBoolean("achievementHelplessGold", false))) {
                editor.putBoolean("achievementStreakGold", true);
                editor.apply();
            }
        }
    }
}
