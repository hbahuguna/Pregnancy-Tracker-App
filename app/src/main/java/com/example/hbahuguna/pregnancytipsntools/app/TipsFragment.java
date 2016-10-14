package com.example.hbahuguna.pregnancytipsntools.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class TipsFragment extends Fragment {

    private static final String TAG = "TipsFragment";

    private static final String LOG_TAG = TipsFragment.class.getSimpleName();
    static final String TIPS_URI = "TIPS_URI";

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
        mTip = (TextView) mRootView.findViewById(R.id.tip);
        if(mWeeks >= 1) {
            String[] tip = {BabyContract.ItemsEntry.COLUMN_TIP};
            String selection = BabyContract.ItemsEntry.COLUMN_ID ;
            String[] selectionArgs = {Integer.toString(mWeeks)};
            Cursor tipCursor = this.getActivity().getContentResolver().query(BabyContract.ItemsEntry.CONTENT_URI,
                    tip,
                    selection,
                    selectionArgs,
                    null,
                    null);
            mTip.setText(tipCursor.getString(0));
            tipCursor.close();
        }
        //ad
        Utils.showAd(mRootView);
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
    }

}
