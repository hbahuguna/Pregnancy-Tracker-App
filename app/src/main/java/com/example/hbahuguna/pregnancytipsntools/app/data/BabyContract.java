package com.example.hbahuguna.pregnancytipsntools.app.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by himanshu on 10/11/16.
 */
public class BabyContract {

    public static final String CONTENT_AUTHORITY = "com.example.hbahuguna.pregnancytipsntools.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_ITEMS = "items";
    public static final String PATH_PLANNER = "planner";
    public static final String PATH_QUEST = "quest";

    public static final class ItemsEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEMS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final String TABLE_NAME = "items";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_AGE = "gestational_age";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_DEVELOPMENT = "development";
        public static final String COLUMN_TIP = "tip";

        public static Uri buildItemsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class QuestEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUEST).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUEST;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUEST;

        public static final String TABLE_NAME = "quest";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_OPTION5 = "option5";
        public static final String COLUMN_OPTION6 = "option6";
        public static final String COLUMN_CATEGORY = "category";

        public static Uri buildQuestUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class PlannerEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLANNER).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLANNER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLANNER;

        public static final String TABLE_NAME = "planner";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DETAIL = "detail";
        public static final String COLUMN_DESCRIPTION = "description";

        public static Uri buildPlannerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
