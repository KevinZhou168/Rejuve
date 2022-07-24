package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void goToLeaderboard(View v) {
        Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }
    public void goToExercise(View v) {
        Intent intent = new Intent(getApplicationContext(), Exercise.class);
        startActivity(intent);
    }
    public void goToNourishment(View v) {
        Intent intent = new Intent(getApplicationContext(), Nourishment.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        mAuth.getInstance().signOut();

        Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(welcomeIntent);

    }
}