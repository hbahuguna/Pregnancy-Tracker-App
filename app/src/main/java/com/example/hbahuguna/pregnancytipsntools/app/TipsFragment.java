package com.example.hbahuguna.pregnancytipsntools.app;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.data.DataAdapter;
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

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
        mTip = (TextView) mRootView.findViewById(R.id.tip);
        DataAdapter mDbHelper = new DataAdapter(this.getActivity());
        mDbHelper.createDatabase();
        mDbHelper.open();
        String filter = "where _id = " + mWeeks;
        mTip.setText(mDbHelper.getData("tip", "items", filter).getString(0));
        mDbHelper.close();
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
        getActivity().setTitle("Pregnancy tips");
    }

}
