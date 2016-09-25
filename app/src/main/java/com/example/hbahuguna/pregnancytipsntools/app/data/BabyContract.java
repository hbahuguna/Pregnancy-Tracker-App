package com.example.hbahuguna.pregnancytipsntools.app.data;

import android.net.Uri;

/**
 * Created by himanshu on 9/12/16.
 */
public class BabyContract {
    public static final String CONTENT_AUTHORITY = "com.example.hbahuguna.pregnancytipsntools";
    public static final Uri BASE_URI = Uri.parse("content://com.example.hbahuguna.pregnancytipsntools");

    interface BabyColumns {
        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String _ID = "_id";
        /** Type: TEXT NOT NULL*/
        String GESTATIONAL_AGE = "gestational_age";
        /** Type: TEXT NOT NULL */
        String  SIZE = "size";
        /** Type: TEXT NOT NULL */
        String DEVELOPMENT = "development";
    }

    public static class Items implements BabyColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.pregnancytipsntools.items";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.pregnancytipsntools.items";

        public static final String DEFAULT_SORT = _ID ;

        /** Matches: /items/ */
        public static Uri buildDirUri() {
            return BASE_URI.buildUpon().appendPath("items").build();
        }

        /** Matches: /items/[_id]/ */
        public static Uri buildItemUri(int _id) {
            return BASE_URI.buildUpon().appendPath("items").appendPath(Integer.toString(_id)).build();
        }

        /** Read item ID item detail URI. */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
    }

    private BabyContract() {
    }


}
