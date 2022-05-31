package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_wins_page);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView totalSpentTV = (TextView) findViewById(R.id.totalSpentTV);
        TextView totalWonTV = (TextView) findViewById(R.id.totalWonTV);
        TextView luckyDay = (TextView) findViewById(R.id.luckyDayTV);
        TextView luckyMoonPhase = (TextView) findViewById(R.id.luckyMoonPhaseTV);

        ListView mainListView = (ListView)findViewById(R.id.listView);
        ListView moonDayListView = (ListView)findViewById(R.id.moonDayList);
        //GridView gridView = (GridView)findViewById(R.id.gridView);
        List<String> list = new ArrayList<>();
        List<String> moonPhaseList = new ArrayList<>();
        List<String> moonDaylist = new ArrayList<>();

        LineChart mChart;

        mChart = (LineChart) this.findViewById(R.id.lotteryChart);

        String barChartTitle = "Lottery Wins per Day";
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        LineDataSet lineSetLottery;
        final Calendar c = Calendar.getInstance();
        MoonPhase moonPhase1 = new MoonPhase(c);
        String moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();
        ArrayList<Entry> lineDataLottery = new ArrayList<>();
        ArrayList<BarEntry> BarEntries = new ArrayList<>();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
                        moonPhaseList);

                if (task.isSuccessful()) {
                    Double moneyIn=0.0;
                    Double moneyOut=0.0;

                    Double NMmoneyIn=0.0;
                    Double NMmoneyOut=0.0;

                    Double WAXCmoneyIn=0.0;
                    Double WAXCmoneyOut=0.0;

                    Double FQmoneyIn=0.0;
                    Double FQmoneyOut=0.0;

                    Double WAXGmoneyIn=0.0;
                    Double WAXGmoneyOut=0.0;

                    Double FMmoneyIn=0.0;
                    Double FMmoneyOut=0.0;

                    Double WANGmoneyIn=0.0;
                    Double WANGmoneyOut=0.0;

                    Double LQmoneyIn=0.0;
                    Double LQmoneyOut=0.0;

                    Double WANCmoneyIn=0.0;
                    Double WANCmoneyOut=0.0;

                    Double percentWin = 0.0;
                    Double NMpercentWin = 0.0;
                    Double WAXCpercentWin = 0.0;
                    Double FQpercentWin = 0.0;
                    Double WAXGpercentWin = 0.0;
                    Double FMpercentWin = 0.0;
                    Double WANGpercentWin = 0.0;
                    Double LQpercentWin = 0.0;
                    Double WANCpercentWin = 0.0;

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

                        /// Trial for cleaner values)//////////
                        if (document.getString("moonPhase").equals("First quarter")) FQmoneyIn=FQmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("First quarter")) FQmoneyOut=FQmoneyOut+Double.valueOf(document.getString("moneyOut"));

                        if (document.getString("moonPhase").equals("Last quarter")) LQmoneyIn=LQmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("Last quarter")) LQmoneyOut=LQmoneyOut+Double.valueOf(document.getString("moneyOut"));

                        if (document.getString("moonPhase").equals("Full moon")) FMmoneyIn=FMmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("Full moon")) FMmoneyOut=FMmoneyOut+Double.valueOf(document.getString("moneyOut"));

                        if (document.getString("moonPhase").equals("Waxing gibbous")) WAXGmoneyIn=WAXGmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("Waxing gibbous")) WAXGmoneyOut=WAXGmoneyOut+Double.valueOf(document.getString("moneyOut"));

                        if (document.getString("moonPhase").equals("New moon")) NMmoneyIn=NMmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("New moon")) NMmoneyOut=NMmoneyOut+Double.valueOf(document.getString("moneyOut"));

                        if (document.getString("moonPhase").equals("Waning gibbous")) WANGmoneyIn=WANGmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("Waning gibbous")) WANGmoneyOut=WANGmoneyOut+Double.valueOf(document.getString("moneyOut"));

                        if (document.getString("moonPhase").equals("Waning crescent")) WANCmoneyIn=WANCmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("Waning crescent")) WANCmoneyOut=WANCmoneyOut+Double.valueOf(document.getString("moneyOut"));

                        if (document.getString("moonPhase").equals("Waxing crescent")) WAXCmoneyIn=WAXCmoneyIn+Double.valueOf(document.getString("moneyIn"));
                        if (document.getString("moonPhase").equals("Waxing crescent")) WAXCmoneyOut=WAXCmoneyOut+Double.valueOf(document.getString("moneyOut"));



                        ////
                        String entryString = document.getString("moonDay") + "  " + document.getString("moonPhase") + "  " + place.toString() + " % Win";
                        list.add(entryString);

                    }
                    if (NMmoneyIn > 0 && NMmoneyOut > 0) {
                        NMpercentWin = NMmoneyOut / NMmoneyIn;
                    } else NMpercentWin = 0.0;

                    if (WAXCmoneyIn > 0 && WAXCmoneyOut > 0) {
                        WAXCpercentWin = WAXCmoneyOut / WAXCmoneyIn;
                    } else WAXCpercentWin = 0.0;

                    if (FQmoneyIn > 0 && FQmoneyOut > 0) {
                        FQpercentWin = FQmoneyOut / FQmoneyIn;
                    } else FQpercentWin = 0.0;

                    if (WAXGmoneyIn > 0 && WAXGmoneyOut > 0) {
                        WAXGpercentWin = WAXGmoneyOut / WAXGmoneyIn;
                    } else WAXGpercentWin = 0.0;

                    if (FMmoneyIn > 0 && FMmoneyOut > 0) {
                        FMpercentWin = FMmoneyOut / FMmoneyIn;
                    } else FMpercentWin = 0.0;

                    if (WANGmoneyIn > 0 && WANGmoneyOut > 0) {
                        WANGpercentWin = WANGmoneyOut / WANGmoneyIn;
                    } else WANGpercentWin = 0.0;

                    if (LQmoneyIn > 0 && LQmoneyOut > 0) {
                        LQpercentWin = LQmoneyOut / LQmoneyIn;
                    } else LQpercentWin = 0.0;

                    if (WANCmoneyIn > 0 && WANCmoneyOut > 0) {
                        WANCpercentWin = WANCmoneyOut / WANCmoneyIn;
                    } else WANCpercentWin = 0.0;

                    ArrayList Max = new ArrayList();
                    Integer BiggestPercentWinMoonPhase = 0;
                    String BiggestPercentWinMoonPhaseString = "Cats";

                    Integer NMplace = (int) Math.round(NMpercentWin) * 100;
                    Max.add(NMplace);
                    Integer WAXCplace = (int) Math.round(WAXCpercentWin) * 100;
                    Max.add(WAXCplace);
                    Integer FQplace = (int) Math.round(FQpercentWin) * 100;
                    Max.add(FQplace);
                    Integer WAXGplace = (int) Math.round(WAXCpercentWin) * 100;
                    Max.add(WAXGplace);
                    Integer FMplace = (int) Math.round(FMpercentWin) * 100;
                    Max.add(FMplace);
                    Integer WANGplace = (int) Math.round(WANGpercentWin) * 100;
                    Max.add(WANGplace);
                    Integer LQplace = (int) Math.round(LQpercentWin) * 100;
                    Max.add(LQplace);
                    Integer WANCplace = (int) Math.round(WANCpercentWin) * 100;
                    Max.add(WANCplace);

                    if (NMplace >= BiggestPercentWinMoonPhase) {
                        BiggestPercentWinMoonPhase = NMplace;
                        BiggestPercentWinMoonPhaseString = "New Moon";
                    }
                    if (WAXCplace >= BiggestPercentWinMoonPhase) {
                        BiggestPercentWinMoonPhase = WAXCplace;
                        BiggestPercentWinMoonPhaseString = "Waning Crescent";
                    }
                    if (FQplace >= BiggestPercentWinMoonPhase) {
                        BiggestPercentWinMoonPhase = FQplace;
                        BiggestPercentWinMoonPhaseString = "First Quarter";
                    }
                    if (WAXGplace >= BiggestPercentWinMoonPhase) {
                        BiggestPercentWinMoonPhase = WAXGplace;
                        BiggestPercentWinMoonPhaseString = "Waxing Gibbous";
                    }
                    if (FMplace >= BiggestPercentWinMoonPhase) {
                        BiggestPercentWinMoonPhase = FMplace;
                        BiggestPercentWinMoonPhaseString = "Full Moon";
                    }
                    if (WANGplace >= BiggestPercentWinMoonPhase) {
                        BiggestPercentWinMoonPhase = WANGplace;
                        BiggestPercentWinMoonPhaseString = "Waning Gibbous";
                    }
                    if (LQplace >= BiggestPercentWinMoonPhase) {
                        BiggestPercentWinMoonPhase = LQplace;
                        BiggestPercentWinMoonPhaseString = "Last Quarter";
                    }
                    if (WANCplace >= BiggestPercentWinMoonPhase){
                        BiggestPercentWinMoonPhase = WANCplace;
                        BiggestPercentWinMoonPhaseString = "Waning Crescent";
                    }


                    String NMentry = "New Moon:  " + NMplace + "% WIN";
                    String WAXCentry = "Waxing Crescent:  " + WAXCplace + "% WIN";
                    String FQentry = "First Quarter:  " + FQplace + "% WIN";
                    String WAXGentry = "Waxing Gibbous:  " + WAXGplace + "% WIN";
                    String FMentry = "Full Moon:  " + FMplace + "% WIN";
                    String WANGentry = "Waning Gibbous:  " + WANGplace + "% WIN";
                    String LQentry = "Last Quarter:  " + LQplace + "% WIN";
                    String WANCentry = "Waning Crescent:  " + WANCplace + "% WIN";

                    moonPhaseList.add(NMentry);
                    moonPhaseList.add(WAXCentry);
                    moonPhaseList.add(FQentry);
                    moonPhaseList.add(WAXGentry);
                    moonPhaseList.add(FMentry);
                    moonPhaseList.add(WANGentry);
                    moonPhaseList.add(LQentry);
                    moonPhaseList.add(WANCentry);



                    totalSpentTV.setText(totalSpent.toString());
                    totalWonTV.setText(totalWin.toString());
                    //mainListView.setAdapter(adapter);
                    Log.d(TAG, list.toString());
                    Collections.sort(list);

                    mainListView.setAdapter(arrayAdapter);
                    luckyMoonPhase.setText(BiggestPercentWinMoonPhaseString);


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(getApplicationContext(), "Did not Retrieve", Toast.LENGTH_SHORT).show();
                }


                if (moonDay1.moneyIn != 0.0 && moonDay1.moneyOut != 0.0) {
                    moonDaylist.add("1" + "  " + moonDay1.getPercent());
                    lineDataLottery.add(new Entry (1, moonDay1.getPercent()));
                    BarEntries.add(new BarEntry (1, moonDay1.getPercent()));
                    if (moonDay1.moneyOut / moonDay1.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay1.moneyOut / moonDay1.moneyIn;
                        luckyMoonDay = 1;
                    }
                }
                if (moonDay2.moneyIn != 0.0 && moonDay2.moneyOut != 0.0) {
                    moonDaylist.add("2" + "  " + moonDay2.getPercent());
                    lineDataLottery.add(new Entry (2, moonDay2.getPercent()));
                    BarEntries.add(new BarEntry (2, moonDay2.getPercent()));
                    if (moonDay2.moneyOut / moonDay2.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay2.moneyOut / moonDay2.moneyIn;
                        luckyMoonDay = 2;
                    }
                }
                if (moonDay3.moneyIn != 0.0 && moonDay3.moneyOut != 0.0) {
                    moonDaylist.add("3" + "  " + moonDay3.getPercent());
                    lineDataLottery.add(new Entry (3, moonDay3.getPercent()));
                    BarEntries.add(new BarEntry (3, moonDay3.getPercent()));
                    if (moonDay3.moneyOut / moonDay3.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay3.moneyOut / moonDay3.moneyIn;
                        luckyMoonDay = 3;
                    }
                }
                if (moonDay4.moneyIn != 0.0 && moonDay4.moneyOut != 0.0) {
                    moonDaylist.add("4" + "  " + moonDay4.getPercent());
                    lineDataLottery.add(new Entry (4, moonDay4.getPercent()));
                    BarEntries.add(new BarEntry (4, moonDay4.getPercent()));
                    if (moonDay4.moneyOut / moonDay4.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay4.moneyOut / moonDay4.moneyIn;
                        luckyMoonDay = 4;
                    }
                }
                if (moonDay5.moneyIn != 0.0 && moonDay5.moneyOut != 0.0) {
                    moonDaylist.add("5" + "  " + moonDay5.getPercent());
                    lineDataLottery.add(new Entry (5, moonDay5.getPercent()));
                    BarEntries.add(new BarEntry (5, moonDay5.getPercent()));
                    if (moonDay5.moneyOut / moonDay5.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay5.moneyOut / moonDay5.moneyIn;
                        luckyMoonDay = 5;
                    }
                }
                if (moonDay6.moneyIn != 0.0 && moonDay6.moneyOut != 0.0) {
                    moonDaylist.add("6" + "  " + moonDay6.getPercent());
                    lineDataLottery.add(new Entry (6, moonDay6.getPercent()));
                    BarEntries.add(new BarEntry (6, moonDay6.getPercent()));
                    if (moonDay6.moneyOut / moonDay6.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay6.moneyOut / moonDay6.moneyIn;
                        luckyMoonDay = 6;
                    }
                }
                if (moonDay7.moneyIn != 0.0 && moonDay7.moneyOut != 0.0) {
                    moonDaylist.add("7" + "  " + moonDay7.getPercent());
                    lineDataLottery.add(new Entry (7, moonDay7.getPercent()));
                    BarEntries.add(new BarEntry (7, moonDay7.getPercent()));
                    if (moonDay7.moneyOut / moonDay7.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay7.moneyOut / moonDay7.moneyIn;
                        luckyMoonDay = 7;
                    }
                }
                if (moonDay8.moneyIn != 0.0 && moonDay8.moneyOut != 0.0) {
                    moonDaylist.add("8" + "  " + moonDay8.getPercent());
                    lineDataLottery.add(new Entry (8, moonDay8.getPercent()));
                    BarEntries.add(new BarEntry (8, moonDay8.getPercent()));
                    if (moonDay8.moneyOut / moonDay8.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay8.moneyOut / moonDay8.moneyIn;
                        luckyMoonDay = 8;
                    }
                }
                if (moonDay9.moneyIn != 0.0 && moonDay9.moneyOut != 0.0) {
                    moonDaylist.add("9" + "  " + moonDay9.getPercent());
                    lineDataLottery.add(new Entry (9, moonDay9.getPercent()));
                    BarEntries.add(new BarEntry (9, moonDay9.getPercent()));
                    if (moonDay9.moneyOut / moonDay9.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay9.moneyOut / moonDay9.moneyIn;
                        luckyMoonDay = 9;
                    }
                }
                if (moonDay10.moneyIn != 0.0 && moonDay10.moneyOut != 0.0) {
                    moonDaylist.add("10" + "  " + moonDay10.getPercent());
                    lineDataLottery.add(new Entry (10, moonDay10.getPercent()));
                    BarEntries.add(new BarEntry (10, moonDay10.getPercent()));
                    if (moonDay10.moneyOut / moonDay10.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay10.moneyOut / moonDay10.moneyIn;
                        luckyMoonDay = 10;
                    }
                }
                if (moonDay11.moneyIn != 0.0 && moonDay11.moneyOut != 0.0) {
                    moonDaylist.add("11" + "  " + moonDay11.getPercent());
                    lineDataLottery.add(new Entry (11, moonDay11.getPercent()));
                    BarEntries.add(new BarEntry (11, moonDay11.getPercent()));
                    if (moonDay11.moneyOut / moonDay11.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay11.moneyOut / moonDay11.moneyIn;
                        luckyMoonDay = 11;
                    }
                }
                if (moonDay12.moneyIn != 0.0 && moonDay12.moneyOut != 0.0) {
                    moonDaylist.add("12" + "  " + moonDay12.getPercent());
                    BarEntries.add(new BarEntry (12, moonDay12.getPercent()));
                    if (moonDay12.moneyOut / moonDay12.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay12.moneyOut / moonDay12.moneyIn;
                        luckyMoonDay = 12;
                    }
                }
                if (moonDay13.moneyIn != 0.0 && moonDay13.moneyOut != 0.0) {
                    moonDaylist.add("13" + "  " + moonDay13.getPercent());
                    lineDataLottery.add(new Entry (13, moonDay13.getPercent()));
                    BarEntries.add(new BarEntry (13, moonDay13.getPercent()));
                    if (moonDay13.moneyOut / moonDay13.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay13.moneyOut / moonDay13.moneyIn;
                        luckyMoonDay = 13;
                    }
                }
                if (moonDay14.moneyIn != 0.0 && moonDay14.moneyOut != 0.0) {
                    moonDaylist.add("14" + "  " + moonDay14.getPercent());
                    lineDataLottery.add(new Entry (14, moonDay14.getPercent()));
                    BarEntries.add(new BarEntry (14, moonDay14.getPercent()));
                    if (moonDay14.moneyOut / moonDay14.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay14.moneyOut / moonDay14.moneyIn;
                        luckyMoonDay = 14;
                    }
                }
                if (moonDay15.moneyIn != 0.0 && moonDay15.moneyOut != 0.0) {
                    moonDaylist.add("15" + "  " + moonDay15.getPercent());
                    lineDataLottery.add(new Entry (15, moonDay15.getPercent()));
                    BarEntries.add(new BarEntry (15, moonDay15.getPercent()));
                    if (moonDay15.moneyOut / moonDay15.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay15.moneyOut / moonDay15.moneyIn;
                        luckyMoonDay = 15;
                    }
                }
                if (moonDay16.moneyIn != 0.0 && moonDay16.moneyOut != 0.0) {
                    moonDaylist.add("16" + "  " + moonDay16.getPercent());
                    lineDataLottery.add(new Entry (16, moonDay16.getPercent()));
                    BarEntries.add(new BarEntry (16, moonDay16.getPercent()));
                    if (moonDay16.moneyOut / moonDay16.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay16.moneyOut / moonDay16.moneyIn;
                        luckyMoonDay = 16;
                    }
                }
                if (moonDay17.moneyIn != 0.0 && moonDay17.moneyOut != 0.0) {
                    moonDaylist.add("17" + "  " + moonDay17.getPercent());
                    lineDataLottery.add(new Entry (17, moonDay17.getPercent()));
                    BarEntries.add(new BarEntry (17, moonDay17.getPercent()));
                    if (moonDay17.moneyOut / moonDay17.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay17.moneyOut / moonDay17.moneyIn;
                        luckyMoonDay = 17;
                    }
                }
                if (moonDay18.moneyIn != 0.0 && moonDay18.moneyOut != 0.0) {
                    moonDaylist.add("18" + "  " + moonDay18.getPercent());
                    lineDataLottery.add(new Entry (18, moonDay18.getPercent()));
                    BarEntries.add(new BarEntry (18, moonDay18.getPercent()));
                    if (moonDay18.moneyOut / moonDay18.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay18.moneyOut / moonDay18.moneyIn;
                        luckyMoonDay = 18;
                    }
                }
                if (moonDay19.moneyIn != 0.0 && moonDay19.moneyOut != 0.0) {
                    moonDaylist.add("19" + "  " + moonDay19.getPercent());
                    lineDataLottery.add(new Entry (19, moonDay19.getPercent()));
                    BarEntries.add(new BarEntry (19, moonDay19.getPercent()));
                    if (moonDay19.moneyOut / moonDay19.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay19.moneyOut / moonDay19.moneyIn;
                        luckyMoonDay = 19;
                    }
                }
                if (moonDay20.moneyIn != 0.0 && moonDay20.moneyOut != 0.0) {
                    moonDaylist.add("20" + "  " + moonDay20.getPercent());
                    lineDataLottery.add(new Entry (20, moonDay20.getPercent()));
                    BarEntries.add(new BarEntry (20, moonDay20.getPercent()));
                    if (moonDay20.moneyOut / moonDay20.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay20.moneyOut / moonDay20.moneyIn;
                        luckyMoonDay = 20;
                    }
                }
                if (moonDay21.moneyIn != 0.0 && moonDay21.moneyOut != 0.0) {
                    moonDaylist.add("21" + "  " + moonDay21.getPercent());
                    lineDataLottery.add(new Entry (21, moonDay21.getPercent()));
                    BarEntries.add(new BarEntry (21, moonDay21.getPercent()));
                    if (moonDay21.moneyOut / moonDay21.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay21.moneyOut / moonDay21.moneyIn;
                        luckyMoonDay = 21;
                    }
                }
                if (moonDay22.moneyIn != 0.0 && moonDay22.moneyOut != 0.0) {
                    moonDaylist.add("22" + "  " + moonDay22.getPercent());
                    lineDataLottery.add(new Entry (22, moonDay22.getPercent()));
                    BarEntries.add(new BarEntry (22, moonDay22.getPercent()));
                    if (moonDay22.moneyOut / moonDay22.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay22.moneyOut / moonDay22.moneyIn;
                        luckyMoonDay = 22;
                    }
                }
                if (moonDay23.moneyIn != 0.0 && moonDay23.moneyOut != 0.0) {
                    moonDaylist.add("23" + "  " + moonDay23.getPercent());
                    lineDataLottery.add(new Entry (23, moonDay23.getPercent()));
                    BarEntries.add(new BarEntry (23, moonDay23.getPercent()));
                    if (moonDay23.moneyOut / moonDay23.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay23.moneyOut / moonDay23.moneyIn;
                        luckyMoonDay = 23;
                    }
                }
                if (moonDay24.moneyIn != 0.0 && moonDay24.moneyOut != 0.0) {
                    moonDaylist.add("24" + "  " + moonDay24.getPercent());
                    lineDataLottery.add(new Entry (24, moonDay24.getPercent()));
                    BarEntries.add(new BarEntry (24, moonDay24.getPercent()));
                    if (moonDay24.moneyOut / moonDay24.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay24.moneyOut / moonDay24.moneyIn;
                        luckyMoonDay = 24;
                    }
                }
                if (moonDay25.moneyIn != 0.0 && moonDay25.moneyOut != 0.0) {
                    moonDaylist.add("25" + "  " + moonDay25.getPercent());
                    lineDataLottery.add(new Entry (25, moonDay25.getPercent()));
                    BarEntries.add(new BarEntry (25, moonDay25.getPercent()));
                    if (moonDay25.moneyOut / moonDay25.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay25.moneyOut / moonDay25.moneyIn;
                        luckyMoonDay = 25;
                    }
                }
                if (moonDay26.moneyIn != 0.0 && moonDay26.moneyOut != 0.0) {
                    moonDaylist.add("26" + "  " + moonDay26.getPercent());
                    lineDataLottery.add(new Entry (26, moonDay26.getPercent()));
                    BarEntries.add(new BarEntry (26, moonDay26.getPercent()));
                    if (moonDay26.moneyOut / moonDay26.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay26.moneyOut / moonDay26.moneyIn;
                        luckyMoonDay = 26;
                    }
                }
                if (moonDay27.moneyIn != 0.0 && moonDay27.moneyOut != 0.0) {
                    moonDaylist.add("27" + "  " + moonDay27.getPercent());
                    lineDataLottery.add(new Entry (27, moonDay27.getPercent()));
                    BarEntries.add(new BarEntry (27, moonDay27.getPercent()));
                    if (moonDay27.moneyOut / moonDay27.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay27.moneyOut / moonDay27.moneyIn;
                        luckyMoonDay = 27;
                    }
                }
                if (moonDay28.moneyIn != 0.0 && moonDay28.moneyOut != 0.0) {
                    moonDaylist.add("28" + "  " + moonDay28.getPercent());
                    lineDataLottery.add(new Entry (28, moonDay28.getPercent()));
                    BarEntries.add(new BarEntry (28, moonDay28.getPercent()));
                    if (moonDay28.moneyOut / moonDay28.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay28.moneyOut / moonDay28.moneyIn;
                        luckyMoonDay = 28;
                    }
                }
                if (moonDay29.moneyIn != 0.0 && moonDay29.moneyOut != 0.0) {
                    moonDaylist.add("29" + "  " + moonDay29.getPercent());
                    lineDataLottery.add(new Entry (29, moonDay29.getPercent()));
                    BarEntries.add(new BarEntry (29, moonDay29.getPercent()));
                    if (moonDay29.moneyOut / moonDay29.moneyIn > luckyDayWinPercent) {
                        luckyDayWinPercent = moonDay29.moneyOut / moonDay29.moneyIn;
                        luckyMoonDay = 29;
                    }
                }
                Collections.sort(moonDaylist);
                ArrayAdapter<String> moonDayArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        moonDaylist);


                moonDayListView.setAdapter(moonDayArrayAdapter);
                //gridView.setAdapter(arrayAdapter);
                luckyDay.setText(luckyMoonDay.toString());

            }
        });

        this.findViewById(R.id.lotteryChartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //////////////////////  Line Graphing Section  /////////////////
                LineChart mChart;
                mChart = (LineChart)findViewById(R.id.lotteryChart);
                mChart.setTouchEnabled(true);
                mChart.setPinchZoom(true);
                LineDataSet lineSetLottery;


                lineSetLottery = new LineDataSet(lineDataLottery, "Lottery");
                lineSetLottery.setDrawIcons(false);
                lineSetLottery.enableDashedLine(0, 1, 0);
                lineSetLottery.enableDashedHighlightLine(10f, 5f, 0f);
                lineSetLottery.setColor(Color.GREEN);
                lineSetLottery.setCircleColor(Color.GREEN);
                lineSetLottery.setLineWidth(0f);
                lineSetLottery.setCircleRadius(7f);
                lineSetLottery.setDrawCircleHole(true);
                lineSetLottery.setValueTextSize(3f);
                lineSetLottery.setDrawFilled(false);
                lineSetLottery.setHighlightEnabled(true);
                lineSetLottery.setFormLineWidth(1f);
                lineSetLottery.setDrawCircles(true);
                lineSetLottery.setFormSize(15.f);


                if (Utils.getSDKInt() >= 18) {
                    Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_launcher_background);
                    //lineSet1.setFillDrawable(drawable);
                } else {
                    //lineSet1.setFillColor(Color.DKGRAY);
                }
                ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
                //ArrayList<BarEntry> BarEntries = new ArrayList<>();

                lineSetLottery.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                lineDataSets.add(lineSetLottery);

                LineData lineData = new LineData(lineDataSets);
                lineData.setDrawValues(false);
                LimitLine ll = new LimitLine(Integer.valueOf(moonDayString), "Today");
                ll.setLineColor(Color.YELLOW);
                ll.setLineWidth(1f);
                ll.setTextColor(Color.BLUE);
                ll.setTextSize(8f);
                mChart.getXAxis().addLimitLine(ll);
                mChart.setData(lineData);
                mChart.setDrawMarkerViews(true);
                customMarkerView customMarkerView = new customMarkerView(getApplicationContext(), R.layout.custom_marker_view);
                mChart.setMarkerView(customMarkerView);
                mChart.getXAxis().setLabelCount(5, /*force: */true);
                mChart.getXAxis().setAxisMinimum(0);
                mChart.getXAxis().setAxisMaximum(29);

                //mChart.setVisibleXRangeMinimum(0);
                //mChart.setVisibleXRangeMaximum(29);
                mChart.notifyDataSetChanged();
                mChart.animateY(3000);

                ////////////////////////////BarChart///////////////////
                BarChart barChart;
                barChart = findViewById(R.id.barChartLottery);
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(29);

                BarDataSet barDataSet = new BarDataSet(BarEntries, barChartTitle);

                BarData data = new BarData(barDataSet);
                barChart.setData(data);
                barChart.invalidate();


                ////////////////////////////////////////////

                //moonPicture.getLayoutParams().width = mChart.getWidth()-40;

                /*checkGreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true){
                            Toast.makeText(getContext(), "Slick Rick" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.add(lineSetLottery);
                            mChart.notifyDataSetChanged();
                            mChart.animateY(1000);
                        }
                        if (isChecked == false){
                            Toast.makeText(getContext(), "False Creek" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.remove(lineSetLottery);
                            mChart.notifyDataSetChanged();
                            mChart.animateY(1000);

                        }
                        //lineDataSets.add(lineSet1);
                    }
                });

                checkRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true){
                            Toast.makeText(getContext(), "Slick Rick" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.add(lineSetRed);
                            mChart.notifyDataSetChanged();
                            mChart.animateY(1000);
                        }
                        if (isChecked == false){
                            Toast.makeText(getContext(), "False Creek" + "", Toast.LENGTH_SHORT).show();
                            lineDataSets.remove(lineSetRed);
                            mChart.notifyDataSetChanged();
                            mChart.animateY(1000);

                        }
                        //lineDataSets.add(lineSet1);
                    }
                });*/

            }
            ///////////////////////////////////////////////////////////////////////////
        }
        );
        this.findViewById(R.id.lotteryChartButton).callOnClick();

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
