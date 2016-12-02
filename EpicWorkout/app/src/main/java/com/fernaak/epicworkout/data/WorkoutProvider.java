package com.fernaak.epicworkout.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.util.Log;

import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;

public class WorkoutProvider extends ContentProvider {

    private static final int EXERCISES = 100;
    private static final int EXERCISES_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(WorkoutContract.CONTENT_AUTHORITY, WorkoutContract.PATH_EXERCISE, EXERCISES);
        sUriMatcher.addURI(WorkoutContract.CONTENT_AUTHORITY, WorkoutContract.PATH_EXERCISE + "/#", EXERCISES_ID);
    }
    private WorkoutDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new WorkoutDbHelper((getContext()));
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
                cursor = database.query(WorkoutEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case EXERCISES_ID:
                selection = WorkoutEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(WorkoutEntry.TABLE_NAME, projection, selection, selectionArgs,
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
                return WorkoutEntry.CONTENT_LIST_TYPE;
            case EXERCISES_ID:
                return WorkoutEntry.CONTENT_ITEM_TYPE;
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
                rowsDeleted = database.delete(WorkoutEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EXERCISES_ID:
                // Delete a single row given by the ID in the URI
                selection = WorkoutEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(WorkoutEntry.TABLE_NAME, selection, selectionArgs);
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
                selection = WorkoutEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateExercise(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private Uri insertExercise(Uri uri, ContentValues values){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(WorkoutEntry.TABLE_NAME, null, values);

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    private int updateExercise(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if (values.containsKey(WorkoutEntry.COLUMN_EXERCISE_NAME)){
            String name = values.getAsString(WorkoutEntry.COLUMN_EXERCISE_NAME);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires name");
            }
        }
        if (values.containsKey(WorkoutEntry.COLUMN_EXERCISE_BODY_AREA)){
            String name = values.getAsString(WorkoutEntry.COLUMN_EXERCISE_BODY_AREA);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires body area");
            }
        }
        if (values.containsKey(WorkoutEntry.COLUMN_EXERCISE_SETS)){
            String name = values.getAsString(WorkoutEntry.COLUMN_EXERCISE_SETS);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of sets");
            }
        }
        if (values.containsKey(WorkoutEntry.COLUMN_EXERCISE_REPS)){
            String name = values.getAsString(WorkoutEntry.COLUMN_EXERCISE_REPS);
            if (name == null){
                throw new IllegalArgumentException("Exercise requires number of reps");
            }
        }
        if (values.size() == 0){
            return 0;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(WorkoutEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}

