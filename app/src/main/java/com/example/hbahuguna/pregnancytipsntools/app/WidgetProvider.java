package com.example.hbahuguna.pregnancytipsntools.app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.example.hbahuguna.pregnancytipsntools.app.data.BabyContract;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

/**
 * Created by himanshu on 10/9/16.
 */
public class WidgetProvider extends AppWidgetProvider {
    static final String CONCEPTION_DAY =  "day";
    static final String CONCEPTION_MONTH =  "month";
    static final String CONCEPTION_YEAR =  "year";
    static final String KEY =  "com.pregnancytipsntools";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.pregnancy_widget);
            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            DateTime d1 = new DateTime();
            int conception_year = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_YEAR, d1.year().get());
            int conception_month = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_MONTH, d1.monthOfYear().get()) + 1;
            int conception_day = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_DAY, d1.dayOfMonth().get());
            DateTime d2 = new DateTime(conception_year, conception_month, conception_day, 0, 0);
            int weeks = Weeks.weeksBetween(d2, d1).getWeeks();
            if (weeks < 0) return;
            String age = weeks + " weeks";
            String babyDevelopment = "Your baby has not conceived yet.";
            String babySize = "Your baby is as big as ";
            if (weeks >= 1) {
                String[] columns = {BabyContract.ItemsEntry.COLUMN_SIZE,BabyContract.ItemsEntry.COLUMN_DEVELOPMENT};
                String selection = BabyContract.ItemsEntry.COLUMN_ID ;
                String[] selectionArgs = {Integer.toString(weeks)};
                Cursor cursor = context.getContentResolver().query(BabyContract.ItemsEntry.CONTENT_URI,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null);
                babySize = babySize + cursor.getString(0);
                babyDevelopment = cursor.getString(1);
            }
            remoteViews.setTextViewText(R.id.baby_age, age);
            remoteViews.setTextViewText(R.id.baby_size, babySize);
            remoteViews.setTextViewText(R.id.baby_development, babyDevelopment);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
