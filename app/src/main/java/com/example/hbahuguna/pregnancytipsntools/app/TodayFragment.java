package com.example.hbahuguna.pregnancytipsntools.app;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.data.BabyContract;
import com.example.hbahuguna.pregnancytipsntools.app.data.DataAdapter;
import com.example.hbahuguna.pregnancytipsntools.app.data.DataLoader;

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

    private Cursor mCursor;

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
        //getLoaderManager().initLoader(0, null, this);
    }

    private int getWeeks() {
        DateTime d1 = new DateTime();
        int conception_year = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_YEAR,d1.year().get());
        int conception_month = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_MONTH,d1.monthOfYear().get());
        int conception_day = this.getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE).getInt(CONCEPTION_DAY,d1.dayOfMonth().get());
        DateTime d2 = new DateTime(conception_year, conception_month, conception_day, 0, 0);
        return Weeks.weeksBetween(d2, d1).getWeeks();
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return DataLoader.newInstanceForGestationaAge(this.getActivity(), mWeeks);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e(TAG, "Error reading detail cursor");
            mCursor.close();
            mCursor = null;
        }
        bindViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
        bindViews();
    }*/

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
        mBabySize.setText(mDbHelper.getData(mWeeks, "size").getString(0));
        mBabyDevelopment.setText(mDbHelper.getData(mWeeks, "development").getString(0));
        mDbHelper.close();
    }

}