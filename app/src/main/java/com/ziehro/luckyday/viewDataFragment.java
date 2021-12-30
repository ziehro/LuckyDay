package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.ziehro.luckyday.whatDayIsIt.letterDay;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String uid = user.getUid();

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

                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("1" + "  " +moonDay1.getPercent());
                     if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 1;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("2" + "  " +moonDay2.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 2;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("3" + "  " +moonDay3.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 3;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("4" + "  " +moonDay4.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 4;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("5" + "  " +moonDay5.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 5;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("6" + "  " +moonDay6.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 6;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("1" + "  " +moonDay23.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 7;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("1" + "  " +moonDay23.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 8;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("1" + "  " +moonDay23.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 9;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("1" + "  " +moonDay23.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 10;}}
                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) {moonDaylist.add("1" + "  " +moonDay23.getPercent());
                    if(moonDay1.moneyOut/moonDay1.moneyIn > luckyDayWinPercent) {luckyDayWinPercent= moonDay1.moneyOut/moonDay1.moneyIn;luckyMoonDay = 11;}}
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


        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(viewDataFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
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

}