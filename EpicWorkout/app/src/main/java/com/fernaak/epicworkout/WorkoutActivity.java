package com.fernaak.epicworkout;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;


public class WorkoutActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXERCISE_LOADER = 0;

    WorkoutCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView exerciseListView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        exerciseListView.setEmptyView(emptyView);

        mCursorAdapter = new WorkoutCursorAdapter(this, null);
        exerciseListView.setAdapter(mCursorAdapter);

        //Fires when a list item is clicked
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WorkoutActivity.this, EditorActivity.class);

                Uri currentExerciseUri = ContentUris.withAppendedId(WorkoutEntry.CONTENT_URI, id);

                intent.setData(currentExerciseUri);

                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(EXERCISE_LOADER, null, this);
    }

    private void insertExercise(){

        ContentValues values = new ContentValues();

        values.put(WorkoutEntry.COLUMN_EXERCISE_NAME, "Curls");
        values.put(WorkoutEntry.COLUMN_EXERCISE_DESCRIPTION, "Builds Peak of bicep");
        values.put(WorkoutEntry.COLUMN_EXERCISE_BODY_AREA, WorkoutEntry.BODY_AREA_ARMS);
        values.put(WorkoutEntry.COLUMN_EXERCISE_SETS, 7);
        values.put(WorkoutEntry.COLUMN_EXERCISE_REPS, 20);

        Uri newUri = getContentResolver().insert(WorkoutEntry.CONTENT_URI, values);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertExercise();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * These are the only values that will show in the listview
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                WorkoutEntry._ID,
                WorkoutEntry.COLUMN_EXERCISE_NAME,
                WorkoutEntry.COLUMN_EXERCISE_DESCRIPTION,
                WorkoutEntry.COLUMN_EXERCISE_BODY_AREA};

        return new CursorLoader(this, WorkoutEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
    private void deleteAllPets(){
        getContentResolver().delete(WorkoutEntry.CONTENT_URI, null, null);
    }
}
