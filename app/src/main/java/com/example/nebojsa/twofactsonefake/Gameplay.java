package com.example.nebojsa.twofactsonefake;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Gameplay extends AppCompatActivity {

    public CountDownTimer tempTimer;
    public Toast toast;
    public LinearLayout linear;
    int pointsWon;
    Button[] buttons = new Button[3];
    TextView timerTextView, tokenTextView, pointsTextView;
    long timeRemaining;
    int RandomAnswer, helpsUsedThisGame;
    Game game = new Game();
    CountDownTimer timer;
    Random random;
    int levelCounter, helpsUsed;
    ProgressBar progressBar;
    ImageView mainCircleImageView, difficultyImageView;
    private int randomFact1, randomFact2, randomFake, currentStreak, randomDifficultyGenerator, levelDifficulty;
    private SharedPreferences prefs;
    private ImageButton tenSecondsImageButton, fiftyImageButton;
    private int[] noEasyFactsRepeat, noMediumFactsRepeat, noHardFactsRepeat, noEasyFakesRepeat, noMediumFakesRepeat, noHardFakesRepeat;
    private boolean randomfact1Check, randomfact2Check, randomfake, randomfactCheck, randomfakeCheck, noTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        mainCircleImageView = (ImageView) findViewById(R.id.mainCircleImageView);
        difficultyImageView = (ImageView) findViewById(R.id.difficultyImageView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);

        currentStreak = 0;
        helpsUsedThisGame = 0;
        noEasyFactsRepeat = new int[60];
        noEasyFakesRepeat = new int[30];
        noMediumFactsRepeat = new int[60];
        noMediumFakesRepeat = new int[30];
        noHardFactsRepeat = new int[60];
        noHardFakesRepeat = new int[30];

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setMax(60000);
        progressBar.setProgress(50000);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        tokenTextView = (TextView) findViewById(R.id.tokenTextView);
        tenSecondsImageButton = (ImageButton) findViewById(R.id.tenSecondsButton);
        fiftyImageButton = (ImageButton) findViewById(R.id.fiftyButton);

        buttons[0] = (Button) findViewById(R.id.buttonStatement1);
        buttons[1] = (Button) findViewById(R.id.buttonStatement2);
        buttons[2] = (Button) findViewById(R.id.buttonStatement3);
        linear = (LinearLayout) findViewById(R.id.LinearLayoutButtons);
        random = new Random(System.currentTimeMillis());

        prefs = this.getSharedPreferences("helpsUsed", Context.MODE_PRIVATE);
        helpsUsed = prefs.getInt("helpsUsed", 0);

        pointsWon = 0;
        levelCounter = 1;

        prefs = this.getSharedPreferences("tokenNumber", Context.MODE_PRIVATE);
        tokenTextView.setText("" + prefs.getInt("tokenNumber", 300));
        timeRemaining = Constants.STARTING_TIME_MILLIS;
        createCountDownTimer(0);
        if (!noTime)
            generateNewLevel();
        addOnClickListeners();
        //setLayouts();
    }

    private void generateNewLevel() {
        enableAll();
        makeButtonsVisible();
        checkAchievements();
        RandomAnswer = random.nextInt(3);
        randomDifficultyGenerator = random.nextInt(10);
        if ((levelCounter <= 2) || ((randomDifficultyGenerator < 2) && (levelCounter > 4))) {
            levelDifficulty = 1;
            String easyFacts = getEasyFacts();
            randomFact1 = Integer.parseInt(easyFacts.substring(0, easyFacts.indexOf(" ")));
            randomFact2 = Integer.parseInt(easyFacts.substring(easyFacts.indexOf(" ") + 1));
            randomFake = getEasyFake();
            if (RandomAnswer == 0) buttons[0].setText(Game.getEasyFakes()[randomFake]);
            else buttons[0].setText(Game.getEasyFacts()[randomFact1]);
            if (RandomAnswer == 1) buttons[1].setText(Game.getEasyFakes()[randomFake]);
            else {
                if (RandomAnswer == 0) buttons[1].setText(Game.getEasyFacts()[randomFact1]);
                else buttons[1].setText(Game.getEasyFacts()[randomFact2]);
            }
            if (RandomAnswer == 2) buttons[2].setText(Game.getEasyFakes()[randomFake]);
            else buttons[2].setText(Game.getEasyFacts()[randomFact2]);
        }
        if (((levelCounter > 2) && (levelCounter <= 4)) || (((randomDifficultyGenerator >= 2) && (randomDifficultyGenerator < 5)) && (levelCounter > 4))) {
            levelDifficulty = 2;
            String mediumFacts = getMediumFacts();
            randomFact1 = Integer.parseInt(mediumFacts.substring(0, mediumFacts.indexOf(" ")));
            randomFact2 = Integer.parseInt(mediumFacts.substring(mediumFacts.indexOf(" ") + 1));
            randomFake = getMediumFake();

            if (RandomAnswer == 0) buttons[0].setText(Game.getMediumFakes()[randomFake]);
            else buttons[0].setText(Game.getMediumFacts()[randomFact1]);
            if (RandomAnswer == 1) buttons[1].setText(Game.getMediumFakes()[randomFake]);
            else {
                if (RandomAnswer == 0) buttons[1].setText(Game.getMediumFacts()[randomFact1]);
                else buttons[1].setText(Game.getMediumFacts()[randomFact2]);
            }
            if (RandomAnswer == 2) buttons[2].setText(Game.getMediumFakes()[randomFake]);
            else buttons[2].setText(Game.getMediumFacts()[randomFact2]);
        }
        if ((levelCounter > 4) && ((randomDifficultyGenerator >= 5) && (randomDifficultyGenerator < 10))) {
            levelDifficulty = 3;
            String hardFacts = getHardFacts();
            randomFact1 = Integer.parseInt(hardFacts.substring(0, hardFacts.indexOf(" ")));
            randomFact2 = Integer.parseInt(hardFacts.substring(hardFacts.indexOf(" ") + 1));
            randomFake = getHardFake();

            if (RandomAnswer == 0) buttons[0].setText(Game.getHardFakes()[randomFake]);
            else buttons[0].setText(Game.getHardFacts()[randomFact1]);
            if (RandomAnswer == 1) buttons[1].setText(Game.getHardFakes()[randomFake]);
            else {
                if (RandomAnswer == 0) buttons[1].setText(Game.getHardFacts()[randomFact1]);
                else buttons[1].setText(Game.getHardFacts()[randomFact2]);
            }
            if (RandomAnswer == 2) buttons[2].setText(Game.getHardFakes()[randomFake]);
            else buttons[2].setText(Game.getHardFacts()[randomFact2]);
        }
        difficultyImageView.setImageResource(R.drawable.xx);
        if (levelDifficulty == 3)
            difficultyImageView.setImageResource(R.drawable.xxx);
        if (levelDifficulty == 1)
            difficultyImageView.setImageResource(R.drawable.x);
    }

    private void createCountDownTimer(long addition) {
        timer = new CountDownTimer(timeRemaining + addition, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("" + millisUntilFinished / 1000);
                timeRemaining = millisUntilFinished;
                progressBar.setProgress((int) timeRemaining);
            }

            @Override
            public void onFinish() {
                finish();
                Intent intent = new Intent(Gameplay.this, EndGameActivity.class);
                intent.putExtra("Points", pointsWon);
                intent.putExtra("HelpsUsedThisGame", helpsUsedThisGame);
                startActivity(intent);
            }

        }.start();
    }

    private void onCorrect() {
        currentStreak++;
        levelCounter++;
        if (currentStreak >= 3 && currentStreak % 3 == 0)
            pointsWon += 5;
        pointsWon += 2;
        timer.cancel();
        timer = null;
        createCountDownTimer(10000);
        generateNewLevel();
        pointsTextView.setText("" + pointsWon);
        prefs = this.getSharedPreferences("correctAnswers", Context.MODE_PRIVATE);
        int correctAnswer = prefs.getInt("correctAnswers", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("correctAnswers", ++correctAnswer);
        editor.apply();
    }

    private void onWrong() {
        currentStreak = 0;
        disableAll();
        makeButtonsInvisible();
        levelCounter++;
        if (pointsWon - 1 < 0)
            pointsWon = 0;
//        else
//        {
//            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(pointUpdate, "alpha",  1f, 0f);
//            fadeOut.setDuration(1500);
//            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(pointUpdate, "alpha", 0f, 1f);
//            fadeIn.setDuration(200);
//
//            final AnimatorSet mAnimationSet = new AnimatorSet();
//
//            mAnimationSet.play(fadeOut).after(fadeIn);
//            mAnimationSet.start();
//
//            animation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT,0.0f,
//                    TranslateAnimation.RELATIVE_TO_PARENT,0.0f,
//                    TranslateAnimation.RELATIVE_TO_PARENT,0.0f,
//                    TranslateAnimation.RELATIVE_TO_PARENT,0.7f);
//            animation.setDuration(1500);
//            animation.setFillAfter(true);
//            animation.setRepeatCount(-1);
//            animation.setRepeatMode(Animation.REVERSE);
//            pointUpdate.startAnimation(animation);
//            pointsWon -= 1;
//        }
        timer.cancel();
        timer = null;

        prefs = this.getSharedPreferences("wrongAnswers", Context.MODE_PRIVATE);
        int wrongAnswer = prefs.getInt("wrongAnswers", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("wrongAnswers", ++wrongAnswer);
        editor.apply();

        createToast();
        if (timeRemaining < 5000) {
            noTime = true;
            Log.e("Usao", "");
        }
        tempTimer = new CountDownTimer(3500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                createCountDownTimer(-5000);
                pointsTextView.setText("" + pointsWon);
                if (!noTime)
                    generateNewLevel();
            }
        };
        tempTimer.start();
    }

    public void createToast() {
        Context context = getApplicationContext();
        CharSequence text = "";
        if (levelDifficulty == 1) {
            text = Game.getEasyFakesAnswers()[randomFake];
        }
        if (levelDifficulty == 2) {
            text = Game.getMediumFakesAnswers()[randomFake];
        }
        if (levelDifficulty == 3) {
            text = Game.getHardFakesAnswers()[randomFake];
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        if (RandomAnswer == 0) {

            toast.setGravity(Gravity.TOP, 0, linear.getTop() + (linear.getBottom() - linear.getTop()) / 8);
        }
        if (RandomAnswer == 1) {
            toast.setGravity(Gravity.TOP, 0, linear.getTop() + (linear.getBottom() - linear.getTop()) / 3 + (linear.getBottom() - linear.getTop()) / 8);
        }
        if (RandomAnswer == 2) {
            toast.setGravity(Gravity.TOP, 0, linear.getTop() + (linear.getBottom() - linear.getTop()) / 3 * 2 + (linear.getBottom() - linear.getTop()) / 8);
        }
        toast.show();
    }

    public void createAchievementToast() {
        Context context = getApplicationContext();
        CharSequence text = "Achievement unlocked!";

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -380);
        toast.show();
    }

    private void disableAll() {
        buttons[0].setEnabled(false);
        buttons[1].setEnabled(false);
        buttons[2].setEnabled(false);
        tenSecondsImageButton.setEnabled(false);
        fiftyImageButton.setEnabled(false);
    }

    private void enableAll() {
        buttons[0].setEnabled(true);
        buttons[1].setEnabled(true);
        buttons[2].setEnabled(true);
        tenSecondsImageButton.setEnabled(true);
        fiftyImageButton.setEnabled(true);
    }

    private void makeButtonsInvisible() {
        if (RandomAnswer == 0) {
            buttons[1].setVisibility(View.INVISIBLE);
            buttons[2].setVisibility(View.INVISIBLE);
        }
        if (RandomAnswer == 1) {
            buttons[0].setVisibility(View.INVISIBLE);
            buttons[2].setVisibility(View.INVISIBLE);
        }
        if (RandomAnswer == 2) {
            buttons[0].setVisibility(View.INVISIBLE);
            buttons[1].setVisibility(View.INVISIBLE);
        }

    }

    private void makeButtonsVisible() {
        buttons[0].setVisibility(View.VISIBLE);
        buttons[1].setVisibility(View.VISIBLE);
        buttons[2].setVisibility(View.VISIBLE);
        fiftyImageButton.setAlpha(1f);
    }

    private boolean updateTokens(int cost) {
        prefs = this.getSharedPreferences("tokenNumber", Context.MODE_PRIVATE);
        int tokenNumber = prefs.getInt("tokenNumber", 1000);
        if (tokenNumber - cost < 0) return false;
        tokenNumber -= cost;
        tokenTextView.setText("" + tokenNumber);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tokenNumber", tokenNumber);
        editor.apply();
        return true;
    }

    private void addOnClickListeners() {
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RandomAnswer == 0)
                    onCorrect();
                else
                    onWrong();
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RandomAnswer == 1)
                    onCorrect();
                else
                    onWrong();
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RandomAnswer == 2)
                    onCorrect();
                else
                    onWrong();
            }
        });
        tenSecondsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateTokens(50)) {
                    tenSecondsImageButton.setEnabled(false);
                    cooldownTimer();
                    updateHelpsUsed();
                    checkAchievements();
                    timer.cancel();
                    timer = null;
                    createCountDownTimer(10000);
                } else createNotEnoughTokensToast();
            }
        });
        fiftyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateTokens(70)) {
                    fiftyImageButton.setEnabled(false);
                    threeToOneFadeOut();
                    updateHelpsUsed();
                    checkAchievements();
                    int RandomFact;
                    random = new Random(System.currentTimeMillis());
                    while ((RandomFact = random.nextInt(2)) == RandomAnswer) ;
                    buttons[RandomFact].setEnabled(false);
                    buttons[RandomFact].setVisibility(View.INVISIBLE);
                } else createNotEnoughTokensToast();
            }
        });

    }

    public void updateHelpsUsed() {
        ++helpsUsedThisGame;
        prefs = this.getSharedPreferences("helpsUsed", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("helpsUsed", ++helpsUsed);
        editor.apply();
    }

    private void checkAchievements() {
        prefs = this.getSharedPreferences("Achievements", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if ((currentStreak >= 7) && (!prefs.getBoolean("achievementStreakBronze", false))) {
            createAchievementToast();
            editor.putBoolean("achievementStreakBronze", true);
            editor.apply();
        }
        if ((currentStreak >= 9) && (!prefs.getBoolean("achievementStreakSilver", false))) {
            createAchievementToast();
            editor.putBoolean("achievementStreakSilver", true);
            editor.apply();
        }
        if ((currentStreak >= 12) && (!prefs.getBoolean("achievementStreakGold", false))) {
            createAchievementToast();
            editor.putBoolean("achievementStreakGold", true);
            editor.apply();
        }
        if ((pointsWon >= 25) && (!prefs.getBoolean("achievementScoreBronze", false))) {
            createAchievementToast();
            editor.putBoolean("achievementScoreBronze", true);
            editor.apply();
        }
        if ((pointsWon >= 40) && (!prefs.getBoolean("achievementScoreSilver", false))) {
            createAchievementToast();
            editor.putBoolean("achievementScoreSilver", true);
            editor.apply();
        }
        if ((pointsWon >= 70) && (!prefs.getBoolean("achievementScoreGold", false))) {
            createAchievementToast();
            editor.putBoolean("achievementScoreGold", true);
            editor.apply();
        }
        if ((helpsUsedThisGame >= 2) && (!prefs.getBoolean("helpsUsedBronze", false))) {
            createAchievementToast();
            editor.putBoolean("helpsUsedBronze", true);
            editor.apply();
        }
        if ((helpsUsedThisGame >= 4) && (!prefs.getBoolean("helpsUsedSilver", false))) {
            createAchievementToast();
            editor.putBoolean("helpsUsedSilver", true);
            editor.apply();
        }
        if ((helpsUsedThisGame >= 6) && (!prefs.getBoolean("helpsUsedGold", false))) {
            createAchievementToast();
            editor.putBoolean("helpsUsedGold", true);
            editor.apply();
        }
    }

    private void createNotEnoughTokensToast() {
        Context context = getApplicationContext();
        CharSequence text = "Not enough tokens!";

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -380);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (tempTimer != null) {
            tempTimer.cancel();
            tempTimer = null;
        }
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        finish();
    }

    private void cooldownTimer() {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(tenSecondsImageButton, "alpha", 1f, 0f);
        fadeOut.setDuration(300);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(tenSecondsImageButton, "alpha", 0f, 1f);
        fadeIn.setDuration(1700);
        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn).after(fadeOut);
        mAnimationSet.start();
        CountDownTimer timer1 = new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                tenSecondsImageButton.setEnabled(true);
            }

        }.start();
    }

    private void threeToOneFadeOut() {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fiftyImageButton, "alpha", 1f, 0f);
        fadeOut.setDuration(500);
        final AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.play(fadeOut);
        mAnimationSet.start();
    }

    private String getEasyFacts() {
        randomfact1Check = false;
        randomfact2Check = false;
        randomfactCheck = true;
        randomFact1 = random.nextInt(Game.getEasyFacts().length);
        randomFact2 = random.nextInt(Game.getEasyFacts().length);
        while (randomfactCheck) {
            int i;
            if (randomfact1Check) randomFact1 = random.nextInt(Game.getEasyFacts().length);
            if (randomfact2Check) randomFact2 = random.nextInt(Game.getEasyFacts().length);
            randomfact1Check = false;
            randomfact2Check = false;
            while (randomFact1 == randomFact2) {
                randomFact2 = random.nextInt(Game.getEasyFacts().length);
            }
            for (i = 0; i < 20; i++) {
                if (randomFact1 == noEasyFactsRepeat[i]) {
                    randomfact1Check = true;
                    break;
                }
                if (randomFact2 == noEasyFactsRepeat[i]) {
                    randomfact2Check = true;
                    break;
                }
            }
            if (i == 20) randomfactCheck = false;
        }
        for (int i = 19; i > 1; i--) {
            noEasyFactsRepeat[i] = noEasyFactsRepeat[i - 1];
        }
        noEasyFactsRepeat[0] = randomFact1;
        noEasyFactsRepeat[1] = randomFact2;
        return randomFact1 + " " + randomFact2;
    }

    private String getMediumFacts() {
        randomfact1Check = false;
        randomfact2Check = false;
        randomfactCheck = true;
        randomFact1 = random.nextInt(Game.getMediumFacts().length);
        randomFact2 = random.nextInt(Game.getMediumFacts().length);
        while (randomfactCheck) {
            int i;
            if (randomfact1Check) randomFact1 = random.nextInt(Game.getMediumFacts().length);
            if (randomfact2Check) randomFact2 = random.nextInt(Game.getMediumFacts().length);
            randomfact1Check = false;
            randomfact2Check = false;
            while (randomFact1 == randomFact2) {
                randomFact2 = random.nextInt(Game.getMediumFacts().length);
            }
            for (i = 0; i < 20; i++) {
                if (randomFact1 == noMediumFactsRepeat[i]) {
                    randomfact1Check = true;
                    break;
                }
                if (randomFact2 == noMediumFactsRepeat[i]) {
                    randomfact2Check = true;
                    break;
                }
            }
            if (i == 20) randomfactCheck = false;
        }
        for (int i = 19; i > 1; i--) {
            noMediumFactsRepeat[i] = noMediumFactsRepeat[i - 1];
        }
        noMediumFactsRepeat[0] = randomFact1;
        noMediumFactsRepeat[1] = randomFact2;
        return randomFact1 + " " + randomFact2;
    }

    private String getHardFacts() {
        randomfact1Check = false;
        randomfact2Check = false;
        randomfactCheck = true;
        randomFact1 = random.nextInt(Game.getHardFacts().length);
        randomFact2 = random.nextInt(Game.getHardFacts().length);
        while (randomfactCheck) {
            int i;
            if (randomfact1Check) randomFact1 = random.nextInt(Game.getHardFacts().length);
            if (randomfact2Check) randomFact2 = random.nextInt(Game.getHardFacts().length);
            randomfact1Check = false;
            randomfact2Check = false;
            while (randomFact1 == randomFact2) {
                randomFact2 = random.nextInt(Game.getHardFacts().length);
            }
            for (i = 0; i < 20; i++) {
                if (randomFact1 == noHardFactsRepeat[i]) {
                    randomfact1Check = true;
                    break;
                }
                if (randomFact2 == noHardFactsRepeat[i]) {
                    randomfact2Check = true;
                    break;
                }
            }
            if (i == 20) randomfactCheck = false;
        }
        for (int i = 19; i > 1; i--) {
            noHardFactsRepeat[i] = noHardFactsRepeat[i - 1];
        }
        noHardFactsRepeat[0] = randomFact1;
        noHardFactsRepeat[1] = randomFact2;
        return randomFact1 + " " + randomFact2;
    }

    private int getEasyFake() {
        randomFake = random.nextInt(Game.getEasyFakes().length);
        randomfakeCheck = true;
        randomfake = false;
        while (randomfakeCheck) {
            if (randomfake) randomFake = random.nextInt(Game.getEasyFakes().length);
            randomfake = false;
            int i;
            for (i = 0; i < 30; i++) {
                if (randomFake == noEasyFakesRepeat[i]) {
                    randomfake = true;
                    break;
                }
            }
            if (i == 30) randomfakeCheck = false;
        }
        for (int i = 29; i > 0; i--) {
            noEasyFakesRepeat[i] = noEasyFakesRepeat[i - 1];
        }
        noEasyFakesRepeat[0] = randomFake;
        return randomFake;
    }

    private int getMediumFake() {
        randomFake = random.nextInt(Game.getMediumFakes().length);
        randomfakeCheck = true;
        randomfake = false;
        while (randomfakeCheck) {
            if (randomfake) randomFake = random.nextInt(Game.getMediumFakes().length);
            randomfake = false;
            int i;
            for (i = 0; i < 30; i++) {
                if (randomFake == noMediumFakesRepeat[i]) {
                    randomfake = true;
                    break;
                }
            }
            if (i == 30) randomfakeCheck = false;
        }
        for (int i = 29; i > 0; i--) {
            noMediumFakesRepeat[i] = noMediumFakesRepeat[i - 1];
        }
        noMediumFakesRepeat[0] = randomFake;
        return randomFake;
    }

    private int getHardFake() {
        randomFake = random.nextInt(Game.getHardFakes().length);
        randomfakeCheck = true;
        randomfake = false;
        while (randomfakeCheck) {
            if (randomfake) randomFake = random.nextInt(Game.getHardFakes().length);
            randomfake = false;
            int i;
            for (i = 0; i < 30; i++) {
                if (randomFake == noHardFakesRepeat[i]) {
                    randomfake = true;
                    break;
                }
            }
            if (i == 30) randomfakeCheck = false;
        }
        for (int i = 29; i > 0; i--) {
            noHardFakesRepeat[i] = noHardFakesRepeat[i - 1];
        }
        noHardFakesRepeat[0] = randomFake;
        return randomFake;
    }

}