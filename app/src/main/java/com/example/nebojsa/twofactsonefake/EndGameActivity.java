package com.example.nebojsa.twofactsonefake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nebojsa.twofactsonefake.utils.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class EndGameActivity extends AppCompatActivity {

    private TextView pointsWonTextView,highScoreTextView,newHighScoreTextView,tokensWonTextView, gameOverTextView, bonusTextView;
    private Button playAgainButton, mainMenuButton, bonusButton;
    private int highScore;
    private int pointsWon, gamesPlayed, helpsUsed;
    private static SharedPreferences prefs;
    private boolean indicator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        gameOverTextView= findViewById(R.id.gameOverTextView);
        tokensWonTextView= findViewById(R.id.tokensWonTextView);
        bonusTextView= findViewById(R.id.bonusTextView);
        newHighScoreTextView= findViewById(R.id.newHighScoreTextView);
        pointsWonTextView= findViewById(R.id.pointsWonTextView);
        highScoreTextView= findViewById(R.id.highScoreTextView);
        playAgainButton= findViewById(R.id.playAgainButton);
        mainMenuButton= findViewById(R.id.mainMenuButton);
        bonusButton= findViewById(R.id.bonusButton);

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
        addTokens(pointsWon * 3);
        tokensWonTextView.setText(tokensWonTextView.getText() + " " + pointsWon * 3);
        addAds();
        checkAchievements();
    }

    private void AddOnClickListeners()
    {
        gameOverTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicator = true;
            }
        });
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
        mainMenuButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(indicator) {
                    showBonusButton();
                }
                return true;
            }
        });
    }

    private void showBonusButton() {
        bonusButton.setVisibility(View.VISIBLE);
        bonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveBonus();
            }
        });
    }

    private void giveBonus() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean alreadyGotBonus = sharedPreferences.getBoolean(Constants.GOT_BONUS, false);
        if(!alreadyGotBonus) {
            addTokens(Constants.BONUS_TOKENS);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.GOT_BONUS, true);
            editor.apply();
        } else {
            bonusTextView.setText("Ne valja preterivati jebiga");
        }

        bonusTextView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bonusTextView.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private boolean updateHighScore()
    {
        if (pointsWon>highScore)
        {
            highScore=pointsWon;
            prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", highScore);
            editor.apply();
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
        editor.apply();

    }
    private void addTokens(int tokensToAdd)
    {
        prefs = this.getSharedPreferences("tokenNumber", Context.MODE_PRIVATE);
        int tokenNumber = prefs.getInt("tokenNumber", 0);
        tokenNumber+= tokensToAdd;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tokenNumber", tokenNumber);
        editor.apply();
    }
    private void addAds()
    {
        MobileAds.initialize(this,
                Constants.AD_UNIT_END_GAME);

        AdView mAdView = findViewById(R.id.adView);
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
