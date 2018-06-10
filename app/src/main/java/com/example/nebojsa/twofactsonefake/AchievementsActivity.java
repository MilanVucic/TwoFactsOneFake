package com.example.nebojsa.twofactsonefake;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AchievementsActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private ImageView  imageView1,imageView2,helplessImageView,imageView4,imageView5,imageView6,imageView7,imageView8;
    private TextView streakTextView, scoreTextView, playerTextView, helpfulTextView, helplessTextView;
    private EditText passwordEditText;
    private Button confirmButton;
    private LinearLayout hiddenLayout;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout();
            }
        });
        imageView5 = findViewById(R.id.imageView5);
        imageView8 = findViewById(R.id.imageView8);
        helplessImageView = findViewById(R.id.helplessImageView);
        passwordEditText= findViewById(R.id.passwordEditText);
        confirmButton= findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPassword();
            }
        });
        hiddenLayout= findViewById(R.id.hiddenLayout);

        streakTextView = findViewById(R.id.correctAnswerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        helpfulTextView = findViewById(R.id.helpfulTextView);
        helplessTextView = findViewById(R.id.helplessTextView);
        playerTextView = findViewById(R.id.playerTextView);
        prefs = this.getSharedPreferences("Achievements", Context.MODE_PRIVATE);

        if (prefs.getBoolean("achievementStreakGold",false))
        {
            streakTextView.setText("Completed");
            imageView1.setBackgroundResource(R.drawable.gold);
        }
        else
        {
            if (prefs.getBoolean("achievementStreakSilver",false))
            {
                streakTextView.setText("Get a streak of 12 for gold");
                imageView1.setBackgroundResource(R.drawable.silver);
            }
            else if (prefs.getBoolean("achievementStreakBronze",false))
            {
                streakTextView.setText("Get a streak of 9 for silver");
                imageView1.setBackgroundResource(R.drawable.bronze);
            }
        }

        if (prefs.getBoolean("achievementScoreGold",false))
        {
            scoreTextView.setText("Completed");
            imageView2.setBackgroundResource(R.drawable.gold);
        }
        else
        {
            if (prefs.getBoolean("achievementScoreSilver",false))
            {
                scoreTextView.setText("Get a score of 70 for gold");
                imageView2.setBackgroundResource(R.drawable.silver);
            }
            else if (prefs.getBoolean("achievementScoreBronze",false))
            {
                scoreTextView.setText("Get a score of 40 for silver");
                imageView2.setBackgroundResource(R.drawable.bronze);
            }
        }

        prefs = this.getSharedPreferences("gamesPlayed", Context.MODE_PRIVATE);
        if (prefs.getInt("gamesPlayed",0)>=80)
        {
            playerTextView.setText("Completed");
            imageView8.setBackgroundResource(R.drawable.gold);
        }
        else
        {
            if (prefs.getInt("gamesPlayed",0)>=40)
            {
                playerTextView.setText("Play 80 games for gold.");
                imageView8.setBackgroundResource(R.drawable.silver);
            }
            else  if (prefs.getInt("gamesPlayed",0)>=20)
            {
                playerTextView.setText("Play 40 games for silver.");
                imageView8.setBackgroundResource(R.drawable.bronze);
            }
        }

        prefs = this.getSharedPreferences("Achievements", Context.MODE_PRIVATE);
        if (prefs.getBoolean("helpsUsedGold",false))
        {
            helpfulTextView.setText("Completed");
            imageView5.setBackgroundResource(R.drawable.gold);
        }
        else
        {
            if (prefs.getBoolean("helpsUsedSilver",false))
            {
                helpfulTextView.setText(R.string.descriptionHelpsUsedGold);
                imageView5.setBackgroundResource(R.drawable.silver);
            }
            else  if (prefs.getBoolean("helpsUsedBronze",false))
            {
                helpfulTextView.setText(R.string.descriptionHelpsUsedSilver);
                imageView5.setBackgroundResource(R.drawable.bronze);
            }
        }
        prefs = this.getSharedPreferences("Achievements", Context.MODE_PRIVATE);
        if (prefs.getBoolean("achievementHelplessGold",false))
        {
            helplessTextView.setText("Completed");
            helplessImageView.setBackgroundResource(R.drawable.gold);
        }
        else
        {
            if (prefs.getBoolean("achievementHelplessSilver",false))
            {
                helplessTextView.setText(R.string.descriptionHelpsUsedGold);
                helplessImageView.setBackgroundResource(R.drawable.silver);
            }
            else  if (prefs.getBoolean("achievementHelplessBronze",false))
            {
                helplessTextView.setText(R.string.descriptionHelpsUsedSilver);
                helplessImageView.setBackgroundResource(R.drawable.bronze);
            }
        }


    }

    private void checkPassword() {
        String password = passwordEditText.getText().toString();
        if("0000a".equalsIgnoreCase(password)){
            findViewById(R.id.isi).setVisibility(View.VISIBLE);
        } else {
            passwordEditText.setError("Pogresna sifra :)");
        }
    }

    private void hiddenLayout() {
        if(++counter >= 10){
            hiddenLayout.setVisibility(View.VISIBLE);
        }
    }

}
