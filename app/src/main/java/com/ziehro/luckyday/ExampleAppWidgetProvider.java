package com.ziehro.luckyday;

import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;
import static com.ziehro.luckyday.addDataFragment.postGreenLight;
import static com.ziehro.luckyday.addDataFragment.postRedLight;

import android.app.Fragment;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.RemoteViews;

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A widget provider.  We have a string that we pull from a preference in order to show
 * the configuration settings and the current time when the widget was updated.  We also
 * register a BroadcastReceiver for time-changed and timezone-changed broadcasts, and
 * update then too.
 *
 * <p>See also the following files:
 * <ul>
 *   <li>ExampleAppWidgetConfigure.java</li>
 *   <li>ExampleBroadcastReceiver.java</li>
 *   <li>res/layout/appwidget_configure.xml</li>
 *   <li>res/layout/appwidget_provider.xml</li>
 *   <li>res/xml/appwidget_provider.xml</li>
 * </ul>
 */

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    // log tag
    private static final String TAG = "ExampleAppWidgetProvider";
    public static String WIDGET_BUTTON_RED = "com.ziehro.luckyday.WIDGET_BUTTON_RED";
    public static String WIDGET_BUTTON_GREEN = "com.ziehro.luckyday.WIDGET_BUTTON_GREEN";
    private static final String MyOnClickRed = "myOnClickTag";
    private static final String MyOnClickGreen = "myOnClickTag1";
    public static String moonZodiac = "Hoot";
    private static final String MyOnClickZodiac = "Button OnClick";
    private static final String MyOnClickLeftSide = "Chaza";
    private static final String MyOnClickRightSide = "MyohMy";



    // Moon Age fo the widget construction.
    final Calendar c = Calendar.getInstance();
    final int hour = c.get(Calendar.HOUR_OF_DAY);
    final int minute = c.get(Calendar.MINUTE);
    final int second = c.get(Calendar.SECOND);
    final int date = c.get(Calendar.DATE);
    final int month = c.get(Calendar.MONTH);
    String time;
    final String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
    String moonDayString = "1";
    MoonPhase moonPhase1 = new MoonPhase(c);
    final double moonPhaseData = MoonPhase.phase(MoonPhase.calendarToJD(c));
    DecimalFormat df = new DecimalFormat("#.##");
    double moonPhaseData2 = Double.parseDouble(df.format(moonPhaseData));
    final String moonAgeString = String.valueOf(moonPhaseData2);
    final String moonPhaseString = moonPhase1.getPhaseIndexString(moonPhase1.getPhaseIndex());







        @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");
        // For each widget that needs an update, get the text that we should display:
        //   - Create a RemoteViews object for it
        //   - Set the text in the RemoteViews object
        //   - Tell the AppWidgetManager to show that views object for the widget.
        final int N = appWidgetIds.length;



        ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget_layout);
            views.setOnClickPendingIntent(R.id.widget_red_button, getPendingSelfIntent(context, MyOnClickRed));
            views.setOnClickPendingIntent(R.id.widget_green_button, getPendingSelfIntent(context, MyOnClickGreen));
            views.setOnClickPendingIntent(R.id.widgetZodiacTV, getPendingSelfIntent(context, MyOnClickZodiac));
            views.setOnClickPendingIntent(R.id.appwidget_text, getPendingSelfIntent(context, MyOnClickLeftSide));
            views.setOnClickPendingIntent(R.id.widgetIllumDisplayText, getPendingSelfIntent(context, MyOnClickRightSide));

            // Moon Age fo the widget construction.
            final Calendar c = Calendar.getInstance();
            final int hour = c.get(Calendar.HOUR_OF_DAY);
            final int minute = c.get(Calendar.MINUTE);
            final int second = c.get(Calendar.SECOND);
            final int date = c.get(Calendar.DATE);
            final int month = c.get(Calendar.MONTH);
            String time;
            final String monthname=(String) DateFormat.format("MMMM", new Date());
            String moonDayString = "1";
            MoonPhase moonPhase1 = new MoonPhase(c);
            final double moonPhaseData = MoonPhase.phase(MoonPhase.calendarToJD(c));
            DecimalFormat df = new DecimalFormat("#.##");
            double moonPhaseData2 = Double.parseDouble(df.format(moonPhaseData));
            final String moonAgeString = String.valueOf(moonPhaseData2);
            final String moonPhaseString = moonPhase1.getPhaseIndexString(moonPhase1.getPhaseIndex());
            moonDayString = moonPhase1.getMoonAgeAsDaysOnly();
            moonZodiac=moonPhase1.getMoonZodiac();


            String text = moonDayString;
            int moonPhaseIllum = ((int) moonPhase1.getPhase());
            views.setTextViewText(R.id.appwidget_text, text);
            views.setTextViewText(R.id.widgetIllumDisplayText, moonPhaseIllum + "% "+ moonPhaseString);
            views.setTextViewText(R.id.widgetZodiacTV, "The moon is in " + moonZodiac);

            //////////////////     Zodiac Page Buttons


            appWidgetManager.updateAppWidget(widgetId, views);
        }

    }



    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(TAG, "onDeleted");
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            ExampleAppWidgetConfigure.deleteTitlePref(context, appWidgetIds[i]);
        }
    }
    @Override
    public void onEnabled(Context context) {
        Log.d(TAG, "onEnabled");
        // When the first widget is created, register for the TIMEZONE_CHANGED and TIME_CHANGED
        // broadcasts.  We don't want to be listening for these if nobody has our widget active.
        // This setting is sticky across reboots, but that doesn't matter, because this will
        // be called after boot if there is a widget instance for this provider.
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("com.ziehro.luckyday", "com.ziehro.luckyday.WidgetBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);




    }
    @Override
    public void onDisabled(Context context) {
        // When the first widget is created, stop listening for the TIMEZONE_CHANGED and
        // TIME_CHANGED broadcasts.
        Log.d(TAG, "onDisabled");
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("com.ziehro.luckyday", "com.ziehro.luckyday.WidgetBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String titlePrefix) {
        Log.d(TAG, "updateAppWidget appWidgetId=" + appWidgetId + " titlePrefix=" + titlePrefix);
        // Getting the string this way allows the string to be localized.  The format
        // string is filled in using java.util.Formatter-style format strings.
        CharSequence text = context.getString(R.string.appwidget_text_format,
                ExampleAppWidgetConfigure.loadTitlePref(context, appWidgetId),
                "0x" + Long.toHexString(SystemClock.elapsedRealtime()));
        // Construct the RemoteViews object.  It takes the package name (in our case, it's our
        // package, but it needs this because on the other side it's the widget host inflating
        // the layout from our package).
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget_layout);


        // Moon Age fo the widget construction.
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        final int second = c.get(Calendar.SECOND);
        final int date = c.get(Calendar.DATE);
        final int month = c.get(Calendar.MONTH);
        String time;
        final String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        String moonDayString = "1";
        MoonPhase moonPhase1 = new MoonPhase(c);
        final double moonPhaseData = MoonPhase.phase(MoonPhase.calendarToJD(c));
        DecimalFormat df = new DecimalFormat("#.##");
        double moonPhaseData2 = Double.parseDouble(df.format(moonPhaseData));
        final String moonAgeString = String.valueOf(moonPhaseData2);
        final String moonPhaseString = moonPhase1.getPhaseIndexString(moonPhase1.getPhaseIndex());
        moonDayString = moonPhase1.getMoonAgeAsDaysOnly();
        moonZodiac=moonPhase1.getMoonZodiac();

        int moonPhaseIllum = ((int) moonPhase1.getPhase());
        text = moonDayString;
        views.setTextViewText(R.id.appwidget_text, text);
        views.setTextViewText(R.id.widgetIllumDisplayText, moonPhaseIllum + "% " + moonPhaseString);
        views.setTextViewText(R.id.widgetZodiacTV, "The moon is in " + moonZodiac);

        views.setOnClickPendingIntent(R.id.widget_red_button, getPendingSelfIntent(context, MyOnClickRed));
        views.setOnClickPendingIntent(R.id.widget_green_button, getPendingSelfIntent(context, MyOnClickGreen));
        views.setOnClickPendingIntent(R.id.widgetZodiacTV, getPendingSelfIntent(context, MyOnClickZodiac));
        views.setOnClickPendingIntent(R.id.appwidget_text, getPendingSelfIntent(context, MyOnClickLeftSide));
        views.setOnClickPendingIntent(R.id.widgetIllumDisplayText, getPendingSelfIntent(context, MyOnClickRightSide));




        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
        //String uid = "Bill";

        String uid = "Brah";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            uid = user.getUid(); //Do what you need to do with the id
        }

        if (MyOnClickRed.equals(intent.getAction())) {

            //RemoteViews remoteViews;
            //remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_appwidget_layout);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_appwidget_layout);
            remoteViews.setTextViewText(R.id.widgetIllumDisplayText, "Red");
            Toast.makeText(context, "Red", Toast.LENGTH_SHORT).show();
            //addDataFragment.postRedLight(uid, moonDayString);
            moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();
            postRedLight(uid,moonDayString);



        } else
        if (MyOnClickGreen.equals(intent.getAction())) {

            RemoteViews remoteViews;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_appwidget_layout);
            remoteViews.setTextViewText(R.id.widgetIllumDisplayText, "Green");
            Toast.makeText(context, "Green", Toast.LENGTH_SHORT).show();
            //addDataFragment.postRedLight(uid, moonDayString);
            moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();
            postGreenLight(uid,moonDayString);



        } else
        if (MyOnClickZodiac.equals(intent.getAction())) {

            int moonZodiacInt = 1;
            RemoteViews remoteViews;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_appwidget_layout);
            remoteViews.setTextViewText(R.id.widgetIllumDisplayText, "Green");
            Toast.makeText(context, "Zodiac", Toast.LENGTH_SHORT).show();
            //addDataFragment.postRedLight(uid, moonDayString);
            //moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();
            moonZodiac = moonPhase1.getMoonZodiac();
            switch(moonZodiac){
                case "Aries":
                    moonZodiacInt = 1;
                    break;
                case "Taurus":
                    moonZodiacInt = 2;
                    break;
                case"Gemini":
                    moonZodiacInt = 3;
                    break;
                case "Cancer":
                    moonZodiacInt = 4;
                    break;
                case "Leo":
                    moonZodiacInt = 5;
                    break;
                case "Virgo":
                    moonZodiacInt = 6;
                    break;
                case "Libra":
                    moonZodiacInt = 7;
                    break;
                case"Scorpio":
                    moonZodiacInt = 8;
                    break;
                case "Sagittarius":
                    moonZodiacInt = 9;
                    break;
                case "Capricorn":
                    moonZodiacInt = 10;
                    break;
                case "Aquarius":
                    moonZodiacInt = 11;
                    break;
                case "Pisces":
                    moonZodiacInt = 12;
                    break;
            }

            Intent n=new Intent(context, ScreenSlidePagerActivity.class);
            n.putExtra("EXTRA_SESSION_ID", moonZodiacInt-1);
            n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(n);

        }
        else
        if (MyOnClickLeftSide.equals(intent.getAction())) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Flag","0");
            editor.apply();
            Intent configIntent = new Intent(context, MainActivity.class);
            //configIntent.putExtra("key", 1);
            configIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(context, "Left", Toast.LENGTH_SHORT).show();
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, PendingIntent.FLAG_IMMUTABLE);
            context.startActivity(configIntent);
        }
        else
        if (MyOnClickRightSide.equals(intent.getAction())) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Flag","1");
            editor.apply();
            Intent configIntent = new Intent(context, MainActivity.class);
            configIntent.putExtra("frgToLoad", 1);
            configIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
            //PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            context.startActivity(configIntent);
        }
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, ExampleAppWidgetProvider.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
    }
}
