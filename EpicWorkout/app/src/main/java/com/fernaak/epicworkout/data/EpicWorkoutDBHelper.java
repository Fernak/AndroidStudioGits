package com.fernaak.epicworkout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;
import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;


public class EpicWorkoutDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "epicworkout.db";

    private static final int DATABASE_VERSION = 1;

    public EpicWorkoutDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_EXERCISE_TABLE =  "CREATE TABLE " + ExerciseEntry.TABLE_NAME + " ("
                + ExerciseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExerciseEntry.COLUMN_EXERCISE_NAME + " TEXT NOT NULL, "
                + ExerciseEntry.COLUMN_EXERCISE_BODY_AREA + " INTEGER NOT NULL, "
                + ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION + " TEXT, "
                + ExerciseEntry.COLUMN_EXERCISE_RANK + " INTEGER NOT NULL, "
                + ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE + " INTEGER NOT NULL, "
                + ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE + " TEXT, "
                + ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE + " TEXT);";


        String SQL_CREATE_WORKOUT_TABLE =  "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " ("
                + WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WorkoutEntry.COLUMN_WORKOUT_NAME + " TEXT NOT NULL, "
                + WorkoutEntry.COLUMN_WORKOUT_BODY_AREA + " INTEGER NOT NULL, "
                + WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION + " TEXT, "
                + WorkoutEntry.COLUMN_WORKOUT_RANK + " INTEGER NOT NULL, "
                + WorkoutEntry.COLUMN_WORKOUT_INFO + " TEXT, "
                + WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE + " TEXT);";


        db.execSQL(SQL_CREATE_EXERCISE_TABLE);
        db.execSQL(SQL_CREATE_WORKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}