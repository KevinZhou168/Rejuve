package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    ArrayList<String> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        exerciseList = new ArrayList<String>();
        exerciseList.removeAll(exerciseList);

        stretchCheck = findViewById(R.id.stretchCB);
        jogCheck = findViewById(R.id.jogCB);
        pushCheck = findViewById(R.id.pushCB);
        jumpCheck = findViewById(R.id.jumpCB);
        weightsCheck = findViewById(R.id.weightsCB);
        equipmentCheck = findViewById(R.id.equipmentCB);
        otherCheck = findViewById(R.id.otherCB);

        stretch = findViewById(R.id.stretchText);
        jog = findViewById(R.id.jogText);
        push = findViewById(R.id.pushText);
        jump = findViewById(R.id.jumpText);
        weights = findViewById(R.id.weightsText);
        equipment = findViewById(R.id.equipmentText);
        other = findViewById(R.id.otherText);

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
                exerciseList.add(stretch.getText().toString());
                numChecks++;
            }
        }
    }
    public void ifJogChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (jogCheck.isChecked()) {
                exerciseList.add(jog.getText().toString());
                numChecks++;
            }
        }
    }
    public void ifPushChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (pushCheck.isChecked()) {
                exerciseList.add(push.getText().toString());
                numChecks++;
            }
        }
    }
    public void ifJumpChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (jumpCheck.isChecked()) {
                exerciseList.add(jump.getText().toString());
                numChecks++;
            }
        }
    }
    public void ifWeightsChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (weightsCheck.isChecked()) {
                exerciseList.add(weights.getText().toString());
                numChecks++;
            }
        }
    }
    public void ifEquipmentChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (equipmentCheck.isChecked()) {
                exerciseList.add(equipment.getText().toString());
                numChecks++;
            }
        }
    }
    public void ifOtherChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (otherCheck.isChecked()) {
                exerciseList.add(other.getText().toString());
                numChecks++;
            }
        }

    }

    public void ifChecked(){
        if(numChecks < 3){
            isClickable = true;
        }
        else{
            isClickable = true;
            Toast.makeText(Exercise.this, "Only 4 exercises can be selected", Toast.LENGTH_LONG).show();
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
        exerciseList.removeAll(exerciseList);

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
            String first = exerciseList.get(0);
            String second = exerciseList.get(1);
            String third = exerciseList.get(2);
            String fourth = exerciseList.get(3);

            Intent intent = new Intent(getApplicationContext(), Timer.class);

            intent.putExtra("one",first);
            intent.putExtra("two",second);
            intent.putExtra("three",third);
            intent.putExtra("four",fourth);
            startActivity(intent);
        }
        else{
            Toast.makeText(Exercise.this, "Choose 4 exercises to move on", Toast.LENGTH_LONG).show();
        }
    }







}
