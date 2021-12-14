package com.ziehro.luckyday;

import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class RedLightGreenLight extends AppCompatActivity {

    final Calendar c = Calendar.getInstance();
    final int hour = c.get(Calendar.HOUR_OF_DAY);
    final int minute = c.get(Calendar.MINUTE);
    final int date = c.get(Calendar.DATE);
    final int month = c.get(Calendar.MONTH);
    String time;
    final String monthName=(String)android.text.format.DateFormat.format("MMMM", new Date());

    public boolean RedLight() {


        return true;
    }

    public boolean GreenLight() {


        return true;
    }


}
