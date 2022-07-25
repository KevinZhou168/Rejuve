package com.example.rejuve;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHelper {
    private static String uid = null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static Paladin paladin;

    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getmAuth() { return mAuth; }

    public String getUid(){
        return uid;
    }

    public Paladin getPaladin() { return paladin; }

    public void updateUid(String uid) {
        FirebaseHelper.uid = uid;
    }

    public void addPaladinToFirebase(String name, String code, String newUID) {
        Map<String, Object> user = new HashMap<>();
        user.put("points", 0);
        user.put("drinks", 0);
        user.put("streak", 0);
        user.put("code", code);
        user.put("name", name);
        user.put("type", "paladin");
        user.put("streak status", false);
        user.put("exercise status", false);

        db.collection(newUID).document(newUID).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("LOG", "Paladin user added");
                        db.collection("Guilds").document(code).collection("Paladins").document(newUID).set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("LOG", "User added to guild: " + code);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("LOG", "error adding user to guild", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("LOG", "error adding paladin account", e);
                    }
                });
    }

    public void addGuildToFirebase(String code, String newUID) {
        Map<String, Object> user = new HashMap<>();
        user.put("code", code);
        user.put("type", "guild");

        db.collection(newUID).document(newUID).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("LOG", "Guild user added: " + code);
                        Map<String, Object> guild = new HashMap<>();
                        guild.put("code", code);
                        db.collection("Guilds").document(code).set(guild)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("LOG", "Guild added: " + code);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("LOG", "error adding guild", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("LOG", "error adding guild account", e);
                    }
                });
    }

    public void getCodes(CodesCallback codesCallback) {
        db.collection("Guilds").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> codes = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            String current = documentSnapshot.getString("code");
                            codes.add(current);
                        }
                        codesCallback.onCallback(codes);
                    }
                });
    }

    public interface CodesCallback {
        void onCallback(ArrayList<String> codes);
    }

    public void getData(FirestoreCallback firestoreCallback) {
        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getUid();
            db.collection(uid).document(uid).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                int points, drinks, streak;
                                String code = documentSnapshot.getString("code");
                                String name = documentSnapshot.getString("name");
                                points = (int)(Math.floor(documentSnapshot.getDouble("points")));
                                drinks = (int)(Math.floor(documentSnapshot.getDouble("drinks")));
                                streak = (int)(Math.floor(documentSnapshot.getDouble("streak")));
                                boolean streakIncremented = documentSnapshot.getBoolean("streak status");
                                boolean exercised = documentSnapshot.getBoolean("exercise status");
                                Paladin paladinGot = new Paladin(name, points, drinks, streak, code, streakIncremented, exercised);
                                firestoreCallback.onCallback(paladinGot);
                            }
                        }
                    });
        }
    }

    public interface FirestoreCallback {
        void onCallback(Paladin paladinGot);
    }

    public void attachDataToPaladin() {
        getData(new FirestoreCallback() {
            @Override
            public void onCallback(Paladin paladinGot) {
                paladin = new Paladin(paladinGot.getName(), paladinGot.getPoints(), paladinGot.getDrinks(), paladinGot.getStreak(), paladinGot.getGuildCode(), paladinGot.isStreakIncremented(), paladinGot.isExercised());
                Log.i("CHECK DAILY", "Inside attachDataToPaladin() " + paladin.toString());
            }
        });
    }

    public void addPoints(int points) {
        DocumentReference documentReference = db.collection(uid).document(uid);
        documentReference.update("points", paladin.getPoints() + points);
        documentReference = db.collection("Guilds").document(paladin.getGuildCode()).collection("Paladins").document(uid);
        documentReference.update("points", paladin.getPoints() + points);
        attachDataToPaladin();
    }

    public void setStreakIncremented(boolean status) {
        DocumentReference documentReference = db.collection(uid).document(uid);
        documentReference.update("streak status", status);
        documentReference = db.collection("Guilds").document(paladin.getGuildCode()).collection("Paladins").document(uid);
        documentReference.update("streak status", status);
        attachDataToPaladin();
    }

    public void setExercised(boolean status) {
        DocumentReference documentReference = db.collection(uid).document(uid);
        documentReference.update("exercise status", status);
        documentReference = db.collection("Guilds").document(paladin.getGuildCode()).collection("Paladins").document(uid);
        documentReference.update("exercise status", status);
        attachDataToPaladin();
    }

    public void getPaladinsInGuild(PaladinsCallback paladinsCallback, String guildCode) {
        Query myData = db.collection("Guilds").document(guildCode).collection("Paladins");
        myData.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                ArrayList<Paladin> paladinsAL = new ArrayList<>();
                for(DocumentSnapshot doc: documents) {
                    int points, drinks, streak;
                    String code = doc.getString("code");
                    String name = doc.getString("name");
                    points = (int)(Math.floor(doc.getDouble("points")));
                    drinks = (int)(Math.floor(doc.getDouble("drinks")));
                    streak = (int)(Math.floor(doc.getDouble("streak")));
                    Paladin paladinGot = new Paladin(doc.getId(), name, points, drinks, streak, code);
                    paladinsAL.add(paladinGot);
                }
                Collections.sort(paladinsAL);
                paladinsCallback.onCallback(paladinsAL);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("PALADIN", "error grabbing paladins in guild");
            }
        });
    }

    public interface PaladinsCallback {
        void onCallback(ArrayList<Paladin> paladinsAL);
    }

    public void setDrinks(int drinks) {
        DocumentReference documentReference = db.collection(uid).document(uid);
        documentReference.update("drinks", drinks);
        documentReference = db.collection("Guilds").document(paladin.getGuildCode()).collection("Paladins").document(uid);
        documentReference.update("drinks", drinks);
        attachDataToPaladin();
    }

    public void setStreak(int streak) {
        DocumentReference documentReference = db.collection(uid).document(uid);
        documentReference.update("streak", streak);
        documentReference = db.collection("Guilds").document(paladin.getGuildCode()).collection("Paladins").document(uid);
        documentReference.update("streak", streak);
        attachDataToPaladin();
    }

    public void getCode(CodeCallback codeCallback) {
        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getUid();
            db.collection(uid).document(uid).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String code = documentSnapshot.getString("code");
                                codeCallback.onCallback(code);
                            }
                        }
                    });
        }
    }

    public interface CodeCallback {
        void onCallback(String code);
    }

    public void dailyReset(String userUID, String code) {
        attachDataToPaladin();
        DocumentReference documentReference1 = db.collection(userUID).document(userUID);
        documentReference1.update("drinks", 0);
        documentReference1.update("exercise status", false);
        documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    boolean status = documentSnapshot.getBoolean("streak status");
                    if (!status){
                        documentReference1.update("streak status", false);
                    }
                }
            }
        });;
        DocumentReference documentReference = db.collection("Guilds").document(code).collection("Paladins").document(userUID);
        documentReference.update("drinks", 0);
        documentReference.update("exercise status", false);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    boolean status = documentSnapshot.getBoolean("streak status");
                    if (!status){
                        documentReference.update("streak status", false);
                    }
                }
            }
        });;

        attachDataToPaladin();
    }
}
