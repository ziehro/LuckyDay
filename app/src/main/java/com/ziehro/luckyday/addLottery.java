package com.ziehro.luckyday;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
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

public class addLottery extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lottery);

        final TextView timeIs;
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        final int second = c.get(Calendar.SECOND);
        final int date = c.get(Calendar.DATE);
        final int month = c.get(Calendar.MONTH);
        String time;
        final String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        timeIs = (TextView) findViewById(R.id.currentTime2);
        if (minute < 10) time = ("" + hour + ":" + "0" + minute);
        else {time = ("" + hour + ":" + minute);}
        timeIs.setText(time);


        final EditText moneyIn = (EditText)findViewById(R.id.descriptionInput);
        final EditText moneyOut = (EditText)findViewById(R.id.moonDayMajorTV);
        //final EditText cribSold = (EditText)findViewById(R.id.cribSoldInput);

        final Editable moneyInValue = moneyIn.getText();
        final Editable moneyOutValue = moneyOut.getText();
        //final Editable cribSoldValue = cribSold.getText();

        //Firestore Stuff
        mFirestore = FirebaseFirestore.getInstance();


        final String[] cribSoldString = {"0"};
        cribSoldString[0] = "0";
        Button T = (Button) findViewById(R.id.submitLotteryButton);
        T.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final ProgressDialog loading = ProgressDialog.show(addLottery.this, "Adding Item", "Please Wait");

                String time;
                //if (minute < 10) time = ("" + hour + ":" + "0" + minute);
                //else {time = ("" + hour + ":" + minute);}
                time = (String) timeIs.getText();


                String dateIs = ((monthname) + " " + String.valueOf(date));
                final String moneyInString = String.valueOf(moneyInValue);
                final String moneyOutString = String.valueOf(moneyOutValue);
                //if (String.valueOf(cribSoldValue)!=null)  cribSoldString[0] = String.valueOf(cribSoldValue);


                String value2= moneyOut.getText().toString();
                int moneyOutInt= Integer.parseInt(value2);
                String value = moneyIn.getText().toString();
                int moneyInInt= Integer.parseInt(value);

                int percent = 0;
                if (moneyInInt!=0) percent = moneyOutInt/moneyInInt*50;
                final String percentString = String.valueOf(percent);

                MoonPhase moonPhase1 = new MoonPhase(c);

                final double moonPhaseData = MoonPhase.phase(MoonPhase.calendarToJD(c));
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
                                Toast.makeText(addLottery.this,response, Toast.LENGTH_LONG).show();
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
                        params.put("action", "addItem");
                        params.put("moneyIn", moneyInString);
                        params.put( "moneyOut", moneyOutString);
                        params.put( "percent", percentString);
                        params.put( "moonPhase", moonPhaseString);
                        params.put( "moonAge", moonAgeString);
                        params.put( "moonIllum", moonIllumString);
                        params.put( "moonDay", moonDayString);
                        params.put( "cribSold", cribSoldString[0]);
                        params.put( "diceRoll", diceRollString);

                        return params;

                    }
                };


                //RequestQueue queue = Volley.newRequestQueue(addLottery.this);
                //queue.add(stringRequest);


                //  Firestore Stuff


                    Map<String, String> params = new HashMap<>();
                    params.put("action", "addItem");
                    params.put("moneyIn", moneyInString);
                    params.put( "moneyOut", moneyOutString);
                    params.put( "percent", percentString);
                    params.put( "moonPhase", moonPhaseString);
                    params.put( "moonAge", moonAgeString);
                    params.put( "moonIllum", moonIllumString);
                    params.put( "moonDay", moonDayString);
                    params.put( "cribSold", cribSoldString[0]);
                    params.put( "diceRoll", diceRollString);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();

                    mFirestore.collection("Lottery").document(uid).collection("Data").document(time + ":" + second +" " + monthname + " " +date).set(params).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(@NonNull Object o) {
                            Toast.makeText(addLottery.this, "Added to FireStore",Toast.LENGTH_SHORT).show();
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addLottery.this,"FireStore Failed", Toast.LENGTH_LONG).show();
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

    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        TextView timeis2 = (TextView)findViewById(R.id.currentTime2);
        String time;
        if (minute < 10) {time = ("" + hour + ":" + "0" + minute);}
        else {time = ("" + hour + ":" + minute);}
        timeis2.setText(time);
    }
}