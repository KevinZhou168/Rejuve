package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        recyclerView = findViewById(R.id.recyclerView);
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.getPaladinsInGuild(new FirebaseHelper.PaladinsCallback() {
            @Override
            public void onCallback(ArrayList<Paladin> paladinsAL) {
                setAdapter(paladinsAL);
            }
        }, firebaseHelper.getPaladin().getGuildCode());
    }

    private void setAdapter(ArrayList<Paladin> paladinAL) {
        recyclerAdapter adapter = new recyclerAdapter(paladinAL);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    public void goToDashboard(View v) {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        mAuth.getInstance().signOut();

        Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(welcomeIntent);

    }
}