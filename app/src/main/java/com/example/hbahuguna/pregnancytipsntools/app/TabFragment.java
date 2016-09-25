package com.example.hbahuguna.pregnancytipsntools.app;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by himanshu on 9/4/16.
 */
public class TabFragment {

    public static Fragment get(int menuItemId, Bundle savedInstanceState, Intent intent) {
        Fragment fragment = null;
        switch (menuItemId) {
            case R.id.today_item:

                if (savedInstanceState == null) {
                    // Create the today fragment and add it to the activity
                    // using a fragment transaction.

                    Bundle arguments = new Bundle();
                    arguments.putParcelable(TodayFragment.TODAY_URI, intent.getData());

                    fragment = new TodayFragment();
                    fragment.setArguments(arguments);

                }
                break;
            case R.id.planner_item:

                break;
            case R.id.tip_item:

                break;
            case R.id.quiz_item:

                break;
        }

        return fragment;

    }

}
