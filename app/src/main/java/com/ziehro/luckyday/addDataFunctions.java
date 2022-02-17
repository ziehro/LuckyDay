package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class addDataFunctions {

    private static FirebaseFirestore mFirestore;

    public static void postStress(String uid, String moonDayString, int progress) {

        mFirestore = FirebaseFirestore.getInstance();

        Map<String, Integer> stress = new HashMap<>();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d(TAG, "Document exists!");

                        double counter;

                        counter = document.getDouble("Counter");
                        String CounterUpper = "Stress"+(int)counter;
                        stress.put(CounterUpper, progress);
                        stress.put("Counter", (int)counter);

                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).update(CounterUpper, progress).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {
                                //Toast.makeText(addDataFragment.this.getActivity(), progress, Toast.LENGTH_LONG);

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).update("Counter", FieldValue.increment(1));
                    } else {
                        Log.d(TAG, "Document does not exist!");

                        double counter = 0;
                        stress.put("Stress"+(int)counter, progress);
                        stress.put("Counter",(int)counter);

                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).set(stress);
                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).update("Counter", FieldValue.increment(1));
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    public static void postEnergy(String uid, String moonDayString, int progress) {

        mFirestore = FirebaseFirestore.getInstance();

        Map<String, Integer> energy = new HashMap<>();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d(TAG, "Document exists!");

                        double counter;

                        counter = document.getDouble("Counter");
                        String CounterUpper = "Stress"+(int)counter;
                        energy.put(CounterUpper, progress);
                        energy.put("Counter", (int)counter);

                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).update(CounterUpper, progress).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {
                                //Toast.makeText(addDataFragment.this.getActivity(), progress, Toast.LENGTH_LONG);

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).update("Counter", FieldValue.increment(1));
                    } else {
                        Log.d(TAG, "Document does not exist!");

                        double counter = 0;
                        energy.put("Stress"+(int)counter, progress);
                        energy.put("Counter",(int)counter);

                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).set(energy);
                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).update("Counter", FieldValue.increment(1));
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }




}
