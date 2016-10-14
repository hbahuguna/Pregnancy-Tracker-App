package com.example.hbahuguna.pregnancytipsntools.app.planner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hbahuguna.pregnancytipsntools.app.R;
import com.example.hbahuguna.pregnancytipsntools.app.data.BabyContract;
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by himanshu on 9/5/16.
 */
public class PlannerFragment extends Fragment implements OnDateSelectedListener {

    protected static final String TAG = "PlannerFragment";
    public static final String PLANNER_URI = "PLANNER_URI";
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    static final String CONCEPTION_DAY =  "day";
    static final String CONCEPTION_MONTH =  "month";
    static final String CONCEPTION_YEAR =  "year";
    static final String KEY =  "com.pregnancytipsntools";


    private Uri mUri;
    private View mRootView;

    private int mConceptionYear;
    private int mConceptionMonth;
    private int mConceptionDay;

    private ArrayList<CalendarDay> mDates;
    private Map<CalendarDay, List<String> > mEvents;

    @Bind(R.id.calendarView)
    MaterialCalendarView widget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(PlannerFragment.PLANNER_URI);
        }

        mRootView = inflater.inflate(R.layout.fragment_planner, container, false);
        Utils.toolBar(mRootView, (AppCompatActivity) getActivity());
        ButterKnife.bind(this.getActivity());

        widget = (MaterialCalendarView) mRootView.findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        DateTime d1 = new DateTime();
        mConceptionYear = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_YEAR,d1.year().get());
        mConceptionMonth = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_MONTH,d1.monthOfYear().get());
        mConceptionDay = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_DAY,d1.dayOfMonth().get());

        int birthYear = mConceptionYear;
        int birthMonth = mConceptionMonth + 11;
        if(mConceptionMonth > 3) {
            birthYear++;
            birthMonth -= 12;
        }

        Calendar instance = Calendar.getInstance();
        widget.setSelectedDate(instance.getTime());

        Calendar instance1 =  Calendar.getInstance();
        instance1.set(mConceptionYear, mConceptionMonth, mConceptionDay);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(birthYear, birthMonth, mConceptionDay);

        widget.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        widget.addDecorators(
                new MySelectorDecorator(this.getActivity()),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );

        new ApiSimulator(this.getActivity()).executeOnExecutor(Executors.newSingleThreadExecutor());
        return mRootView;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if(mDates.contains(date)) {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.Events.ALL_DAY, true);
            intent.putExtra(CalendarContract.Events.TITLE, mEvents.get(date).get(0));
            intent.putExtra(CalendarContract.Events.DESCRIPTION, mEvents.get(date).get(1));
            startActivity(intent);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().setTitle("Pregnancy Planner");
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        private final Activity activity;

        public ApiSimulator(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String[] planner = {BabyContract.PlannerEntry.COLUMN_ID,BabyContract.PlannerEntry.COLUMN_DETAIL,
                    BabyContract.PlannerEntry.COLUMN_DESCRIPTION};
            Cursor cursor = activity.getContentResolver().query(BabyContract.PlannerEntry.CONTENT_URI,
                    planner,
                    null,
                    null,
                    null,
                    null);
            List<Integer> days = new ArrayList<>();
            Map<Integer, List<String>> event = new HashMap<>();
            int x = 0;
            if (cursor.moveToFirst()){
                do{
                    int day = cursor.getInt(0);
                    days.add(day);
                    List<String> details = new ArrayList<>();
                    details.add(cursor.getString(1));
                    details.add(cursor.getString(2));
                    event.put(x++, details);
                } while(cursor.moveToNext());
            }
            List<Integer> daysDiff = new ArrayList<>();
            daysDiff.add(days.get(0));
            for(int i = 1; i < days.size(); i++) {
                int dayDiff = days.get(i) - days.get(i - 1);
                daysDiff.add(dayDiff);
            }
            cursor.close();
            Calendar calendar = Calendar.getInstance();
            calendar.set(mConceptionYear, mConceptionMonth, mConceptionDay);
            calendar.add(Calendar.DATE, daysDiff.get(0));
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 1; i < daysDiff.size(); i++) {
                CalendarDay calendarDay = CalendarDay.from(calendar);
                dates.add(calendarDay);
                calendar.add(Calendar.DATE, daysDiff.get(i));
            }
            CalendarDay calendarDay = CalendarDay.from(calendar);
            dates.add(calendarDay);
            Map<CalendarDay, List<String>> events = new HashMap<>();
            int i = 0;
            for(CalendarDay date: dates) {
                events.put(date, event.get(i++));
            }
            mDates = dates;
            mEvents = events;
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isRemoving()) {
                return;
            }

            widget.addDecorator(new EventDecorator(Color.RED, calendarDays, activity));
        }
    }
}
