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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        ListView mainListView = (ListView)view.findViewById(R.id.listView);
        ListView moonDayListView = (ListView)view.findViewById(R.id.moonDayList);
        List<String> list = new ArrayList<>();
        List<String> moonDaylist = new ArrayList<>();
        Map<String, Object> moonDayMap = new HashMap<>();


        moonAgeInDays moonDay24 = new moonAgeInDays(0.0, 0.0);


        mFirestore.collection("Lottery").document(uid).collection("Data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Double moneyIn;
                    Double moneyOut;
                    Double percentWin = 0.0;
                    Double totalSpent = 0.0;
                    Double totalWin = 0.0;
                    String ObjectString = null;



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
                    textView.setText(moonDay23.moneyIn.toString());


                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            list );

                    mainListView.setAdapter(arrayAdapter);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(view.getContext(), "Did not Retrieve", Toast.LENGTH_SHORT).show();
                }

                if(moonDay1.moneyIn!=0.0 &&moonDay1.moneyOut!=0.0) moonDaylist.add("1" + "  " +moonDay23.getPercent());

                if(moonDay23.moneyIn!=0.0 &&moonDay23.moneyOut!=0.0) moonDaylist.add("23" + "  " +moonDay23.getPercent());
                if(moonDay24.moneyIn!=0.0 &&moonDay24.moneyOut!=0.0) moonDaylist.add("24" + "  " +moonDay23.getPercent());

                ArrayAdapter<String> moonDayArrayAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        moonDaylist );

                moonDayListView.setAdapter(moonDayArrayAdapter);

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
            case "2":
                moonDay2.moneyIn +=moneyInVar;
            case "3":
                moonDay3.moneyIn +=moneyInVar;
            case "4":
                moonDay4.moneyIn +=moneyInVar;
            case "5":
                moonDay5.moneyIn +=moneyInVar;
            case "6":
                moonDay6.moneyIn +=moneyInVar;
            case "7":
                moonDay7.moneyIn +=moneyInVar;
            case "8":
                moonDay8.moneyIn +=moneyInVar;
            case "9":
                moonDay9.moneyIn +=moneyInVar;
            case "10":
                moonDay10.moneyIn +=moneyInVar;
            case "11":
                moonDay11.moneyIn +=moneyInVar;
            case "12":
                moonDay12.moneyIn +=moneyInVar;
            case "13":
                moonDay13.moneyIn +=moneyInVar;
            case "14":
                moonDay14.moneyIn +=moneyInVar;
            case "15":
                moonDay15.moneyIn +=moneyInVar;
            case "16":
                moonDay16.moneyIn +=moneyInVar;
            case "17":
                moonDay17.moneyIn +=moneyInVar;
            case "18":
                moonDay18.moneyIn +=moneyInVar;
            case "19":
                moonDay19.moneyIn +=moneyInVar;
            case "20":
                moonDay20.moneyIn +=moneyInVar;
            case "21":
                moonDay21.moneyIn +=moneyInVar;
            case "22":
                moonDay22.moneyIn +=moneyInVar;
            case "23":
                moonDay23.moneyIn +=moneyInVar;
            case "24":
                moonDay24.moneyIn +=moneyInVar;
            case "25":
                moonDay25.moneyIn +=moneyInVar;
            case "26":
                moonDay26.moneyIn +=moneyInVar;
            case "27":
                moonDay27.moneyIn +=moneyInVar;
            case "28":
                moonDay28.moneyIn +=moneyInVar;
            case "29":
                moonDay29.moneyIn +=moneyInVar;
            case "0":
                moonDay0.moneyIn +=moneyInVar;

        }


        return false;
    };

    boolean addMoneyOutToMoonDay(String moonDay, Double moneyOutVar){
        switch (moonDay){
            case "1":
                moonDay1.moneyOut +=moneyOutVar;
            case "2":
                moonDay2.moneyOut +=moneyOutVar;
            case "3":
                moonDay3.moneyOut +=moneyOutVar;
            case "4":
                moonDay4.moneyOut +=moneyOutVar;
            case "5":
                moonDay5.moneyOut +=moneyOutVar;
            case "6":
                moonDay6.moneyOut +=moneyOutVar;
            case "7":
                moonDay7.moneyOut +=moneyOutVar;
            case "8":
                moonDay8.moneyOut +=moneyOutVar;
            case "9":
                moonDay9.moneyOut +=moneyOutVar;
            case "10":
                moonDay10.moneyOut +=moneyOutVar;
            case "11":
                moonDay11.moneyOut +=moneyOutVar;
            case "12":
                moonDay12.moneyOut +=moneyOutVar;
            case "13":
                moonDay13.moneyOut +=moneyOutVar;
            case "14":
                moonDay14.moneyOut +=moneyOutVar;
            case "15":
                moonDay15.moneyOut +=moneyOutVar;
            case "16":
                moonDay16.moneyOut +=moneyOutVar;
            case "17":
                moonDay17.moneyOut +=moneyOutVar;
            case "18":
                moonDay18.moneyOut +=moneyOutVar;
            case "19":
                moonDay19.moneyOut +=moneyOutVar;
            case "20":
                moonDay20.moneyOut +=moneyOutVar;
            case "21":
                moonDay21.moneyOut +=moneyOutVar;
            case "22":
                moonDay22.moneyOut +=moneyOutVar;
            case "23":
                moonDay23.moneyOut +=moneyOutVar;
            case "24":
                moonDay24.moneyOut +=moneyOutVar;
            case "25":
                moonDay25.moneyOut +=moneyOutVar;
            case "26":
                moonDay26.moneyOut +=moneyOutVar;
            case "27":
                moonDay27.moneyOut +=moneyOutVar;
            case "28":
                moonDay28.moneyOut +=moneyOutVar;
            case "29":
                moonDay29.moneyOut +=moneyOutVar;
        }


        return false;
    };

}