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
    ArrayList exerciseList;

    TextView testText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        exerciseList = new ArrayList();
        exerciseList.removeAll(exerciseList);

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

        testText = findViewById(R.id.textView17);

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
                exerciseList.add(stretch);
                numChecks++;
                /**
                CharSequence testTextTwo = push.getText();
                testText.setText(testTextTwo);
                **/
            }
        }
    }
    public void ifJogChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (jogCheck.isChecked()) {
                exerciseList.add(jog);
                numChecks++;
            }
        }
    }
    public void ifPushChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (pushCheck.isChecked()) {
                exerciseList.add(push);
                numChecks++;
            }
        }
    }
    public void ifJumpChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (jumpCheck.isChecked()) {
                exerciseList.add(jump);
                numChecks++;
            }
        }
    }
    public void ifWeightsChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (weightsCheck.isChecked()) {
                exerciseList.add(weights);
                numChecks++;
            }
        }
    }
    public void ifEquipmentChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (equipmentCheck.isChecked()) {
                exerciseList.add(equipment);
                numChecks++;
            }
        }
    }
    public void ifOtherChecked(View v)
    {
        ifChecked();
        if(isClickable) {
            if (otherCheck.isChecked()) {
                exerciseList.add(other);
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
            Intent intent = new Intent(getApplicationContext(), Timer.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(Exercise.this, "Choose 4 exercises to move on", Toast.LENGTH_LONG).show();
        }
    }







}
