package com.example.hbahuguna.pregnancytipsntools.app;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.data.BabyContract;
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;

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
        Utils.toolBar(mRootView, (AppCompatActivity) getActivity());
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
        //getActivity().setTitle("Your Baby Today");
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
            mBabyDays.setText(mWeeks + " weeks");
            int weeksLeft = 40 - mWeeks;
            mBabyDaysLeft.setText(weeksLeft + " weeks to go!");
            String[] columns = {BabyContract.ItemsEntry.COLUMN_SIZE,BabyContract.ItemsEntry.COLUMN_DEVELOPMENT};
            String selection = BabyContract.ItemsEntry.COLUMN_ID;
            String[] selectionArgs = {Integer.toString(mWeeks)};
            Cursor cursor = this.getActivity().getContentResolver().query(BabyContract.ItemsEntry.CONTENT_URI,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null);
            mBabySize.setText("Your baby is now as big as " + cursor.getString(0));
            mBabyDevelopment.setText(cursor.getString(1));
            cursor.close();
        }
        //ad
        Utils.showAd(mRootView);
    }

}