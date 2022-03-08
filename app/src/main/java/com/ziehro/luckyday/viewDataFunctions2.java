package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class viewDataFunctions2 {


    public static void makeGraph(FirebaseUser user, LineGraphSeries<DataPoint> series, ArrayList<BarEntry> yVals1) {
        LineGraphSeries<DataPoint> seriesHere = new LineGraphSeries<>(new DataPoint[] {});
        final ArrayList<BarEntry> yValsHere = new ArrayList<>();
        seriesHere = series;
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        String uid = "bob";
        uid=user.getUid();
        DataPoint[] dataPoints = new DataPoint[29];

        String finalUid = uid;
        CollectionReference reference = rootRef.collection("Human Metrics").document(finalUid).collection("Data");
        rootRef.collection("Data").orderBy("MoonDay", Query.Direction.ASCENDING);
        ArrayList<BarEntry> finalYVals = yVals1;
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //reference.orderBy("MoonDay", Query.Direction.ASCENDING);
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                reference.orderBy("Data", Query.Direction.ASCENDING);
                if (task.isSuccessful()) {
                    Double DataTotal = 0.0;
                    Log.d(TAG, "Graph point! " + "Yaaaahhhhhhooooooooooo");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DataTotal = 0.0;
                        Double counter = Double.parseDouble(document.get("Counter").toString());
                        if (document.exists()) {
                            Double average = 0.0;
                            Log.d(TAG, "Graph point! " + document + DataTotal);
                            for (int i = 0; i<counter; i++){
                                DataTotal = DataTotal + (Double.parseDouble(document.get("Emotions" + i).toString()));
                            }
                            average = DataTotal/counter;
                            int id = Integer.valueOf(document.getId());
                            dataPoints[id] = new DataPoint(id, average); // not sure but I think the second argument should be of type double
//                                    Log.d(TAG, "Graph point! " + g + document.get("Emotions" + g));
                            Double db = new Double(average);
                            float avgFloat = db.floatValue();

                            Log.d(TAG, "Logged " + id + "  counter = " + avgFloat + yVals1);
                            series.appendData(dataPoints[id], false, 30, false);
                            yVals1.add(new BarEntry(id, avgFloat));

                        }
                        else{
                            Log.d(TAG, "Document does not exist" + document);
                        }

                    }
                    Log.d(TAG, "Done query checking" + "Hiii" + finalYVals);

                } else{
                    Log.d(TAG, "Graph point! " + "NONONONONONONONONOONONONONONONON");
                }
            }

        });


      /*  reference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            Double DataTotal = 0.0;
                            DataTotal = 0.0;

                            for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                                Double counter = Double.parseDouble(document.get("Counter").toString());
                                Double average = 0.0;
                                Log.d(TAG, "Graph point! " + document + DataTotal);
                                for (int i = 0; i<counter; i++){
                                    DataTotal = DataTotal + (Double.parseDouble(document.get("Emotions" + i).toString()));
                                }
                                average = DataTotal/counter;
                                int id = Integer.valueOf(document.getId());
                                //dataPoints[id] = new DataPoint(id, average); // not sure but I think the second argument should be of type double
//                                    Log.d(TAG, "Graph point! " + g + document.get("Emotions" + g));
                                Double db = new Double(average);
                                float avgFloat = db.floatValue();

                                Log.d(TAG, "Logged " + id + "  counter = " + avgFloat + yVals1);
                                //series.appendData(dataPoints[id], false, 30, false);
                                yVals1.add(new BarEntry(id, avgFloat));
                            }
                        }else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });*/

    }




}
