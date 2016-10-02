package com.example.hbahuguna.pregnancytipsntools.app.utils;

import android.app.Activity;
import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Weeks;

/**
 * Created by himanshu on 9/30/16.
 */
public class Utils {

    static final String CONCEPTION_DAY =  "day";
    static final String CONCEPTION_MONTH =  "month";
    static final String CONCEPTION_YEAR =  "year";
    static final String KEY =  "com.pregnancytipsntools";

    public static DateTime getConceptionDay(Activity activity, DateTime d1) {
        int conception_year = activity.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_YEAR,d1.year().get());
        int conception_month = activity.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_MONTH,d1.monthOfYear().get());
        int conception_day = activity.getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_DAY,d1.dayOfMonth().get());
        return new DateTime(conception_year, conception_month, conception_day, 0, 0);
    }

    public static int getDays(Activity activity) {
        DateTime d1 = new DateTime();
        return Days.daysBetween(Utils.getConceptionDay(activity, d1), d1).getDays();
    }

    public static int getWeeks(Activity activity) {
        DateTime d1 = new DateTime();
        return Weeks.weeksBetween(Utils.getConceptionDay(activity, d1), d1).getWeeks();
    }

}
