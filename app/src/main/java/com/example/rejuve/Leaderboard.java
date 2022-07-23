package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {
    private RecyclerView recyclerView;

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
}