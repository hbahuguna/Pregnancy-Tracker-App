package com.example.hbahuguna.pregnancytipsntools.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.data.DataAdapter;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

/**
 * Created by himanshu on 9/5/16.
 */
public class TodayFragment extends Fragment {

    private static final String TAG = "BabyTodayFragment";

    private static final String LOG_TAG = TodayFragment.class.getSimpleName();
    static final String TODAY_URI = "TODAY_URI";
    static final String CONCEPTION_DAY =  "day";
    static final String CONCEPTION_MONTH =  "month";
    static final String CONCEPTION_YEAR =  "year";
    static final String KEY =  "com.pregnancytipsntools";

    private Uri mUri;
    private TextView mBabyDays;
    private TextView mBabySize;
    private TextView mBabyDaysLeft;
    private TextView mBabyDevelopment;
    private View mRootView;
    private int mWeeks;


    public TodayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(TodayFragment.TODAY_URI);
        }

        mRootView = inflater.inflate(R.layout.fragment_today, container, false);
        bindViews();
        return mRootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeeks = getWeeks();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Your Baby Today");
    }

    private int getWeeks() {
        DateTime d1 = new DateTime();
        int conception_year = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_YEAR,d1.year().get());
        int conception_month = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_MONTH,d1.monthOfYear().get());
        int conception_day = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_DAY,d1.dayOfMonth().get());
        DateTime d2 = new DateTime(conception_year, conception_month, conception_day, 0, 0);
        return Weeks.weeksBetween(d2, d1).getWeeks();
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }
        DataAdapter mDbHelper = new DataAdapter(this.getActivity());
        mDbHelper.createDatabase();
        mDbHelper.open();
        mBabyDays = (TextView) mRootView.findViewById(R.id.days_value);
        mBabySize = (TextView) mRootView.findViewById(R.id.size_value);
        mBabyDaysLeft = (TextView) mRootView.findViewById(R.id.countDown_value);
        mBabyDevelopment = (TextView) mRootView.findViewById(R.id.development_value);
        mBabyDays.setText(mWeeks + " weeks");
        int weeksLeft = 40 - mWeeks;
        mBabyDaysLeft.setText(weeksLeft + " weeks to go!");
        mBabySize.setText("Your baby is now as big as a " + mDbHelper.getData(mWeeks, "size").getString(0));
        mBabyDevelopment.setText(mDbHelper.getData(mWeeks, "development").getString(0));
        mDbHelper.close();
    }

}