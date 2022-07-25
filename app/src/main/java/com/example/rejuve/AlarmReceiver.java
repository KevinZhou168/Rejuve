package com.example.rejuve;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.getCode(new FirebaseHelper.CodeCallback() {
            @Override
            public void onCallback(String code) {
                firebaseHelper.getPaladinsInGuild(new FirebaseHelper.PaladinsCallback() {
                    @Override
                    public void onCallback(ArrayList<Paladin> paladinsAL) {
                        for(Paladin p : paladinsAL) {
                            firebaseHelper.dailyReset(p.getpUID(), code);
                        }
                    }
                }, code);
            }
        });
        Log.i("ALARM", "Alarm received before callback");
    }
}
