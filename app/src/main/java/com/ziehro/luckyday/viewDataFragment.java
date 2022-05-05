package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.ziehro.luckyday.whatDayIsIt.letterDay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class viewDataFragment extends Fragment {

    String greenLightCounter;
    String redLightCounter;
    public String uid = "boob";
    AdView mAdView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_data, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MobileAds.initialize(view.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ImageView greenLightIndicator = (ImageView) view.findViewById(R.id.greenLightIndicator);
        ImageView redLightIndicator = (ImageView) view.findViewById(R.id.redLightIndicator);
        ImageView moonPicture = (ImageView)view.findViewById(R.id.moonPicture);
        TextView redLightsDisplay = (TextView)view.findViewById(R.id.redLightsTV);
        TextView greenLightsDisplay = (TextView)view.findViewById(R.id.greenLightsTV);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        if (user !=null) {
            uid = user.getUid();

        }
        else {
            uid = "bob";

        }
        //uid = user.getUid();

        final Calendar c = Calendar.getInstance();
        MoonPhase moonPhase1 = new MoonPhase(c);
        String moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();

        greenLightsDisplay.setText(greenLightCounter);
        redLightsDisplay.setText(redLightCounter);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("RedGreen").document(uid).collection("Data").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        mFirestore.collection("RedGreen").document(uid).collection("Data").document(moonDayString).get().addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {
                                if (document.get("RedLights")!=null) redLightsDisplay.setText(document.get("RedLights").toString());
                                if (document.get("GreenLights") != null) greenLightsDisplay.setText(document.get("GreenLights").toString());
                                //Toast.makeText(getContext(), "Got lights!", Toast.LENGTH_SHORT).show();
                                view.findViewById(R.id.chartButton).callOnClick();
                                int redLights = Integer.valueOf(document.get("RedLights").toString());
                                int greenLights = Integer.valueOf(document.get("GreenLights").toString());
                                int diff = 0;
                                if (redLights>greenLights) diff = redLights - greenLights;
                                if (greenLights>redLights) diff = greenLights-redLights;
                                if (diff<70){
                                    redLightIndicator.getLayoutParams().height=redLights*20+1;
                                    greenLightIndicator.getLayoutParams().height=greenLights*20+1;
                                    redLightIndicator.getLayoutParams().width=redLights*20+1;
                                    greenLightIndicator.getLayoutParams().width=greenLights*20+1;
                                }


                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
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






        CheckBox checkEmotions = view.findViewById(R.id.checkBoxEmotions);
        CheckBox checkEnergy = view.findViewById(R.id.checkBoxEnergy);
        CheckBox checkStress = view.findViewById(R.id.checkBoxStress);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {});
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        ArrayList<Entry> lineData = new ArrayList<>();
        ArrayList<Entry> lineDataEnergy = new ArrayList<>();
        ArrayList<Entry> lineDataStress = new ArrayList<>();
        BarChart bchart = (BarChart)getView().findViewById(R.id.chart);
        for (int i = (int) 0; i < 29 + 1; i++) {
            float val = (float) (Math.random());
            //yVals1.add(new BarEntry(i, 0));
            //lineData.add(new Entry(i,1+i));
        }

        for (int i = (int) 0; i < 10 + 1; i++) {
            float val = (float) (Math.random());
            //yVals2.add(new BarEntry(i, 11-i));
        }
        makeChart(user, series,yVals1, lineData, lineDataEnergy, lineDataStress);
        Log.d("YOOOOOOOOOOOOOOOOOOO", "HeeeeeeeeeeeeeeeeeeeeeRRRe" + yVals1);


        view.findViewById(R.id.chartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Inside Button" + "Hiii" + yVals1);
                Log.d(TAG, "Inside Button" + "Hiii" + lineData);
           /*    BarDataSet set1,set2;
                set1 = new BarDataSet(yVals1, "The year 2017");
                set2 = new BarDataSet(yVals2, "The year 2017");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set2.setColors(ColorTemplate.JOYFUL_COLORS);
                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);
                ArrayList<IBarDataSet> dataSets2 = new ArrayList<IBarDataSet>();
                dataSets.add(set2);
                BarData data = new BarData(dataSets);
                dataSets.add(set1);
                dataSets.add(set2);
                dataSets.add(set1);
                data.setValueTextSize(10f);
                data.setBarWidth(0.25f);
                bchart.setTouchEnabled(false);
                bchart.setData(data);
                bchart.notifyDataSetChanged();
                bchart.invalidate();*/
                Log.d(TAG, "Inside Button" + "ggg" + yVals1);
                Log.d(TAG, "Inside Button" + "ggg" + lineData);



    //////////////////////  Line Graphing Section  /////////////////
                LineChart mChart;
                mChart = (LineChart) getView().findViewById(R.id.lineChart);
                mChart.setTouchEnabled(true);
                mChart.setPinchZoom(true);
                LineDataSet lineSet1, lineSetEnergy, lineSetStress;
               /* if (mChart.getData() != null &&
                        mChart.getData().getDataSetCount() > 0) {
                    lineSet1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                    lineSet1.setValues(lineData);
                    mChart.getData().notifyDataChanged();
                    mChart.getData().setHighlightEnabled(true);
                    mChart.notifyDataSetChanged();
                    mChart.animateY(3000);


                } else {*/
                    lineSet1 = new LineDataSet(lineData, "Emotions");
                    lineSetEnergy = new LineDataSet(lineDataEnergy, "Energy");
                    lineSetStress = new LineDataSet(lineDataStress, "Stress");
                    lineSet1.setDrawIcons(false);
                    //lineSet1.enableDashedLine(10f, 5f, 0f);
                    lineSet1.enableDashedHighlightLine(10f, 5f, 0f);
                    lineSet1.setColor(R.color.dkGreen);
                    lineSetEnergy.setColor(Color.BLUE);
                    lineSetStress.setColor(Color.RED);
                    lineSet1.setCircleColor(Color.WHITE);
                    lineSet1.setLineWidth(3f);
                    lineSetEnergy.setLineWidth(3f);
                    lineSetStress.setLineWidth(3f);
                    lineSet1.setCircleRadius(1f);
                    lineSet1.setDrawCircleHole(false);
                    lineSet1.setValueTextSize(3f);
                    lineSet1.setDrawFilled(false);
                    lineSet1.setHighlightEnabled(true);
                    lineSet1.setFormLineWidth(1f);
                    lineSet1.setDrawCircles(false);
                    lineSetEnergy.setDrawCircles(false);
                    lineSetStress.setDrawCircles(false);
                    lineSet1.setFormSize(15.f);
                    lineSetEnergy.setFormSize(15.f);
                    lineSetStress.setFormSize(15.f);
                    if (Utils.getSDKInt() >= 18) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_background);
                        //lineSet1.setFillDrawable(drawable);
                    } else {
                        //lineSet1.setFillColor(Color.DKGRAY);
                    }
                    ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
                    lineSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    lineSetEnergy.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    lineSetStress.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    lineDataSets.add(lineSet1);
                    lineDataSets.add(lineSetEnergy);
                    lineDataSets.add(lineSetStress);
                    LineData lineData = new LineData(lineDataSets);
                    lineData.setDrawValues(false);
                    LimitLine ll = new LimitLine(Integer.valueOf(moonDayString), "Today");
                     ll.setLineColor(Color.WHITE);
                     ll.setLineWidth(4f);
                     ll.setTextColor(Color.BLACK);
                     ll.setTextSize(12f);
                    mChart.getXAxis().addLimitLine(ll);
                    mChart.setData(lineData);
                    mChart.setDrawMarkerViews(true);
                    customMarkerView customMarkerView = new customMarkerView(getContext(), R.layout.custom_marker_view);
                    mChart.setMarkerView(customMarkerView);
                    mChart.notifyDataSetChanged();
                    mChart.animateY(3000);

                    moonPicture.getLayoutParams().width = mChart.getWidth()-40;

                checkEmotions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true){
                            Toast.makeText(getContext(), "Slick Rick" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.add(lineSet1);
                            mChart.notifyDataSetChanged();
                            mChart.animateX(1000);
                        }
                        if (isChecked == false){
                            Toast.makeText(getContext(), "False Creek" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.remove(lineSet1);
                            mChart.notifyDataSetChanged();
                            mChart.animateY(1000);

                        }
                        //lineDataSets.add(lineSet1);
                    }
                });

                checkEnergy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true){
                            Toast.makeText(getContext(), "Slick Rick" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.add(lineSetEnergy);
                            mChart.notifyDataSetChanged();
                            mChart.animateX(1000);
                        }
                        if (isChecked == false){
                            Toast.makeText(getContext(), "False Creek" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.remove(lineSetEnergy);
                            mChart.notifyDataSetChanged();
                            mChart.animateY(1000);

                        }
                        //lineDataSets.add(lineSet1);
                    }
                });

                checkStress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true){
                            Toast.makeText(getContext(), "Slick Rick" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.add(lineSetStress);
                            mChart.notifyDataSetChanged();
                            mChart.animateX(1000);
                        }
                        if (isChecked == false){
                            Toast.makeText(getContext(), "False Creek" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.remove(lineSetStress);
                            mChart.notifyDataSetChanged();
                            mChart.animateY(1000);

                        }
                        //lineDataSets.add(lineSet1);
                    }
                });

                }
                ///////////////////////////////////////////////////////////////////////////

           // }
        });




        Button lotteryWinsButton=(Button)view.findViewById(R.id.lotteryPageButton);
        lotteryWinsButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent n=new Intent(getContext(), addLottery.class);
                //startActivity(n);
                Toast.makeText(getContext(), "Lottery Page" + "", Toast.LENGTH_SHORT).show();
                Intent n=new Intent(getContext(), LotteryWinsPage.class);
                startActivity(n);


            }
        });

        Button moreChartsButton=(Button)view.findViewById(R.id.moreChartsButton);
        moreChartsButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent n=new Intent(getContext(), addLottery.class);
                //startActivity(n);
                Toast.makeText(getContext(), "More Charts" + "", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(viewDataFragment.this)
                        .navigate(R.id.action_SecondFragment_to_ThirdFragment);


            }
        });


        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "End" + yVals1.toString(), Toast.LENGTH_LONG ).show();
                 NavHostFragment.findNavController(viewDataFragment.this)
                         .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }


    public static void showToastMethod(Context context) {
        Toast.makeText(context, "mymessage ", Toast.LENGTH_SHORT).show();
    }

    public void makeChart(FirebaseUser user, LineGraphSeries<DataPoint> series, ArrayList<BarEntry> yVals1, ArrayList<Entry> lineData,ArrayList<Entry> lineDataEnergy,ArrayList<Entry> lineDataStress) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        String uid = "bob";
        uid=user.getUid();
        String finalUid = uid;

        CollectionReference reference = rootRef.collection("Human Metrics").document(finalUid).collection("Data");
        Query moonDayOrder = reference.orderBy("MoonDay");
        moonDayOrder.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                reference.orderBy("MoonDay", Query.Direction.DESCENDING);
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
                            if (counter != 0.0) average = DataTotal/counter;
                            else average = 3.5;
                            Double db = new Double(average);
                            float avgFloat = db.floatValue();
                            int id = Integer.valueOf(document.getId());
                            lineData.add(new Entry (id, avgFloat));
                            yVals1.add(new BarEntry(id, avgFloat));
                        }
                        else{
                            Log.d(TAG, "Document does not exist" + document);
                        }

                    }

                } else{
                    Log.d(TAG, "Graph point! " + "NONONONONONONONONOONONONONONONON");
                }
            }

        });
        ////////////////////////////////////

        CollectionReference referenceEnergy = rootRef.collection("Human Metrics").document(finalUid).collection("Energy");
        moonDayOrder = referenceEnergy.orderBy("MoonDay");
        moonDayOrder.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                referenceEnergy.orderBy("MoonDay", Query.Direction.DESCENDING);
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
                                DataTotal = DataTotal + (Double.parseDouble(document.get("Stress" + i).toString()));
                            }
                            if (counter != 0.0) average = DataTotal/counter;
                            else average = 3.5;
                            Double db = new Double(average);
                            float avgFloat = db.floatValue();
                            int id = Integer.valueOf(document.getId());
                            lineDataEnergy.add(new Entry (id, avgFloat));
                            //yVals1.add(new BarEntry(id, avgFloat));
                        }
                        else{
                            Log.d(TAG, "Document does not exist" + document);
                        }

                    }

                } else{
                    Log.d(TAG, "Graph point! " + "NONONONONONONONONOONONONONONONON");
                }
            }

        });

        CollectionReference referenceStress = rootRef.collection("Human Metrics").document(finalUid).collection("Stress");
        moonDayOrder = referenceStress.orderBy("MoonDay");
        moonDayOrder.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                referenceStress.orderBy("MoonDay", Query.Direction.DESCENDING);
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
                                DataTotal = DataTotal + (Double.parseDouble(document.get("Stress" + i).toString()));
                            }
                            if (counter != 0.0) average = DataTotal/counter;
                            else average = 3.5;
                            Double db = new Double(average);
                            float avgFloat = db.floatValue();
                            int id = Integer.valueOf(document.getId());
                            lineDataStress.add(new Entry (id, avgFloat));
                            //yVals1.add(new BarEntry(id, avgFloat));
                        }
                        else{
                            Log.d(TAG, "Document does not exist" + document);
                        }

                    }

                } else{
                    Log.d(TAG, "Graph point! " + "NONONONONONONONONOONONONONONONON");
                }
            }

        });
/////////////////////////////////////

     /*   CollectionReference referenceAll = rootRef.collection("Human Metrics").document(finalUid).collection("Stress");
        moonDayOrder = referenceAll.orderBy("MoonDay");
        moonDayOrder.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                referenceAll.orderBy("MoonDay", Query.Direction.DESCENDING);
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
                                DataTotal = DataTotal + (Double.parseDouble(document.get("Stress" + i).toString()));
                            }
                            if (counter != 0.0) average = DataTotal/counter;
                            else average = 3.5;
                            Double db = new Double(average);
                            float avgFloat = db.floatValue();
                            int id = Integer.valueOf(document.getId());
                            lineDataStress.add(new Entry (id, avgFloat));
                            //yVals1.add(new BarEntry(id, avgFloat));
                        }
                        else{
                            Log.d(TAG, "Document does not exist" + document);
                        }

                    }

                } else{
                    Log.d(TAG, "Graph point! " + "NONONONONONONONONOONONONONONONON");
                }
            }

        });*/

 /////////////////////////////////////

    }
}