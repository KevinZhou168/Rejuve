package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class JoinCode extends AppCompatActivity {
    private static ArrayList<String> existingCodes = new ArrayList<String>();
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_code);

        Intent intent = getIntent();
        uid = intent.getStringExtra("userUID");

        MainActivity.firebaseHelper.getCodes(new FirebaseHelper.CodesCallback() {
            @Override
            public void onCallback(ArrayList<String> codes) {
                existingCodes = codes;
                Log.i("LOG", "Got all codes in JoinCode");
            }
        });
    }

    public boolean codeExists(String code) {
        for(String c : existingCodes) {
            if(code.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public void signUpUser(View v) {
        EditText guildJoinCode = findViewById(R.id.guildJoinCode);
        EditText paladinName = findViewById(R.id.paladinNameET);
        String joinCode = guildJoinCode.getText().toString();
        String name = paladinName.getText().toString();
        if(codeExists(joinCode)) {
            MainActivity.firebaseHelper.addPaladinToFirebase(name, joinCode, uid);
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Join code doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }
}