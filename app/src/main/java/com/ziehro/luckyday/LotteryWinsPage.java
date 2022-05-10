package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LotteryWinsPage extends AppCompatActivity {
    Integer luckyMoonDay = 0;
    Double luckyDayWinPercent = 0.0;
    //FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public String uid = "boob";

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_wins_page);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView totalSpentTV = (TextView) findViewById(R.id.totalSpentTV);
        TextView totalWonTV = (TextView) findViewById(R.id.totalWonTV);
        TextView luckyDay = (TextView) findViewById(R.id.luckyDayTV);

        ListView mainListView = (ListView)findViewById(R.id.listView);
        ListView moonDayListView = (ListView)findViewById(R.id.moonDayList);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        List<String> list = new ArrayList<>();
        List<String> moonDaylist = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        if (user !=null) {
            uid = user.getUid();

        }
        else {
            uid = "bob";

        }

        ArrayAdapter<String> adapter;

        mFirestore.collection("Lottery").document(uid).collection("Data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        list);

                if (task.isSuccessful()) {
                    Double moneyIn;
                    Double moneyOut;
                    Double percentWin = 0.0;
                    Double totalSpent = 0.0;
                    Double totalWin = 0.0;


                    for (QueryDocumentSnapshot document : task.getResult()) {
                        moneyIn = Double.valueOf(document.getString("moneyIn"));
                        moneyOut = Double.valueOf(document.getString("moneyOut"));
                        if (moneyIn > 0 && moneyOut > 0) {
                            percentWin = moneyOut / moneyIn;
                        } else percentWin = 0.0;
                        totalSpent = totalSpent + moneyIn;
                        totalWin = totalWin + moneyOut;

                        String moonDayLetter = whatDayIsIt.letterDay(document.getString("moonDay"));

                        addMoneyInToMoonDay(document.getString("moonDay"), moneyIn);
                        addMoneyOutToMoonDay(document.getString("moonDay"), moneyOut);
                        Integer place = (int) Math.round(percentWin) * 100;

                        String entryString = document.getString("moonDay") + "  " + document.getString("moonPhase") + "  " + place.toString() + " % Win";
                        list.add(entryString);

                    }
                    totalSpentTV.setText(totalSpent.toString());
                    totalWonTV.setText(totalWin.toString());
                    //mainListView.setAdapter(adapter);
                    Log.d(TAG, list.toString());






                    mainListView.setAdapter(arrayAdapter);


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(getApplicationContext(), "Did not Retrieve", Toast.LENGTH_SHORT).show();
                }
                if (moonDay1.moneyIn != 0.0 && moonDay1.moneyOut != 0.0) {
                    moonDaylist.add("1" + "  " + moonDay1.getPercent());
                    if (moonDay1.moneyOut / moonDay1.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay1.moneyOut / moonDay1.moneyIn;
                        luckyMoonDay = 1;
                    }
                }
                if (moonDay2.moneyIn != 0.0 && moonDay2.moneyOut != 0.0) {
                    moonDaylist.add("2" + "  " + moonDay2.getPercent());
                    if (moonDay2.moneyOut / moonDay2.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay2.moneyOut / moonDay2.moneyIn;
                        luckyMoonDay = 2;
                    }
                }
                if (moonDay3.moneyIn != 0.0 && moonDay3.moneyOut != 0.0) {
                    moonDaylist.add("3" + "  " + moonDay3.getPercent());
                    if (moonDay3.moneyOut / moonDay3.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay3.moneyOut / moonDay3.moneyIn;
                        luckyMoonDay = 3;
                    }
                }
                if (moonDay4.moneyIn != 0.0 && moonDay4.moneyOut != 0.0) {
                    moonDaylist.add("4" + "  " + moonDay4.getPercent());
                    if (moonDay4.moneyOut / moonDay4.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay4.moneyOut / moonDay4.moneyIn;
                        luckyMoonDay = 4;
                    }
                }
                if (moonDay5.moneyIn != 0.0 && moonDay5.moneyOut != 0.0) {
                    moonDaylist.add("5" + "  " + moonDay5.getPercent());
                    if (moonDay5.moneyOut / moonDay5.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay5.moneyOut / moonDay5.moneyIn;
                        luckyMoonDay = 5;
                    }
                }
                if (moonDay6.moneyIn != 0.0 && moonDay6.moneyOut != 0.0) {
                    moonDaylist.add("6" + "  " + moonDay6.getPercent());
                    if (moonDay6.moneyOut / moonDay6.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay6.moneyOut / moonDay6.moneyIn;
                        luckyMoonDay = 6;
                    }
                }
                if (moonDay7.moneyIn != 0.0 && moonDay7.moneyOut != 0.0) {
                    moonDaylist.add("7" + "  " + moonDay7.getPercent());
                    if (moonDay7.moneyOut / moonDay7.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay7.moneyOut / moonDay7.moneyIn;
                        luckyMoonDay = 7;
                    }
                }
                if (moonDay8.moneyIn != 0.0 && moonDay8.moneyOut != 0.0) {
                    moonDaylist.add("8" + "  " + moonDay8.getPercent());
                    if (moonDay8.moneyOut / moonDay8.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay8.moneyOut / moonDay8.moneyIn;
                        luckyMoonDay = 8;
                    }
                }
                if (moonDay9.moneyIn != 0.0 && moonDay9.moneyOut != 0.0) {
                    moonDaylist.add("9" + "  " + moonDay9.getPercent());
                    if (moonDay9.moneyOut / moonDay9.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay9.moneyOut / moonDay9.moneyIn;
                        luckyMoonDay = 9;
                    }
                }
                if (moonDay10.moneyIn != 0.0 && moonDay10.moneyOut != 0.0) {
                    moonDaylist.add("10" + "  " + moonDay10.getPercent());
                    if (moonDay10.moneyOut / moonDay10.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay10.moneyOut / moonDay10.moneyIn;
                        luckyMoonDay = 10;
                    }
                }
                if (moonDay11.moneyIn != 0.0 && moonDay11.moneyOut != 0.0) {
                    moonDaylist.add("11" + "  " + moonDay11.getPercent());
                    if (moonDay11.moneyOut / moonDay11.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay11.moneyOut / moonDay11.moneyIn;
                        luckyMoonDay = 11;
                    }
                }

                if (moonDay12.moneyIn != 0.0 && moonDay12.moneyOut != 0.0) {
                    moonDaylist.add("12" + "  " + moonDay12.getPercent());
                    if (moonDay12.moneyOut / moonDay12.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay12.moneyOut / moonDay12.moneyIn;
                        luckyMoonDay = 12;
                    }
                }
                if (moonDay13.moneyIn != 0.0 && moonDay13.moneyOut != 0.0) {
                    moonDaylist.add("13" + "  " + moonDay13.getPercent());
                    if (moonDay13.moneyOut / moonDay13.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay13.moneyOut / moonDay13.moneyIn;
                        luckyMoonDay = 13;
                    }
                }
                if (moonDay14.moneyIn != 0.0 && moonDay14.moneyOut != 0.0) {
                    moonDaylist.add("14" + "  " + moonDay14.getPercent());
                    if (moonDay14.moneyOut / moonDay14.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay14.moneyOut / moonDay14.moneyIn;
                        luckyMoonDay = 14;
                    }
                }
                if (moonDay15.moneyIn != 0.0 && moonDay15.moneyOut != 0.0) {
                    moonDaylist.add("15" + "  " + moonDay15.getPercent());
                    if (moonDay15.moneyOut / moonDay15.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay15.moneyOut / moonDay15.moneyIn;
                        luckyMoonDay = 15;
                    }
                }
                if (moonDay16.moneyIn != 0.0 && moonDay16.moneyOut != 0.0) {
                    moonDaylist.add("16" + "  " + moonDay16.getPercent());
                    if (moonDay16.moneyOut / moonDay16.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay16.moneyOut / moonDay16.moneyIn;
                        luckyMoonDay = 16;
                    }
                }
                if (moonDay17.moneyIn != 0.0 && moonDay17.moneyOut != 0.0) {
                    moonDaylist.add("17" + "  " + moonDay17.getPercent());
                    if (moonDay17.moneyOut / moonDay17.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay17.moneyOut / moonDay17.moneyIn;
                        luckyMoonDay = 17;
                    }
                }
                if (moonDay18.moneyIn != 0.0 && moonDay18.moneyOut != 0.0) {
                    moonDaylist.add("18" + "  " + moonDay18.getPercent());
                    if (moonDay18.moneyOut / moonDay18.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay18.moneyOut / moonDay18.moneyIn;
                        luckyMoonDay = 18;
                    }
                }
                if (moonDay19.moneyIn != 0.0 && moonDay19.moneyOut != 0.0) {
                    moonDaylist.add("19" + "  " + moonDay19.getPercent());
                    if (moonDay19.moneyOut / moonDay19.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay19.moneyOut / moonDay19.moneyIn;
                        luckyMoonDay = 19;
                    }
                }
                if (moonDay20.moneyIn != 0.0 && moonDay20.moneyOut != 0.0) {
                    moonDaylist.add("20" + "  " + moonDay20.getPercent());
                    if (moonDay20.moneyOut / moonDay20.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay20.moneyOut / moonDay20.moneyIn;
                        luckyMoonDay = 20;
                    }
                }
                if (moonDay21.moneyIn != 0.0 && moonDay21.moneyOut != 0.0) {
                    moonDaylist.add("21" + "  " + moonDay21.getPercent());
                    if (moonDay21.moneyOut / moonDay21.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay21.moneyOut / moonDay21.moneyIn;
                        luckyMoonDay = 21;
                    }
                }
                if (moonDay22.moneyIn != 0.0 && moonDay22.moneyOut != 0.0) {
                    moonDaylist.add("22" + "  " + moonDay22.getPercent());
                    if (moonDay22.moneyOut / moonDay22.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay22.moneyOut / moonDay22.moneyIn;
                        luckyMoonDay = 22;
                    }
                }
                if (moonDay23.moneyIn != 0.0 && moonDay23.moneyOut != 0.0) {
                    moonDaylist.add("23" + "  " + moonDay23.getPercent());
                    if (moonDay23.moneyOut / moonDay23.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay23.moneyOut / moonDay23.moneyIn;
                        luckyMoonDay = 23;
                    }
                }
                if (moonDay24.moneyIn != 0.0 && moonDay24.moneyOut != 0.0) {
                    moonDaylist.add("24" + "  " + moonDay24.getPercent());
                    if (moonDay24.moneyOut / moonDay24.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay24.moneyOut / moonDay24.moneyIn;
                        luckyMoonDay = 24;
                    }
                }
                if (moonDay25.moneyIn != 0.0 && moonDay25.moneyOut != 0.0) {
                    moonDaylist.add("25" + "  " + moonDay25.getPercent());
                    if (moonDay25.moneyOut / moonDay25.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay25.moneyOut / moonDay25.moneyIn;
                        luckyMoonDay = 25;
                    }
                }
                if (moonDay26.moneyIn != 0.0 && moonDay26.moneyOut != 0.0) {
                    moonDaylist.add("26" + "  " + moonDay26.getPercent());
                    if (moonDay26.moneyOut / moonDay26.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay26.moneyOut / moonDay26.moneyIn;
                        luckyMoonDay = 26;
                    }
                }
                if (moonDay27.moneyIn != 0.0 && moonDay27.moneyOut != 0.0) {
                    moonDaylist.add("27" + "  " + moonDay27.getPercent());
                    if (moonDay27.moneyOut / moonDay27.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay27.moneyOut / moonDay27.moneyIn;
                        luckyMoonDay = 27;
                    }
                }
                if (moonDay28.moneyIn != 0.0 && moonDay28.moneyOut != 0.0) {
                    moonDaylist.add("28" + "  " + moonDay28.getPercent());
                    if (moonDay28.moneyOut / moonDay28.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay28.moneyOut / moonDay28.moneyIn;
                        luckyMoonDay = 28;
                    }
                }
                if (moonDay29.moneyIn != 0.0 && moonDay29.moneyOut != 0.0) {
                    moonDaylist.add("29" + "  " + moonDay29.getPercent());
                    if (moonDay29.moneyOut / moonDay29.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay29.moneyOut / moonDay29.moneyIn;
                        luckyMoonDay = 29;
                    }
                }

                ArrayAdapter<String> moonDayArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        moonDaylist);

                //moonDayListView.setAdapter(new ArrayAdapter<String>(LotteryWinsPage.this,android.R.layout.simple_spinner_dropdown_item, mylist));

                moonDayListView.setAdapter(moonDayArrayAdapter);
                gridView.setAdapter(arrayAdapter);

                luckyDay.setText(luckyMoonDay.toString());




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
