package com.example.rejuve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class Reward extends AppCompatActivity {

    Button spinButton;
    Button returnButton;
    ImageView ivWheel;

    private static final String [] sectors = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private static final int [] sectorDegrees = new int[sectors.length];
    Random random = new Random();
    private int degree = 0;
    private boolean isSpinning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        spinButton = (Button) findViewById(R.id.spinButton);
        returnButton = (Button) findViewById(R.id.returnHome);
        spinButton.setVisibility( View.VISIBLE );
        returnButton.setVisibility( View.GONE );

        ivWheel = findViewById(R.id.ivWheel);

        getDegreesForSectors();

        // on click listener for spinButton
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSpinning){
                    spin();
                    isSpinning = true;
                }
            }
        });
    }

    private void spin(){
        degree = random.nextInt(sectors.length-1);
        RotateAnimation rotateAnimation = new RotateAnimation(0, (360*sectors.length)+sectorDegrees[degree],
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation){

            }
            @Override
            public void onAnimationEnd(Animation animation){
                FirebaseHelper firebaseHelper = new FirebaseHelper();
                String pointsEarned = sectors[sectors.length - (degree+2)];
                Toast.makeText(Reward.this, "Congratulations! You earned " + pointsEarned + " points!", Toast.LENGTH_SHORT).show();
                // add firebase code
                int numPts = Integer.parseInt(pointsEarned);
                firebaseHelper.addPoints(numPts);
                spinButton.setVisibility( View.GONE );
                spinButton.setClickable(false); //apparently useless
                returnButton.setVisibility( View.VISIBLE );
                isSpinning = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivWheel.startAnimation(rotateAnimation);

    }

    private void getDegreesForSectors(){

        int sectorDegree = 360/sectors.length;
        for (int i=0; i < sectors.length; i++){
            sectorDegrees[i] = (i+1)*sectorDegree;
        }

    }

    public void returnHome(View v){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}