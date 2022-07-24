package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Exercise extends AppCompatActivity {

    CheckBox stretchCheck;
    TextView stretch;
    CheckBox jogCheck;
    TextView jog;
    CheckBox pushCheck;
    TextView push;
    CheckBox jumpCheck;
    TextView jump;
    CheckBox weightsCheck;
    TextView weights;
    CheckBox equipmentCheck;
    TextView equipment;
    CheckBox otherCheck;
    TextView other;
    private Integer numChecks;
    private boolean isClickable;
    private boolean nextScreenTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        stretchCheck = findViewById(R.id.stretchCB);
        jogCheck = findViewById(R.id.jogCB);
        pushCheck = findViewById(R.id.pushCB);
        jumpCheck = findViewById(R.id.jumpCB);
        weightsCheck = findViewById(R.id.weightsCB);
        equipmentCheck = findViewById(R.id.equipmentCB);
        otherCheck = findViewById(R.id.otherCB);
        numChecks = 0;
        isClickable = false;
        nextScreenTimer = false;

        stretchCheck.setEnabled(true);
        jogCheck.setEnabled(true);
        pushCheck.setEnabled(true);
        jumpCheck.setEnabled(true);
        weightsCheck.setEnabled(true);
        equipmentCheck.setEnabled(true);
        otherCheck.setEnabled(true);

    }

    public void ifStretchChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (stretchCheck.isChecked()) {
                numChecks++;
            }
        }
    }
    public void ifJogChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (jogCheck.isChecked()) {
                numChecks++;
            }
        }
    }
    public void ifPushChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (pushCheck.isChecked()) {
                numChecks++;
            }
        }
    }
    public void ifJumpChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (jumpCheck.isChecked()) {
                numChecks++;
            }
        }
    }
    public void ifWeightsChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (weightsCheck.isChecked()) {
                numChecks++;
            }
        }
    }
    public void ifEquipmentChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (equipmentCheck.isChecked()) {
                numChecks++;
            }
        }
    }
    public void ifOtherChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (otherCheck.isChecked()) {
                numChecks++;
            }
        }
    }

    public void ifChecked(){
        if(numChecks < 3){
            isClickable = true;
        }
        else{
            Toast.makeText(Exercise.this, "Only 4 exercises can be selected", Toast.LENGTH_LONG).show();
            isClickable = false;
            stretchCheck.setEnabled(false);
            jogCheck.setEnabled(false);
            pushCheck.setEnabled(false);
            jumpCheck.setEnabled(false);
            weightsCheck.setEnabled(false);
            equipmentCheck.setEnabled(false);
            otherCheck.setEnabled(false);
            nextScreenTimer = true;
        }

    }

    public void reset(View v){
        numChecks = 0;
        isClickable = false;
        nextScreenTimer = false;

        stretchCheck.setChecked(false);
        jogCheck.setChecked(false);
        pushCheck.setChecked(false);
        jumpCheck.setChecked(false);
        weightsCheck.setChecked(false);
        equipmentCheck.setChecked(false);
        otherCheck.setChecked(false);

        stretchCheck.setEnabled(true);
        jogCheck.setEnabled(true);
        pushCheck.setEnabled(true);
        jumpCheck.setEnabled(true);
        weightsCheck.setEnabled(true);
        equipmentCheck.setEnabled(true);
        otherCheck.setEnabled(true);
    }

    public void goToTimer(View v){
        if(nextScreenTimer){
            Intent intent = new Intent(getApplicationContext(), Timer.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(Exercise.this, "Choose 4 exercises to move on", Toast.LENGTH_LONG).show();
        }
    }







}
