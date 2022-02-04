package com.ziehro.luckyday;


import static android.content.ContentValues.TAG;

import static com.ziehro.luckyday.ExampleAppWidgetProvider.WIDGET_BUTTON_RED;
import static com.ziehro.luckyday.ExampleAppWidgetProvider.WIDGET_BUTTON_GREEN;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;


public class WidgetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ExampleBroadcastReceiver", "intent=" + intent);
        String uid = "Bill";
        String moonDayString = "22";
        //For our example, we'll also update all of the widgets when the timezone
        //changes, or the user or network sets the time.
        String action = intent.getAction();
        /*if (WIDGET_BUTTON_RED.equals(intent.getAction())) {
            addDataFragment.postRedLight(uid, moonDayString);

        }
        if (WIDGET_BUTTON_GREEN.equals(intent.getAction())) {
            addDataFragment.postRedLight(uid, moonDayString);

        }
*/
        if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)
                || action.equals(Intent.ACTION_TIME_CHANGED)) {
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            ArrayList<Integer> appWidgetIds = new ArrayList<Integer>();
            ArrayList<String> texts = new ArrayList<String>();

            ExampleAppWidgetConfigure.loadAllTitlePrefs(context, appWidgetIds, texts);

            final int N = appWidgetIds.size();
            for (int i=0; i<N; i++) {
                ExampleAppWidgetProvider.updateAppWidget(context, gm, appWidgetIds.get(i), texts.get(i));
            }
        }
    }

}