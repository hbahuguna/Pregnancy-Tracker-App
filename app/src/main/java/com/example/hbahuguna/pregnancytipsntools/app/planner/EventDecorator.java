package com.example.hbahuguna.pregnancytipsntools.app.planner;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.hbahuguna.pregnancytipsntools.app.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by himanshu on 9/25/16.
 */
public class EventDecorator implements DayViewDecorator {
    private int color;
    private HashSet<CalendarDay> dates;
    private final Drawable drawable;

    public EventDecorator(int color, Collection<CalendarDay> dates, Activity activity) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        drawable = activity.getResources().getDrawable(R.drawable.my_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
        //view.setSelectionDrawable(drawable);
    }
}
