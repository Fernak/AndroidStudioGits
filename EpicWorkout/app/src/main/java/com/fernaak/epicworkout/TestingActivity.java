package com.fernaak.epicworkout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fernaak.epicworkout.adapters.WorkoutRecyclerAdapter;
import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;

public class TestingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar mToolbar;
    private ImageView mWorkoutImage;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_main);

        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(TestingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Set up the toolbar and display buttons
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * RecyclerView
         */
        mLayoutManager = new GridLayoutManager(this, 2);//--> for a 2 row card layout
        mRecyclerView.setLayoutManager(mLayoutManager);
        WorkoutRecyclerAdapter mAdapter = new WorkoutRecyclerAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        mWorkoutImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bench_pic));

        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertWorkout();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                return true;
            case R.id.action_add_to_firebase:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] workoutProjection = {
                WorkoutEntry._ID,
                WorkoutEntry.COLUMN_WORKOUT_NAME,
                WorkoutEntry.COLUMN_WORKOUT_BODY_AREA,
                WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION,
                WorkoutEntry.COLUMN_WORKOUT_RANK,
                WorkoutEntry.COLUMN_WORKOUT_INFO,
                WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE};

        return new CursorLoader(this, WorkoutEntry.CONTENT_URI, workoutProjection, null, null, null);    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String[] Columns = new String[]{
                WorkoutEntry._ID,
                WorkoutEntry.COLUMN_WORKOUT_NAME,
                WorkoutEntry.COLUMN_WORKOUT_BODY_AREA
        };
        MatrixCursor mx = new MatrixCursor(Columns);
        fillMx(data, mx);
        ((WorkoutRecyclerAdapter)mRecyclerView.getAdapter()).swapCursor(mx);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //workoutCursorAdapter.swapCursor(null);
    }

    //Initialize the components of the layout
    public void initializeScreen() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWorkoutImage = (ImageView) findViewById(R.id.workout_image);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void insertWorkout() {

        ContentValues workoutValues = new ContentValues();
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_NAME, "Get Shredded");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_BODY_AREA, WorkoutEntry.BODY_AREA_ABS);
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION, "Get them guns");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_RANK, 1);
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_INFO, "stuff");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE, "stuff");

        getContentResolver().insert(WorkoutEntry.CONTENT_URI, workoutValues);
    }
    private void fillMx(Cursor data, MatrixCursor mx){
        if(data == null)
            return;
        data.moveToPosition(-1);
        while (data.moveToNext()){
            mx.addRow(new Object[]{
                    data.getString(data.getColumnIndex(WorkoutEntry._ID)),
                    data.getString(data.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_NAME)),
                    data.getString(data.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_BODY_AREA))
            });
        }
    }
}

