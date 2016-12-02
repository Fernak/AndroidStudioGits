package com.fernaak.epicworkout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;

public class WorkoutDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "workout.db";

    private static final int DATABASE_VERSION = 1;

    public WorkoutDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_WORKOUT_TABLE =  "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " ("
                + WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WorkoutEntry.COLUMN_EXERCISE_NAME + " TEXT NOT NULL, "
                + WorkoutEntry.COLUMN_EXERCISE_DESCRIPTION + " TEXT, "
                + WorkoutEntry.COLUMN_EXERCISE_BODY_AREA + " INTEGER NOT NULL, "
                + WorkoutEntry.COLUMN_EXERCISE_SETS + " INTEGER NOT NULL, "
                + WorkoutEntry.COLUMN_EXERCISE_REPS + " INTEGER NOT NULL);";

         db.execSQL(SQL_CREATE_WORKOUT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
