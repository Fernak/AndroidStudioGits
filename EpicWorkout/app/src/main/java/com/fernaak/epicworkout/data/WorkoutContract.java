package com.fernaak.epicworkout.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class WorkoutContract {

    private WorkoutContract() {}

    public static final String CONTENT_AUTHORITY = "com.fernaak.epicworkout";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EXERCISE = "exercise";

    public static final class WorkoutEntry implements BaseColumns{

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXERCISE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXERCISE;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EXERCISE);

        /**
         * The need table variables needed for the exercise table
         */
        public final static String TABLE_NAME = "exercise";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_EXERCISE_NAME = "name";
        public final static String COLUMN_EXERCISE_DESCRIPTION = "description";
        public final static String COLUMN_EXERCISE_BODY_AREA = "body_area";
        public final static String COLUMN_EXERCISE_SETS = "sets";
        public final static String COLUMN_EXERCISE_REPS = "reps";

        //Int values for the different areas of the body
        public static final int BODY_AREA_UNKNOWN = 0;
        public static final int BODY_AREA_ABS = 1;
        public static final int BODY_AREA_ARMS = 2;
        public static final int BODY_AREA_BACK = 3;
        public static final int BODY_AREA_CHEST = 4;
        public static final int BODY_AREA_LEGS = 5;
        public static final int BODY_AREA_SHOULDERS = 6;
    }
}
