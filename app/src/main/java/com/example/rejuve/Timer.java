package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//https://learntodroid.com/how-to-create-a-count-down-timer-in-android/

public class Timer extends AppCompatActivity {
    TextView countDownText;
    private Button start, end;
    private CountDownTimer timer;
    int startTime;
    int minutesLeft, secondsLeft;
    TextView minutesLeftText, secondsLeftText;
    int totalSecondsLeft;

    int counter;
    String exercise;
    boolean exerciseOrReward = true;

    LinearLayout timerLinearLayout;
    LinearLayout buttonLinearLayout;
    Button spinButton;

    String first;
    String second;
    String third;
    String fourth;

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
        whichExercise();
        countDownText.setText(exercise);
    }

    private void setupButtons() {

            start = findViewById(R.id.start_button);
            end = findViewById(R.id.end_button);
            if(exerciseOrReward) {
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timerLinearLayout.setVisibility(View.VISIBLE);

                        countDownText.setText(exercise);
                        startTime = 0;
                        startTime += Integer.parseInt("10") * 1000;
                        startTime += Integer.parseInt("00") * 60 * 1000;
                        start.setEnabled(false);
                        end.setEnabled(true);
                        timer = new CountDownTimer(startTime, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                updateTimeRemaining(millisUntilFinished);
                            }

                            @Override
                            public void onFinish() {
                                finishTimer("Break");
                                timerLinearLayout.setVisibility(View.INVISIBLE);
                                counter++;
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            first = extras.getString("one");
            second = extras.getString("two");
            third = extras.getString("three");
            fourth = extras.getString("four");
        }

        countDownText = findViewById(R.id.countDownText);
        minutesLeftText = findViewById(R.id.minutesLeftText);
        secondsLeftText = findViewById(R.id.secondsLeftText);
        setupButtons();

        timerLinearLayout = findViewById(R.id.timerLinearLayout);
        timerLinearLayout.setVisibility(View.VISIBLE);
        buttonLinearLayout = findViewById(R.id.buttonLinearLayout);
        buttonLinearLayout.setVisibility(View.VISIBLE);
        spinButton = findViewById(R.id.spinBtn);
        spinButton.setVisibility(View.INVISIBLE);

        countDownText.setText(first);
        counter = 0;
        exerciseOrReward = true;

    }

    private void whichExercise(){
        if(counter == 0){
            exercise = first;
        }
        if(counter == 1){
            exercise = second;
        }
        if(counter == 2){
            exercise = third;
        }
        if(counter == 3){
            exercise = fourth;
        }
        if(counter == 4){
            exerciseOrReward = false;
            buttonLinearLayout.setVisibility(View.INVISIBLE);
            timerLinearLayout.setVisibility(View.INVISIBLE);
            spinButton.setVisibility(View.VISIBLE);
        }
    }

    public void goToReward(View v){

            Intent intent = new Intent(getApplicationContext(), Reward.class);
            startActivity(intent);
    }
}