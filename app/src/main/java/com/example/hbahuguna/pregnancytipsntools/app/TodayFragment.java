package com.example.hbahuguna.pregnancytipsntools.app;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.data.BabyContract;
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;

/**
 * Created by himanshu on 9/5/16.
 */
public class TodayFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "BabyTodayFragment";

    private static final String LOG_TAG = TodayFragment.class.getSimpleName();
    static final String TODAY_URI = "TODAY_URI";
    static final String[] COLUMNS = {BabyContract.ItemsEntry.COLUMN_SIZE, BabyContract.ItemsEntry.COLUMN_DEVELOPMENT};
    private String[] selectionArgs ;

    private Uri mUri;
    private TextView mBabyDays;
    private TextView mBabySize;
    private TextView mBabyDaysLeft;
    private TextView mBabyDevelopment;
    private View mRootView;
    private int mWeeks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(TodayFragment.TODAY_URI);
        }
        mRootView = inflater.inflate(R.layout.fragment_today, container, false);
        Utils.toolBar(mRootView, (AppCompatActivity) getActivity());
        mWeeks = Utils.getWeeks(this.getActivity());
        return mRootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        selectionArgs = new String[1];
        selectionArgs[0] = Integer.toString(mWeeks);
        return new CursorLoader(
            getActivity(),
            BabyContract.ItemsEntry.CONTENT_URI,
            COLUMNS,
            BabyContract.ItemsEntry.COLUMN_ID,
            selectionArgs,
            null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mBabyDays = (TextView) mRootView.findViewById(R.id.days_value);
        mBabySize = (TextView) mRootView.findViewById(R.id.size_value);
        mBabyDaysLeft = (TextView) mRootView.findViewById(R.id.countDown_value);
        mBabyDevelopment = (TextView) mRootView.findViewById(R.id.development_value);

        if (data != null && data.moveToFirst()) {
            if(mWeeks >= 1) {
                mBabyDays.setText(mWeeks + " weeks");
                int weeksLeft = 40 - mWeeks;
                mBabyDaysLeft.setText(weeksLeft + " weeks to go!");
                mBabySize.setText("Your baby is now as big as " + data.getString(0));
                mBabyDevelopment.setText(data.getString(1));
            }
            //ad
            Utils.showAd(mRootView);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

}