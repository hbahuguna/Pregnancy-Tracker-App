package com.example.hbahuguna.pregnancytipsntools.app.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by himanshu on 10/11/16.
 */
public class BabyDataProvider extends ContentProvider {

    protected static final String TAG = "BabyDataProvider";

    private SQLiteDatabase db;
    public static final UriMatcher uriMatcher = buildUriMatcher();
    DataAdapter mDbHelper;

    static final int ITEMS = 100;
    static final int QUEST = 200;
    static final int PLANNER = 300;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BabyContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, BabyContract.PATH_ITEMS, ITEMS);
        matcher.addURI(authority, BabyContract.PATH_PLANNER, PLANNER);
        matcher.addURI(authority, BabyContract.PATH_QUEST, QUEST);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mDbHelper = new DataAdapter(getContext());
        mDbHelper.createDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String filter = "";
        if(selection != null)
            filter = "WHERE " + selection + " = " + selectionArgs[0];
        String fields = projection[0];
        for(int i = 1; i < projection.length; i++) {
            fields += ", " + projection[i];
        }
        mDbHelper.open();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                cursor = mDbHelper.getData(fields, BabyContract.ItemsEntry.TABLE_NAME, filter);
                break;

            case QUEST:
                cursor = mDbHelper.getData(fields, BabyContract.QuestEntry.TABLE_NAME, filter);
                break;

            case PLANNER:
                cursor = mDbHelper.getData(fields, BabyContract.PlannerEntry.TABLE_NAME, filter);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        mDbHelper.close();
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);

        switch (match) {
            case ITEMS:
                return BabyContract.ItemsEntry.CONTENT_ITEM_TYPE;
            case QUEST:
                return BabyContract.QuestEntry.CONTENT_ITEM_TYPE;
            case PLANNER:
                return BabyContract.PlannerEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
