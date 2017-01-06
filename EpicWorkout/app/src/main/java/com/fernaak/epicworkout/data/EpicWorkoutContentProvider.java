package com.fernaak.epicworkout.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class EpicWorkoutContentProvider extends android.content.ContentProvider {

    private static final int EXERCISES = 100;
    private static final int EXERCISES_ID = 101;

    private static final int WORKOUTS = 102;
    private static final int WORKOUTS_ID = 103;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(WorkoutContract.CONTENT_AUTHORITY, WorkoutContract.PATH_WORKOUT, WORKOUTS);
        sUriMatcher.addURI(WorkoutContract.CONTENT_AUTHORITY, WorkoutContract.PATH_WORKOUT + "/#", WORKOUTS_ID);

        sUriMatcher.addURI(ExerciseContract.CONTENT_AUTHORITY, ExerciseContract.PATH_EXERCISE, EXERCISES);
        sUriMatcher.addURI(ExerciseContract.CONTENT_AUTHORITY, ExerciseContract.PATH_EXERCISE + "/#", EXERCISES_ID);
    }
    private EpicWorkoutDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new EpicWorkoutDBHelper((getContext()));
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case EXERCISES:
                cursor = database.query(ExerciseContract.ExerciseEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case EXERCISES_ID:
                selection = ExerciseContract.ExerciseEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ExerciseContract.ExerciseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
            case WORKOUTS:
                cursor = database.query(WorkoutContract.WorkoutEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORKOUTS_ID:
                selection = WorkoutContract.WorkoutEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(WorkoutContract.WorkoutEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXERCISES:
                return ExerciseContract.ExerciseEntry.CONTENT_LIST_TYPE;
            case EXERCISES_ID:
                return ExerciseContract.ExerciseEntry.CONTENT_ITEM_TYPE;
            case WORKOUTS:
                return WorkoutContract.WorkoutEntry.CONTENT_LIST_TYPE;
            case WORKOUTS_ID:
                return WorkoutContract.WorkoutEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case EXERCISES:
                return insertExercise(uri, values);
            case WORKOUTS:
                return insertWorkouts(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXERCISES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(ExerciseContract.ExerciseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EXERCISES_ID:
                // Delete a single row given by the ID in the URI
                selection = ExerciseContract.ExerciseEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ExerciseContract.ExerciseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case WORKOUTS:
            // Delete all rows that match the selection and selection args
            rowsDeleted = database.delete(WorkoutContract.WorkoutEntry.TABLE_NAME, selection, selectionArgs);
            break;
            case WORKOUTS_ID:
                // Delete a single row given by the ID in the URI
                selection = WorkoutContract.WorkoutEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(WorkoutContract.WorkoutEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXERCISES:
                return updateExercise(uri, values, selection, selectionArgs);
            case EXERCISES_ID:
                selection = ExerciseContract.ExerciseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateExercise(uri, values, selection, selectionArgs);
            case WORKOUTS:
                return updateWorkouts(uri, values, selection, selectionArgs);
            case WORKOUTS_ID:
                selection = WorkoutContract.WorkoutEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateWorkouts(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private Uri insertExercise(Uri uri, ContentValues values){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(ExerciseContract.ExerciseEntry.TABLE_NAME, null, values);

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertWorkouts(Uri uri, ContentValues values){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, values);

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * This is what updates the exercise table
     */
    private int updateExercise(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if (values.containsKey(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_NAME)){
            String name = values.getAsString(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_NAME);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires name");
            }
        }
        if (values.containsKey(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_BODY_AREA)){
            String name = values.getAsString(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_BODY_AREA);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires body area");
            }
        }
        if (values.containsKey(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION)){
            String name = values.getAsString(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of sets");
            }
        }
        if (values.containsKey(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_RANK)){
            String name = values.getAsString(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_RANK);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.containsKey(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE)) {
            String name = values.getAsString(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE);
            if (name == null) {
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.containsKey(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE)) {
            String name = values.getAsString(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE);
            if (name == null) {
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.containsKey(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE)) {
            String name = values.getAsString(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE);
            if (name == null) {
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.size() == 0){
            return 0;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(ExerciseContract.ExerciseEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * This is what updates the workouts table
     */
    private int updateWorkouts(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if (values.containsKey(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_NAME)){
            String name = values.getAsString(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_NAME);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires name");
            }
        }
        if (values.containsKey(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_BODY_AREA)){
            String name = values.getAsString(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_BODY_AREA);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires body area");
            }
        }
        if (values.containsKey(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION)){
            String name = values.getAsString(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of sets");
            }
        }
        if (values.containsKey(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_RANK)){
            String name = values.getAsString(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_RANK);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.containsKey(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE)){
            String name = values.getAsString(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.containsKey(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_INFO)){
            String name = values.getAsString(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_INFO);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.size() == 0){
            return 0;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(WorkoutContract.WorkoutEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}