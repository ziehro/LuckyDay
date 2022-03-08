package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.ziehro.luckyday.whatDayIsIt.letterDay;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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


    private ArrayAdapter<String> adapter;


    public moonAgeInDays moonDay0 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay1 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay2 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay3 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay4 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay5 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay6 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay7 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay8 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay9 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay10 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay11 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay12 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay13 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay14 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay15 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay16 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay17 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay18 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay19 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay20 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay21 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay22 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay23 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay24 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay25 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay26 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay27 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay28 = new moonAgeInDays(0.0, 0.0);
    public moonAgeInDays moonDay29 = new moonAgeInDays(0.0, 0.0);
    Integer luckyMoonDay = 0;
    Double luckyDayWinPercent= 0.0;
    String greenLightCounter;
    String redLightCounter;
    public String uid = "boob";

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
        TextView textView = (TextView) view.findViewById(R.id.textView);
        TextView totalSpentTV = (TextView) view.findViewById(R.id.totalSpentTV);
        TextView totalWonTV = (TextView) view.findViewById(R.id.totalWonTV);
        TextView luckyDay = (TextView) view.findViewById(R.id.luckyDayTV);
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
                                redLightsDisplay.setText(document.get("RedLights").toString());
                                greenLightsDisplay.setText(document.get("GreenLights").toString());
                                Toast.makeText(getContext(), "Got lights!", Toast.LENGTH_SHORT).show();

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





        ListView mainListView = (ListView)view.findViewById(R.id.listView);
        ListView moonDayListView = (ListView)view.findViewById(R.id.moonDayList);
        List<String> list = new ArrayList<>();
        List<String> moonDaylist = new ArrayList<>();

        mFirestore.collection("Lottery").document(uid).collection("Data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Double moneyIn;
                    Double moneyOut;
                    Double percentWin = 0.0;
                    Double totalSpent = 0.0;
                    Double totalWin = 0.0;


                    for (QueryDocumentSnapshot document : task.getResult()) {
                        moneyIn = Double.valueOf(document.getString("moneyIn"));
                        moneyOut = Double.valueOf(document.getString("moneyOut"));
                        if (moneyIn >0 && moneyOut >0) {
                            percentWin = moneyOut / moneyIn;
                        }
                        else percentWin = 0.0;
                        totalSpent=totalSpent+moneyIn;
                        totalWin = totalWin+moneyOut;

                        String moonDayLetter = whatDayIsIt.letterDay(document.getString("moonDay"));

                        addMoneyInToMoonDay(document.getString("moonDay"),moneyIn);
                        addMoneyOutToMoonDay(document.getString("moonDay"),moneyOut);
                        Integer place = (int)Math.round(percentWin)*100;

                        String entryString = document.getString("moonDay") + moonDayLetter +"  " + document.getString("moonPhase") + "  " + place.toString() + " % Win";
                        list.add(entryString);

                    }
                    totalSpentTV.setText(totalSpent.toString());
                    totalWonTV.setText(totalWin.toString());
                    mainListView.setAdapter(adapter);
                    Log.d(TAG, list.toString());

                    greenLightsDisplay.setText(greenLightCounter);
                    redLightsDisplay.setText(redLightCounter);


                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            list );

                    mainListView.setAdapter(arrayAdapter);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(view.getContext(), "Did not Retrieve", Toast.LENGTH_SHORT).show();
                }

                if(moonDay12.moneyIn!=0.0 &&moonDay12.moneyOut!=0.0) {moonDaylist.add("12" + "  " +moonDay12.getPercent());
                     if(moonDay12.moneyOut/moonDay12.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay12.moneyOut/moonDay12.moneyIn;luckyMoonDay = 12;}}
                if(moonDay13.moneyIn!=0.0 &&moonDay13.moneyOut!=0.0) {moonDaylist.add("13" + "  " +moonDay13.getPercent());
                    if(moonDay13.moneyOut/moonDay13.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay13.moneyOut/moonDay13.moneyIn;luckyMoonDay = 13;}}
                if(moonDay14.moneyIn!=0.0 &&moonDay14.moneyOut!=0.0) {moonDaylist.add("14" + "  " +moonDay14.getPercent());
                    if(moonDay14.moneyOut/moonDay14.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay14.moneyOut/moonDay14.moneyIn;luckyMoonDay = 14;}}
                if(moonDay15.moneyIn!=0.0 &&moonDay15.moneyOut!=0.0) {moonDaylist.add("15" + "  " +moonDay15.getPercent());
                    if(moonDay15.moneyOut/moonDay15.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay15.moneyOut/moonDay15.moneyIn;luckyMoonDay = 15;}}
                if(moonDay16.moneyIn!=0.0 &&moonDay16.moneyOut!=0.0) {moonDaylist.add("16" + "  " +moonDay16.getPercent());
                    if(moonDay16.moneyOut/moonDay16.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay16.moneyOut/moonDay16.moneyIn;luckyMoonDay = 16;}}
                if(moonDay17.moneyIn!=0.0 &&moonDay17.moneyOut!=0.0) {moonDaylist.add("17" + "  " +moonDay17.getPercent());
                    if(moonDay17.moneyOut/moonDay17.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay17.moneyOut/moonDay17.moneyIn;luckyMoonDay = 17;}}
                if(moonDay18.moneyIn!=0.0 &&moonDay18.moneyOut!=0.0) {moonDaylist.add("18" + "  " +moonDay18.getPercent());
                    if(moonDay18.moneyOut/moonDay18.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay18.moneyOut/moonDay18.moneyIn;luckyMoonDay = 18;}}
                if(moonDay19.moneyIn!=0.0 &&moonDay19.moneyOut!=0.0) {moonDaylist.add("19" + "  " +moonDay19.getPercent());
                    if(moonDay19.moneyOut/moonDay19.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay19.moneyOut/moonDay19.moneyIn;luckyMoonDay = 19;}}
                if(moonDay20.moneyIn!=0.0 &&moonDay20.moneyOut!=0.0) {moonDaylist.add("20" + "  " +moonDay20.getPercent());
                    if(moonDay20.moneyOut/moonDay20.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay20.moneyOut/moonDay20.moneyIn;luckyMoonDay = 20;}}
                if(moonDay21.moneyIn!=0.0 &&moonDay21.moneyOut!=0.0) {moonDaylist.add("21" + "  " +moonDay21.getPercent());
                    if(moonDay21.moneyOut/moonDay21.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay21.moneyOut/moonDay21.moneyIn;luckyMoonDay = 21;}}
                if(moonDay22.moneyIn!=0.0 &&moonDay22.moneyOut!=0.0) {moonDaylist.add("22" + "  " +moonDay22.getPercent());
                    if(moonDay22.moneyOut/moonDay22.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay22.moneyOut/moonDay22.moneyIn;luckyMoonDay = 22;}}
                if(moonDay23.moneyIn!=0.0 &&moonDay23.moneyOut!=0.0) {moonDaylist.add("23" + "  " +moonDay23.getPercent());
                    if(moonDay23.moneyOut/moonDay23.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay23.moneyOut/moonDay23.moneyIn;luckyMoonDay = 23;}}
                if(moonDay24.moneyIn!=0.0 &&moonDay24.moneyOut!=0.0) {moonDaylist.add("24" + "  " +moonDay24.getPercent());
                    if(moonDay24.moneyOut/moonDay24.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay24.moneyOut/moonDay24.moneyIn;luckyMoonDay = 24;}}
                if(moonDay25.moneyIn!=0.0 &&moonDay25.moneyOut!=0.0) {moonDaylist.add("25" + "  " +moonDay25.getPercent());
                    if(moonDay25.moneyOut/moonDay25.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay25.moneyOut/moonDay25.moneyIn;luckyMoonDay = 25;}}
                if(moonDay26.moneyIn!=0.0 &&moonDay26.moneyOut!=0.0) {moonDaylist.add("26" + "  " +moonDay26.getPercent());
                    if(moonDay26.moneyOut/moonDay26.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay26.moneyOut/moonDay26.moneyIn;luckyMoonDay = 26;}}
                if(moonDay27.moneyIn!=0.0 &&moonDay27.moneyOut!=0.0) {moonDaylist.add("27" + "  " +moonDay27.getPercent());
                    if(moonDay27.moneyOut/moonDay27.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay27.moneyOut/moonDay27.moneyIn;luckyMoonDay = 27;}}
                if(moonDay28.moneyIn!=0.0 &&moonDay28.moneyOut!=0.0) {moonDaylist.add("28" + "  " +moonDay28.getPercent());
                    if(moonDay28.moneyOut/moonDay28.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay28.moneyOut/moonDay28.moneyIn;luckyMoonDay = 28;}}
                if(moonDay29.moneyIn!=0.0 &&moonDay29.moneyOut!=0.0) {moonDaylist.add("29" + "  " +moonDay29.getPercent());
                    if(moonDay29.moneyOut/moonDay29.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay29.moneyOut/moonDay29.moneyIn;luckyMoonDay = 29;}}

                ArrayAdapter<String> moonDayArrayAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        moonDaylist );

                moonDayListView.setAdapter(moonDayArrayAdapter);
                luckyDay.setText(luckyMoonDay.toString());

            }
        });


        /*GraphView graph = (GraphView)getView().findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(29);*/
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {});
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        //graph.addSeries(series);
        //Toast.makeText(getContext(), "Make Graph", Toast.LENGTH_LONG ).show();
        BarChart bchart = (BarChart)getView().findViewById(R.id.chart);
        for (int i = (int) 0; i < 29 + 1; i++) {
            float val = (float) (Math.random());
            yVals1.add(new BarEntry(i, 0));
        }

        for (int i = (int) 0; i < 10 + 1; i++) {
            float val = (float) (Math.random());
            yVals2.add(new BarEntry(i, 5));
        }
        makeChart(user, series,yVals1);
        //Toast.makeText(getContext(), "Before" + yVals1.toString(), Toast.LENGTH_LONG ).show();
        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Toast.makeText(getContext(), "After" + yVals1.toString(), Toast.LENGTH_LONG ).show();
        Log.d("YOOOOOOOOOOOOOOOOOOO", "HeeeeeeeeeeeeeeeeeeeeeRRRe" + yVals1);
       /* BarDataSet set1;
        set1 = new BarDataSet(yVals1, "The year 2017");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        bchart.setTouchEnabled(false);
        bchart.setData(data);
        Toast.makeText(getContext(), "End" + yVals1.toString(), Toast.LENGTH_LONG ).show();
        set1 = new BarDataSet(yVals1, "The year 2017");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSets.add(set1);
        //BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        bchart.setTouchEnabled(false);
        bchart.setData(data);
        bchart.notifyDataSetChanged();
        bchart.invalidate();*/



        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarDataSet set1,set2;
                set1 = new BarDataSet(yVals1, "The year 2017");
                set2 = new BarDataSet(yVals2, "The year 2017");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set2.setColors(ColorTemplate.JOYFUL_COLORS);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);
                ArrayList<IBarDataSet> dataSets2 = new ArrayList<IBarDataSet>();
                dataSets.add(set2);
                BarData data = new BarData(dataSets);

                //Toast.makeText(getContext(), "End" + yVals1.toString(), Toast.LENGTH_LONG ).show();

                dataSets.add(set1);
                dataSets.add(set2);


                dataSets.add(set1);
                //BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                data.setBarWidth(0.25f);
                bchart.setTouchEnabled(false);
                bchart.setData(data);
                bchart.notifyDataSetChanged();
                bchart.invalidate();

                //Toast.makeText(getContext(), "End" + yVals1.toString(), Toast.LENGTH_LONG ).show();
               // NavHostFragment.findNavController(viewDataFragment.this)
               //         .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    boolean addMoneyInToMoonDay(String moonDay, Double moneyInVar){
        switch (moonDay){
            case "1":
                moonDay1.moneyIn +=moneyInVar;
                break;
            case "2":
                moonDay2.moneyIn +=moneyInVar;
                break;
            case "3":
                moonDay3.moneyIn +=moneyInVar;
                break;
            case "4":
                moonDay4.moneyIn +=moneyInVar;
                break;
            case "5":
                moonDay5.moneyIn +=moneyInVar;
                break;
            case "6":
                moonDay6.moneyIn +=moneyInVar;
                break;
            case "7":
                moonDay7.moneyIn +=moneyInVar;
                break;
            case "8":
                moonDay8.moneyIn +=moneyInVar;
                break;
            case "9":
                moonDay9.moneyIn +=moneyInVar;
                break;
            case "10":
                moonDay10.moneyIn +=moneyInVar;
                break;
            case "11":
                moonDay11.moneyIn +=moneyInVar;
                break;
            case "12":
                moonDay12.moneyIn +=moneyInVar;
                break;
            case "13":
                moonDay13.moneyIn +=moneyInVar;
                break;
            case "14":
                moonDay14.moneyIn +=moneyInVar;
                break;
            case "15":
                moonDay15.moneyIn +=moneyInVar;
                break;
            case "16":
                moonDay16.moneyIn +=moneyInVar;
                break;
            case "17":
                moonDay17.moneyIn +=moneyInVar;
                break;
            case "18":
                moonDay18.moneyIn +=moneyInVar;
                break;
            case "19":
                moonDay19.moneyIn +=moneyInVar;
                break;
            case "20":
                moonDay20.moneyIn +=moneyInVar;
                break;
            case "21":
                moonDay21.moneyIn +=moneyInVar;
                break;
            case "22":
                moonDay22.moneyIn +=moneyInVar;
                break;
            case "23":
                moonDay23.moneyIn +=moneyInVar;
                break;
            case "24":
                moonDay24.moneyIn +=moneyInVar;
                break;
            case "25":
                moonDay25.moneyIn +=moneyInVar;
                break;
            case "26":
                moonDay26.moneyIn +=moneyInVar;
                break;
            case "27":
                moonDay27.moneyIn +=moneyInVar;
                break;
            case "28":
                moonDay28.moneyIn +=moneyInVar;
                break;
            case "29":
                moonDay29.moneyIn +=moneyInVar;
                break;
            case "0":
                moonDay0.moneyIn +=moneyInVar;
                break;

        }


        return false;
    };

    boolean addMoneyOutToMoonDay(String moonDay, Double moneyOutVar){
        switch (moonDay){
            case "1":
                moonDay1.moneyOut +=moneyOutVar;
                break;
            case "2":
                moonDay2.moneyOut +=moneyOutVar;
                break;
            case "3":
                moonDay3.moneyOut +=moneyOutVar;
                break;
            case "4":
                moonDay4.moneyOut +=moneyOutVar;
                break;
            case "5":
                moonDay5.moneyOut +=moneyOutVar;
                break;
            case "6":
                moonDay6.moneyOut +=moneyOutVar;
                break;
            case "7":
                moonDay7.moneyOut +=moneyOutVar;
                break;
            case "8":
                moonDay8.moneyOut +=moneyOutVar;
                break;
            case "9":
                moonDay9.moneyOut +=moneyOutVar;
                break;
            case "10":
                moonDay10.moneyOut +=moneyOutVar;
                break;
            case "11":
                moonDay11.moneyOut +=moneyOutVar;
                break;
            case "12":
                moonDay12.moneyOut +=moneyOutVar;
                break;
            case "13":
                moonDay13.moneyOut +=moneyOutVar;
                break;
            case "14":
                moonDay14.moneyOut +=moneyOutVar;
                break;
            case "15":
                moonDay15.moneyOut +=moneyOutVar;
                break;
            case "16":
                moonDay16.moneyOut +=moneyOutVar;
                break;
            case "17":
                moonDay17.moneyOut +=moneyOutVar;
                break;
            case "18":
                moonDay18.moneyOut +=moneyOutVar;
                break;
            case "19":
                moonDay19.moneyOut +=moneyOutVar;
                break;
            case "20":
                moonDay20.moneyOut +=moneyOutVar;
                break;
            case "21":
                moonDay21.moneyOut +=moneyOutVar;
                break;
            case "22":
                moonDay22.moneyOut +=moneyOutVar;
                break;
            case "23":
                moonDay23.moneyOut +=moneyOutVar;
                break;
            case "24":
                moonDay24.moneyOut +=moneyOutVar;
                break;
            case "25":
                moonDay25.moneyOut +=moneyOutVar;
                break;
            case "26":
                moonDay26.moneyOut +=moneyOutVar;
                break;
            case "27":
                moonDay27.moneyOut +=moneyOutVar;
                break;
            case "28":
                moonDay28.moneyOut +=moneyOutVar;
                break;
            case "29":
                moonDay29.moneyOut +=moneyOutVar;
                break;
        }


        return false;
    };

    public static void showToastMethod(Context context) {
        Toast.makeText(context, "mymessage ", Toast.LENGTH_SHORT).show();
    }

    public void makeChart(FirebaseUser user, LineGraphSeries<DataPoint> series, ArrayList<BarEntry> yVals1) {
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
                            average = DataTotal/counter;
                            int id = Integer.valueOf(document.getId());
                            //dataPoints[id] = new DataPoint(id, average); // not sure but I think the second argument should be of type double
//                          //          Log.d(TAG, "Graph point! " + g + document.get("Emotions" + g));
                            Double db = new Double(average);
                            float avgFloat = db.floatValue();

                            Log.d(TAG, "Logged " + id + "  counter = " + avgFloat + yValsHere);
                            //series.appendData(dataPoints[id], false, 30, false);
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

    }
}