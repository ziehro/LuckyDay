

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
        import com.google.firebase.firestore.FieldPath;
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

public class moreGraphs extends Fragment {

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
        return inflater.inflate(R.layout.more_graphs, container, false);

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

        final Calendar c = Calendar.getInstance();
        MoonPhase moonPhase1 = new MoonPhase(c);
        String moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {});
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        ArrayList<Entry> lineDataGreen = new ArrayList<>();
        ArrayList<Entry> lineDataRed = new ArrayList<>();

        BarChart bchart = (BarChart)getView().findViewById(R.id.chart);
        for (int i = (int) 0; i < 29 + 1; i++) {
            float val = (float) (Math.random());
        }

        for (int i = (int) 0; i < 10 + 1; i++) {
            float val = (float) (Math.random());

        }
        makeRedGreenChart(user, series,yVals1, lineDataGreen, lineDataRed);
        Log.d("YOOOOOOOOOOOOOOOOOOO", "HeeeeeeeeeeeeeeeeeeeeeRRRe" + yVals1);


        view.findViewById(R.id.chartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //////////////////////  Line Graphing Section  /////////////////
                LineChart mChart;
                mChart = (LineChart) getView().findViewById(R.id.redGreenChart);
                mChart.setTouchEnabled(true);
                mChart.setPinchZoom(true);
                LineDataSet lineSetGreen, lineSetRed;

                    lineSetGreen = new LineDataSet(lineDataGreen, "Green");
                    lineSetRed = new LineDataSet(lineDataRed, "Red");

                    lineSetGreen.setDrawIcons(false);
                    lineSetGreen.enableDashedLine(0, 1, 0);
                    lineSetRed.enableDashedLine(0, 1, 0);
                    lineSetGreen.enableDashedHighlightLine(10f, 5f, 0f);
                    lineSetGreen.setColor(R.color.dkGreen);
                    lineSetRed.setColor(R.color.fui_transparent);

                    lineSetGreen.setCircleColor(Color.GREEN);
                    lineSetRed.setCircleColor(Color.RED);
                    lineSetGreen.setLineWidth(0f);
                    lineSetRed.setLineWidth(0f);

                    lineSetGreen.setCircleRadius(7f);
                    lineSetRed.setCircleRadius(7f);
                    lineSetGreen.setDrawCircleHole(true);
                    lineSetGreen.setValueTextSize(3f);
                    lineSetGreen.setDrawFilled(false);
                    lineSetGreen.setHighlightEnabled(true);
                    lineSetGreen.setFormLineWidth(1f);
                    lineSetGreen.setDrawCircles(true);
                    lineSetRed.setDrawCircles(true);

                    lineSetGreen.setFormSize(15.f);
                    lineSetRed.setFormSize(15.f);

                    if (Utils.getSDKInt() >= 18) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_background);
                        //lineSet1.setFillDrawable(drawable);
                    } else {
                        //lineSet1.setFillColor(Color.DKGRAY);
                    }
                    ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
                    lineSetGreen.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    lineSetRed.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    lineDataSets.add(lineSetGreen);
                    lineDataSets.add(lineSetRed);

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

                }
                ///////////////////////////////////////////////////////////////////////////
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




        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "End" + yVals1.toString(), Toast.LENGTH_LONG ).show();
                NavHostFragment.findNavController(moreGraphs.this)
                        .navigate(R.id.action_ThirdFragment_to_SecondFragment);
            }
        });
    }


    public static void showToastMethod(Context context) {
        Toast.makeText(context, "mymessage ", Toast.LENGTH_SHORT).show();
    }

    public void makeRedGreenChart(FirebaseUser user, LineGraphSeries<DataPoint> series, ArrayList<BarEntry> yVals1, ArrayList<Entry> lineDataGreen,ArrayList<Entry> lineDataRed) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        String uid = "bob";
        uid=user.getUid();
        String finalUid = uid;

        CollectionReference referenceGreen = rootRef.collection("RedGreen").document(finalUid).collection("Data");
        Query moonDayOrder = referenceGreen.orderBy(FieldPath.documentId());
        moonDayOrder.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                referenceGreen.orderBy(FieldPath.documentId(), Query.Direction.DESCENDING);
                if (task.isSuccessful()) {
                    Double DataTotal = 0.0;
                    Log.d(TAG, "Graph point! " + "Yaaaahhhhhhooooooooooo");
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        DataTotal = 0.0;
                        //Double counter = Double.parseDouble(document.get("Counter").toString());
                        if (document.exists()) {
                            Double average = 0.0;
                            Log.d(TAG, "Graph point! " + document + DataTotal);
                            //if document.exists();
                            DataTotal = (Double.parseDouble(document.get("GreenLights").toString()));
                            //if (counter != 0.0) average = DataTotal/counter;
                            //else average = 3.5;
                            Double db = new Double(DataTotal);
                            float avgFloat = db.floatValue();
                            int id = Integer.valueOf(document.getId());
                            lineDataGreen.add(new Entry (id, avgFloat));
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

        CollectionReference referenceRed = rootRef.collection("RedGreen").document(finalUid).collection("Data");
        moonDayOrder = referenceRed.orderBy(FieldPath.documentId());
        moonDayOrder.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                referenceRed.orderBy(FieldPath.documentId(), Query.Direction.DESCENDING);
                if (task.isSuccessful()) {
                    Double DataTotal = 0.0;
                    Log.d(TAG, "Graph point! " + "Yaaaahhhhhhooooooooooo");
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        DataTotal = 0.0;
                        //Double counter = Double.parseDouble(document.get("Counter").toString());
                        if (document.exists()) {
                            Double average = 0.0;
                            Log.d(TAG, "Graph point! " + document + DataTotal);
                            DataTotal = (Double.parseDouble(document.get("RedLights").toString()));
                            //if (counter != 0.0) average = DataTotal/counter;
                            //else average = 3.5;
                            Double db = new Double(DataTotal);
                            float avgFloat = db.floatValue();
                            int id = Integer.valueOf(document.getId());
                            lineDataRed.add(new Entry (id, avgFloat));
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


/////////////////////////////////////
    }
}