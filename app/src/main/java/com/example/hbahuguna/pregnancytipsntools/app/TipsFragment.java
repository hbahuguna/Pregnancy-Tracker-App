package com.example.hbahuguna.pregnancytipsntools.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.data.BabyContract;
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;

/**
 * Created by himanshu on 10/2/16.
 */
public class TipsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "TipsFragment";

    private static final String LOG_TAG = TipsFragment.class.getSimpleName();
    static final String TIPS_URI = "TIPS_URI";
    static final String[] COLUMNS = {BabyContract.ItemsEntry.COLUMN_TIP};
    private String[] selectionArgs ;


    private Uri mUri;
    private View mRootView;
    private int mWeeks;
    private TextView mTip;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(TipsFragment.TIPS_URI);
        }

        mRootView = inflater.inflate(R.layout.fragment_tip, container, false);
        Utils.toolBar(mRootView, (AppCompatActivity) getActivity());
        return mRootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeeks = Utils.getWeeks(this.getActivity());
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
        mTip = (TextView) mRootView.findViewById(R.id.tip);
        if (data != null && data.moveToFirst()) {
            if (mWeeks >= 1) {
                mTip.setText(data.getString(0));
            }
            //ad
            Utils.showAd(mRootView);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

}
