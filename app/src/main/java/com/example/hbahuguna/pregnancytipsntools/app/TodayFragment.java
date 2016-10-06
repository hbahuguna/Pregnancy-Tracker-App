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
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

/**
 * Created by himanshu on 9/5/16.
 */
public class TodayFragment extends Fragment {

    private static final String TAG = "BabyTodayFragment";

    private static final String LOG_TAG = TodayFragment.class.getSimpleName();
    static final String TODAY_URI = "TODAY_URI";

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
        mWeeks = Utils.getWeeks(this.getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Your Baby Today");
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }
        mBabyDays = (TextView) mRootView.findViewById(R.id.days_value);
        mBabySize = (TextView) mRootView.findViewById(R.id.size_value);
        mBabyDaysLeft = (TextView) mRootView.findViewById(R.id.countDown_value);
        mBabyDevelopment = (TextView) mRootView.findViewById(R.id.development_value);
        if(mWeeks >= 1) {
            DataAdapter mDbHelper = new DataAdapter(this.getActivity());
            mDbHelper.createDatabase();
            mDbHelper.open();
            mBabyDays.setText(mWeeks + " weeks");
            int weeksLeft = 40 - mWeeks;
            mBabyDaysLeft.setText(weeksLeft + " weeks to go!");
            String filter = "where _id = " + mWeeks;
            mBabySize.setText("Your baby is now as big as a " + mDbHelper.getData("size", "items", filter).getString(0));
            mBabyDevelopment.setText(mDbHelper.getData("development", "items", filter).getString(0));
            mDbHelper.close();
        }
    }

}