package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class viewDataFunctions {


public static void makeGraph(FirebaseUser user, String moonDayString, LineGraphSeries<DataPoint> series) {
        LineGraphSeries<DataPoint> seriesHere = new LineGraphSeries<>(new DataPoint[] {});
        seriesHere = series;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    String uid = "bob";
    uid=user.getUid();
    DocumentReference docIdRef = rootRef.collection("Human Metrics").document(uid).collection("Data").document(moonDayString);
    String finalUid = uid;
    docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "Document exists!");
                    rootRef.collection("Human Metrics").document(finalUid).get().addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(@NonNull Object o) {


                            int beer = 0;
                            int counter = (Integer.valueOf(rootRef.collection("Data").document(moonDayString).toString()));
                            DataPoint[] dataPoints = new DataPoint[counter]; // declare an array of DataPoint objects with the same size as your list

                            for (int i = 1; i < counter; i++) {
                                if ((rootRef.collection("Data").document(moonDayString).emp)
                                // add new DataPoint object to the array for each of your list entries
                                dataPoints[i] = new DataPoint(i, Double.parseDouble(document.get("Emotions"+i).toString())); // not sure but I think the second argument should be of type double
                                Log.d(TAG, "Graph point! " + i + document.get("Emotions"+i));
                                series.appendData(dataPoints[i],false,30,false);
                            }


                            //Toast.makeText(, "Got lights!", Toast.LENGTH_SHORT).show();

                       }

                      /* if (document.exists()) {
                            Log.d(TAG, "Document exists!");
                            rootRef.collection("Human Metrics").document(finalUid).collection("Data").document(moonDayString).get().addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(@NonNull Object o) {
                                    int beer = 0;
                                    int counter = (Integer.valueOf(document.get("Counter").toString()));
                                    DataPoint[] dataPoints = new DataPoint[counter]; // declare an array of DataPoint objects with the same size as your list
                                    for (int i = 1; i < counter; i++) {

                                        // add new DataPoint object to the array for each of your list entries
                                        dataPoints[i] = new DataPoint(i, Double.parseDouble(document.get("Emotions"+i).toString())); // not sure but I think the second argument should be of type double
                                        Log.d(TAG, "Graph point! " + i + document.get("Emotions"+i));
                                        series.appendData(dataPoints[i],false,30,false);
                                    }


                                    //Toast.makeText(, "Got lights!", Toast.LENGTH_SHORT).show();

                                }*/


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Log.d(TAG, "Document does not exist!");
                    //mFirestore.collection("RedGreen").document(uid).collection("Data").document(moonDayString).set(greenlights);
                }
            } else {
                Log.d(TAG, "Failed with: ", task.getException());
            }

        }

    });
}
}
