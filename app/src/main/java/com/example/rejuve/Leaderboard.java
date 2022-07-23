package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Leaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.getPaladinsInGuild("2yudfosh");
    }
}