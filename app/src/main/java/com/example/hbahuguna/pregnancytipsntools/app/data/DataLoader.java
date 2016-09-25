package com.example.hbahuguna.pregnancytipsntools.app.data;

import android.support.v4.content.CursorLoader;
import android.content.Context;

import android.net.Uri;

/**
 * Created by himanshu on 9/12/16.
 */
public class DataLoader extends CursorLoader {

    public static DataLoader newInstanceForGestationaAge(Context context, int itemId) {
        return new DataLoader(context, BabyContract.Items.buildItemUri(itemId));
    }

    private DataLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, BabyContract.Items.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                BabyContract.Items._ID,
                BabyContract.Items.GESTATIONAL_AGE,
                BabyContract.Items.SIZE,
                BabyContract.Items.DEVELOPMENT,
        };

        int _ID = 0;
        int GESTATIONAL_AGE = 1;
        int SIZE = 2;
        int DEVELOPMENT = 3;
    }
}
