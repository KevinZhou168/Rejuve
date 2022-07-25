package com.example.rejuve;

import static com.example.rejuve.MainActivity.firebaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Nourishment extends AppCompatActivity {

    int streak;
    int numCups;
    final int dailyRequirement = 8;
    boolean streakContinued = false; // check
    Button rewardButton;
    Button homeButton;
    Button logButton;
    TextView streakTV;
    TextView dailyTV;
    String streakOutput;
    String dailyOutput;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourishment);

        rewardButton = (Button) findViewById(R.id.reward);
        homeButton = (Button) findViewById(R.id.homeButton);
        logButton = (Button) findViewById(R.id.submit);

        rewardButton.setVisibility( View.GONE );
        homeButton.setVisibility( View.VISIBLE );
        logButton.setVisibility( View.VISIBLE );

        streakTV = findViewById(R.id.streak);
        dailyTV = findViewById(R.id.cupsLogged);

        // define streak from Firebase
        streak = firebaseHelper.getPaladin().getStreak();
        // define numCups from Firebase
        numCups = firebaseHelper.getPaladin().getDrinks();

        // display
        streakOutput = "Streak: " + streak;
        streakTV.setText(streakOutput);
        dailyOutput = "Daily Progress: " + numCups;
        dailyTV.setText(dailyOutput);

        firebaseHelper.getPaladin().setStreak(6); // imported firebaseHelper from MainActivity
        firebaseHelper.getPaladin().setDrinks(7);
        // ^for testing purposes

    }

    public void log(View v){
        EditText logET = findViewById(R.id.logET);
        String loggedCups = logET.getText().toString();
        int newDaily = numCups + Integer.parseInt(loggedCups);
        firebaseHelper.getPaladin().setDrinks(newDaily);
        // update display
        dailyOutput = "Daily Progress: " + newDaily;
        dailyTV.setText(dailyOutput);
        checkDaily(newDaily);
    }

    public void checkDaily(int total){
        if (total >= dailyRequirement){
            Toast.makeText(Nourishment.this, "Congratulations on meeting your daily goal! Remember to continue to remain hydrated throughout the day!", Toast.LENGTH_SHORT).show();
            streak++;
            firebaseHelper.getPaladin().setStreak(streak);
            streakContinued = true;
            checkStreak();
        }
        else {
            Toast.makeText(Nourishment.this, "You're on your way to reaching your daily goal!", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkStreak(){
        if (streak == 7){
            Toast.makeText(Nourishment.this, "Congratulations on your 7-day streak! Please proceed to claim your reward.", Toast.LENGTH_SHORT).show();
            streak = 0;
            firebaseHelper.getPaladin().setStreak(streak);
            homeButton.setVisibility( View.GONE );
            rewardButton.setVisibility(View.VISIBLE);
        }
    }

    public void returnHome(View v){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void proceedToReward(View v){
        Intent intent = new Intent(this, Reward.class);
        startActivity(intent);
    }

    // useless function atm
    public void dailyReset(View v){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final Context context = this;
        PendingIntent pi = PendingIntent.getService(context, 0,
                new Intent(context, Nourishment.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);

    }

    public void goToDashboard(View v) {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
    }

    public void goToLeaderboard(View v) {
        Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        mAuth.getInstance().signOut();

        Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(welcomeIntent);

    }

}