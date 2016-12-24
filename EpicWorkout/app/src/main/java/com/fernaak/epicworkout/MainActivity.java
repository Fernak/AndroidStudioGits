package com.fernaak.epicworkout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;
import com.fernaak.epicworkout.ui.workout_items.WorkoutRecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayList<String> mItems = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private static final int LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_main);

        mItems = generateValues();
        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, TestingActivity.class);
                startActivity(intent);
            }
        });
        /**
         * RecyclerView
         */
        //mLayoutManager = new LinearLayoutManager(this); --> for a stacked card layout
        mLayoutManager = new GridLayoutManager(this, 2);//--> for a 2 row card layout
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WorkoutRecyclerAdapter(this, mItems);
        //mAdapter = new WorkoutRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        //getSupportLoaderManager().initLoader(LOADER, null, this);
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
                insertExercise();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                return true;
            case R.id.action_add_to_firebase:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Initialize the components of the layout
    public void initializeScreen() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] exerciseProjection = {
                ExerciseEntry._ID,
                ExerciseEntry.COLUMN_EXERCISE_NAME,
                ExerciseEntry.COLUMN_EXERCISE_BODY_AREA,
                ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION,
                ExerciseEntry.COLUMN_EXERCISE_RANK,
                ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE,
                ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE,
                ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE};

        return new CursorLoader(this, ExerciseEntry.CONTENT_URI, exerciseProjection, null, null, null);    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //exerciseCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //exerciseCursorAdapter.swapCursor(null);
    }

    /**
     * Enter a garbage item to the table
     */
    private void insertExercise() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues exerciseValues = new ContentValues();
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_NAME, "Drag Curls");
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA, ExerciseEntry.BODY_AREA_ABS);
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION, "Get them guns");
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_RANK, 1);
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE, ExerciseEntry.WEIGHT_TYPE_BARBELL);
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE, "stuff");
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE, "stuff");

        getContentResolver().insert(ExerciseEntry.CONTENT_URI, exerciseValues);
    }
    public static ArrayList<String> generateValues(){
        ArrayList<String> dummyValues = new ArrayList<>();
        for (int i = 1; i < 101; i++){
            dummyValues.add("Item " +i);
        }
        return dummyValues;
    }
}

