package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class GuildDashboard extends AppCompatActivity {
    TextView guildCodeDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_dashboard);
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        guildCodeDisplay = findViewById(R.id.joinCodeDisplay);
        firebaseHelper.getCode(new FirebaseHelper.CodeCallback() {
            @Override
            public void onCallback(String code) {
                guildCodeDisplay.setText(code);
            }
        });
    }

    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();

        Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(welcomeIntent);

    }

    public void goToLeaderboard(View v) {
        Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }

    public void goToQuests(View v) {
        Intent intent = new Intent(getApplicationContext(), Challenges.class);
        startActivity(intent);
    }


}