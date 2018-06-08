package com.example.nebojsa.twofactsonefake;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class FeedBackActivity extends AppCompatActivity {
    String message;
    RadioButton suggestionsRB, factRB, fakeRB;
    RadioGroup radioGroup;
    EditText messageEditText;
    ImageButton sendEmailImageButton;
    boolean sent;
    long time, nextTime;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        sent = false;
        suggestionsRB = (RadioButton) findViewById(R.id.suggestionsRadioButton);
        factRB = (RadioButton) findViewById(R.id.factRadioButton);
        fakeRB = (RadioButton) findViewById(R.id.fakeRadioButton);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        sendEmailImageButton = (ImageButton) findViewById(R.id.sendEmailImageButton);

        prefs = this.getSharedPreferences("time", Context.MODE_PRIVATE);
        nextTime = prefs.getLong("time", 0);

        messageEditText = (EditText) findViewById(R.id.editText);

        sendEmailImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=new Date().getTime();
                if (time > nextTime) {

                    message = messageEditText.getText().toString();
                    int selected = radioGroup.getCheckedRadioButtonId();

                    final String subject;
                    if (selected == 0)
                        subject = "Suggestion";
                    else if (selected == 1)
                        subject = "Fact";
                    else subject = "Fake";

                    Thread getInfo = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                GMailSender sender = new GMailSender("faceorfact@gmail.com", "1wa1wa1wa");
                                if (message.length() > 4) {
                                    sender.sendMail(subject, message, "faceorfact@gmail.com", "faceorfact@gmail.com");
                                    sent = true;
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Please write something.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                                sent = false;
                            }
                        }
                    });
                    getInfo.start();
                    try {
                        getInfo.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (sent) {
                        nextTime = new Date().getTime() + 3600000;
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putLong("time", nextTime);
                        editor.apply();
                        Toast toast = Toast.makeText(getApplicationContext(), "E-mail sent", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    } else {

                        Toast toast = Toast.makeText(getApplicationContext(), "Check your internet connection and try again.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else
                {
                    long x = (nextTime-time)/60000;
                    Toast toast = Toast.makeText(getApplicationContext(), "You need to wait at least another "+x+" minutes before sending another message.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
