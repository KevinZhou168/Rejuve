package com.example.rejuve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static FirebaseHelper firebaseHelper;
    private Spinner signUpSpinner;
    private boolean isPaladin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseHelper = new FirebaseHelper();
    }

    public void goToSignIn(View v) { setContentView(R.layout.sign_in_choose); }
    public void goToPaladinSignIn(View v) { setContentView(R.layout.sign_in); isPaladin = true;}
    public void goToGuildSignIn(View v) { setContentView(R.layout.sign_in_guild); isPaladin = false;}

    public void goToSignUp(View v) {
        setContentView(R.layout.sign_up);
        signUpSpinner = (Spinner) findViewById(R.id.signUpSpinner);

        ArrayAdapter<CharSequence> adaptersignUp = ArrayAdapter.createFromResource(this,
                R.array.paladinOrGuild, android.R.layout.simple_spinner_item);

        //https://developer.android.com/guide/topics/ui/controls/spinner
        adaptersignUp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        signUpSpinner.setAdapter(adaptersignUp);
    }

    public void signUp(View v) {
        String email, password, confPassword;
        EditText emailET = findViewById(R.id.emailSignUpET);
        EditText passwordET = findViewById(R.id.passSignUpET);
        EditText confPasswordET = findViewById(R.id.confPassSignUpET);
        signUpSpinner = findViewById(R.id.signUpSpinner);
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        confPassword = confPasswordET.getText().toString();
        String type = signUpSpinner.getSelectedItem().toString();
        if (email.length() == 0 || password.length() == 0 || confPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 char long", Toast.LENGTH_SHORT).show();
        } else if (!(password.equals(confPassword))){
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            firebaseHelper.getmAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Log.i("LOG", "Successfully signed up");

                                FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();

                                firebaseHelper.updateUid(user.getUid());

                                if(type.equals("Guild")) {
                                    String code = generateRandomCode();
                                    firebaseHelper.addGuildToFirebase(code, user.getUid());
                                    Intent intent = new Intent(getApplicationContext(), GuildDashboard.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), JoinCode.class);
                                    intent.putExtra("userUID", user.getUid());
                                    startActivity(intent);
                                }
                            } else {
                                Log.i("LOG", "Error signing up", task.getException());
                                // TODO: add error message (toast or something)
                            }
                        }
                    });
        }
    }

    public void signIn(View v) {
        String email, password;
        EditText emailET = findViewById(R.id.emailSignInET);
        EditText passwordET = findViewById(R.id.passSignInET);
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be at least 6 char long", Toast.LENGTH_SHORT).show();
        } else {
            firebaseHelper.getmAuth().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
                                firebaseHelper.updateUid(user.getUid());

                                if(isPaladin) {
                                    Toast.makeText(getApplicationContext(), "Signed In as Paladin", Toast.LENGTH_SHORT).show();
                                    firebaseHelper.attachDataToPaladin();
                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Signed In as Guild", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), GuildDashboard.class);
                                    startActivity(intent);
                                }
                            } else {
                                Log.i("LOG", "Error logging in", task.getException());
                                // TODO: add error message (toast or something)
                            }
                        }
                    });
        }
    }

    private String generateRandomCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        String code = "";
        for(int i = 0; i < 8; i++) {
            int randNum = (int)(Math.random() * 36);
            code += characters.charAt(randNum);
        }
        return code;
    }
}