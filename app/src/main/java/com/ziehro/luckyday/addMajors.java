package com.ziehro.luckyday;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.ziehro.luckyday.BaseActivity.sheetURL;

public class addMajors extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private FirebaseFirestore mFirestore;
    DatePicker dpDate;
    TimePicker tpTime;
    public int hour = 1;
    public int minute = 1;
    public Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_majors);
        dpDate = (DatePicker)findViewById(R.id.dpDate);
        final TextView timeIs;
        final TextView dateIs;
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        final int second = c.get(Calendar.SECOND);
        final int date = c.get(Calendar.DATE);
        final int month = c.get(Calendar.MONTH);
        final int year = c.get(Calendar.YEAR);
        String time;
        String currentDate;
        final String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        timeIs = (TextView) findViewById(R.id.currentTime2);
        dateIs = (TextView) findViewById(R.id.currentDate);
        if (minute < 10) time = ("" + hour + ":" + "0" + minute);
        else {time = ("" + hour + ":" + minute);}
        timeIs.setText(time);
        currentDate = String.valueOf(date) + String.valueOf(month) + String.valueOf(year);
        dateIs.setText(currentDate);


        final EditText descriptionString = (EditText)findViewById(R.id.descriptionInput);
        final TextView moonDayDisplay = (TextView)findViewById(R.id.moonDayMajorTV);
        //final EditText cribSold = (EditText)findViewById(R.id.cribSoldInput);
        final Editable description = descriptionString.getText();

        //Firestore Stuff
        mFirestore = FirebaseFirestore.getInstance();

        final String[] cribSoldString = {"0"};
        cribSoldString[0] = "0";
        Button T = (Button) findViewById(R.id.submitLotteryButton);
        T.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final ProgressDialog loading = ProgressDialog.show(addMajors.this, "Adding Item", "Please Wait");

                String time;
                //if (minute < 10) time = ("" + hour + ":" + "0" + minute);
                //else {time = ("" + hour + ":" + minute);}
                time = (String) timeIs.getText();
                String dateIs = ((monthname) + " " + String.valueOf(date));
                final String descriptionString = String.valueOf(description);


                String value2= moonDayDisplay.getText().toString();
                //int moneyOutInt= Integer.parseInt(value2);
                String value = descriptionString;
                //int moneyInInt= Integer.parseInt(value);

                MoonPhase moonPhase1 = new MoonPhase(calendar);

                final double moonPhaseData = MoonPhase.phase(MoonPhase.calendarToJD(calendar));
                DecimalFormat df = new DecimalFormat("#.##");
                double moonPhaseData2 = Double.parseDouble(df.format(moonPhaseData));
                final String moonAgeString = String.valueOf(moonPhaseData2);
                final String moonPhaseString = moonPhase1.getPhaseIndexString(moonPhase1.getPhaseIndex());
                final String moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();
                final String moonIllumString = String.valueOf(Math.abs(moonPhaseData2));

                Random rand = new Random();
                int randomNum = rand.nextInt((6 - 1) + 1) + 1;
                final String diceRollString = String.valueOf(randomNum);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, sheetURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                loading.dismiss();
                                Toast.makeText(addMajors.this,response, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("descriptionString", descriptionString);
                        params.put( "moonPhase", moonPhaseString);
                        //params.put( "moonDay", moonDayString);
                        params.put( "GoodBad", "good");


                        return params;

                    }
                };

                //  Firestore Stuff


                Map<String, String> params = new HashMap<>();
                params.put("descriptionString", descriptionString);
                params.put( "moonPhase", moonPhaseString);
                //params.put( "moonDay", moonDayString);
                params.put( "GoodBad", "bad");


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                String majorDate = (dpDate.getMonth()+1) + " " + (dpDate.getDayOfMonth()) +" " + (dpDate.getYear());
                //majorDate = "hi";
                String majorTime = (calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                //majorTime = "there";
                mFirestore.collection("Majors").document(uid).collection("Data").document(moonDayString).collection(majorDate).document(majorTime).set(params).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(@NonNull Object o) {
                        Toast.makeText(addMajors.this, "Added to FireStore",Toast.LENGTH_SHORT).show();
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addMajors.this,"FireStore Failed", Toast.LENGTH_LONG).show();
                    }
                });


                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        Button Time = (Button) findViewById(R.id.timeBtn);
        Time.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        /*Button Date = (Button) findViewById(R.id.dateButton);
        Time.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });*/

    }

    public void getDate(View v) {
        StringBuilder builder=new StringBuilder();
        builder.append("Current Date: ");
        builder.append((dpDate.getMonth() + 1)+"/");//month is 0 based
        builder.append(dpDate.getDayOfMonth()+"/");
        builder.append(dpDate.getYear());
        Toast.makeText(this, builder.toString(), Toast.LENGTH_SHORT).show();

        final TextView dateIs = (TextView) findViewById(R.id.currentDate);
        TextView moonDayDisplay = (TextView)findViewById(R.id.moonDayMajorTV);
        TextView moonPhaseDisplay = (TextView)findViewById(R.id.moonPhaseMajorTV);
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.SECOND, 0);
        //calendar.set(Calendar.MINUTE, minute);
        //calendar.set(Calendar.HOUR, hour);
        //calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.MONTH, dpDate.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, dpDate.getDayOfMonth());
        calendar.set(Calendar.YEAR,dpDate.getYear());
        String currentDate = (dpDate.getMonth()+1) + " " + (dpDate.getDayOfMonth()) +" " + (dpDate.getYear());
        dateIs.setText(currentDate);

        MoonPhase moonPhase1 = new MoonPhase(calendar);
        moonPhase1.updateCal(calendar);
        String moonDayString = moonPhase1.getMoonAgeAsDaysOnly();
        final String moonPhaseString = moonPhase1.getPhaseIndexString(moonPhase1.getPhaseIndex());;

        moonDayDisplay.setText(moonDayString);
        moonPhaseDisplay.setText(moonPhaseString);

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        MoonPhase moonPhase1 = new MoonPhase(calendar);
        TextView timeis2 = (TextView)findViewById(R.id.currentTime2);
        String time;
        if (minute < 10) {time = ("" + hour + ":" + "0" + minute);}
        else {time = ("" + hour + ":" + minute);}
        timeis2.setText(time);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        moonPhase1.updateCal(calendar);
        Toast.makeText(this, String.valueOf(hour), Toast.LENGTH_SHORT).show();
        //calendar.set(Calendar.AM_PM, Calendar.AM);
    }
}