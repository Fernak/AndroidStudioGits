package com.fernaak.epicworkout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;
import com.fernaak.epicworkout.ui.workout_items.WorkoutRecyclerAdapter;

import java.util.ArrayList;

public class TestingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<String> mItems = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private static final int LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_main);

        mItems = generateValues();
        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(TestingActivity.this, MainActivity.class);
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

    //Initialize the components of the layout
    public void initializeScreen() {
        //mWorkoutListView = (ListView) findViewById(R.id.list_view_workouts_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
        //workoutCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //workoutCursorAdapter.swapCursor(null);
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
    public static ArrayList<String> generateValues(){
        ArrayList<String> dummyValues = new ArrayList<>();
        for (int i = 1; i < 101; i++){
            dummyValues.add("Item " +i);
        }
        return dummyValues;
    }
}

