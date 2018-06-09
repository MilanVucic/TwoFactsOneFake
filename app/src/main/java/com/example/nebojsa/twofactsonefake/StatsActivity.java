package com.example.nebojsa.twofactsonefake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {
    private TextView highScoreValueTextView, correctAnswersTextView, wrongAnswersTextView, gamesPlayedTextView, correctAnswersPercentageTextView, helpsUsedTextView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        highScoreValueTextView=(TextView)findViewById(R.id.highScoreValueTextView);
        correctAnswersTextView=(TextView)findViewById(R.id.correctAnswersValueTextView);
        wrongAnswersTextView=(TextView)findViewById(R.id.wrongAnswersValueTextView);
        gamesPlayedTextView=(TextView)findViewById(R.id.gamesPlayedTextView);
        correctAnswersPercentageTextView=(TextView)findViewById(R.id.correctAnswersPercentageTextView);
        helpsUsedTextView=(TextView)findViewById(R.id.helpsUsedTextView);

        prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
        highScoreValueTextView.setText(""+prefs.getInt("highScore", 0));

        prefs = this.getSharedPreferences("correctAnswers", Context.MODE_PRIVATE);
        int correctAnswers = prefs.getInt("correctAnswers", 0);
        correctAnswersTextView.setText(""+correctAnswers);

        prefs = this.getSharedPreferences("wrongAnswers", Context.MODE_PRIVATE);
        int wrongAnswers = prefs.getInt("wrongAnswers", 0);
        wrongAnswersTextView.setText(""+wrongAnswers);

        prefs = this.getSharedPreferences("gamesPlayed", Context.MODE_PRIVATE);
        gamesPlayedTextView.setText(""+prefs.getInt("gamesPlayed", 0));

        correctAnswersPercentageTextView.setText(""+String.format("%.2f",100.0*correctAnswers/(correctAnswers+wrongAnswers))+"%");

        prefs = this.getSharedPreferences("helpsUsed", Context.MODE_PRIVATE);
        helpsUsedTextView.setText(""+prefs.getInt("helpsUsed", 0));
    }

}
