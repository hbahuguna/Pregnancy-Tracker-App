package com.example.hbahuguna.pregnancytipsntools.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.hbahuguna.pregnancytipsntools.app.data.DataAdapter;
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

/**
 * Created by himanshu on 10/7/16.
 */
public class Receiver extends BroadcastReceiver {
    static final String CONCEPTION_DAY =  "day";
    static final String CONCEPTION_MONTH =  "month";
    static final String CONCEPTION_YEAR =  "year";
    static final String KEY =  "com.pregnancytipsntools";

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    public void showNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        DateTime d1 = new DateTime();
        int conception_year = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_YEAR,d1.year().get());
        int conception_month = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_MONTH,d1.monthOfYear().get()) + 1;
        int conception_day = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_DAY,d1.dayOfMonth().get());
        DateTime d2 = new DateTime(conception_year, conception_month, conception_day, 0, 0);
        int weeks = Weeks.weeksBetween(d2, d1).getWeeks();
        if(weeks < 0) return;
        String title = weeks + " weeks";
        String text = "Your baby has not conceived yet.";
        if(weeks >= 1) {
            DataAdapter mDbHelper = new DataAdapter(context);
            mDbHelper.createDatabase();
            mDbHelper.open();
            String filter = "where _id = " + weeks;
            text = mDbHelper.getData("development", "items", filter).getString(0);
            mDbHelper.close();
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.pregnant)
                .setContentTitle(title)
                .setContentText(text);
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
