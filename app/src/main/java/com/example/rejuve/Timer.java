package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//https://learntodroid.com/how-to-create-a-count-down-timer-in-android/

public class Timer extends AppCompatActivity {
    private EditText minutesEditText, secondsEditText;
    TextView countDownText;
    private Button start, end;
    private CountDownTimer timer;
    int startTime;
    int minutesLeft, secondsLeft;
    TextView minutesLeftText, secondsLeftText;
    int totalSecondsLeft;

    private void finishTimer(String message) {
        countDownText.setText(message);
        start.setEnabled(true);
        end.setEnabled(false);
    }

    private void updateTimeRemaining(long millisUntilFinished) {
        totalSecondsLeft = (int) millisUntilFinished / 1000;
        minutesLeft = (totalSecondsLeft % 3600) / 60;
        secondsLeft = totalSecondsLeft % 60;
        minutesLeftText.setText(String.format("%02d", minutesLeft));
        secondsLeftText.setText(String.format("%02d", secondsLeft));
        countDownText.setText("Count down in progress");
    }

    private void setupButtons() {
        start = findViewById(R.id.start_button);
        end = findViewById(R.id.end_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = 0;
                startTime += Integer.parseInt(secondsEditText.getText().toString()) * 1000;
                startTime += Integer.parseInt(minutesEditText.getText().toString()) * 60 * 1000;
                start.setEnabled(false);
                end.setEnabled(true);
                timer = new CountDownTimer(startTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        updateTimeRemaining(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        finishTimer("Count down complete");
                    }
                }.start();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finishTimer("Count down cancelled");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        minutesEditText = findViewById(R.id.minutes);
        secondsEditText = findViewById(R.id.seconds);

        countDownText = findViewById(R.id.countDownText);
        minutesLeftText = findViewById(R.id.minutesLeftText);
        secondsLeftText = findViewById(R.id.secondsLeftText);
        setupButtons();
    }
}