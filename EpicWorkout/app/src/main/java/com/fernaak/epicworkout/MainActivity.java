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

import com.fernaak.epicworkout.adapters.ExerciseRecyclerAdapter;
import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar mToolbar;
    private ImageView mExerciseImage;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_main);

        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, TestingActivity.class);
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
        ExerciseRecyclerAdapter mAdapter = new ExerciseRecyclerAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        mExerciseImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gym_pic));

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

        return new CursorLoader(this, ExerciseEntry.CONTENT_URI, exerciseProjection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String[] Columns = new String[]{
                ExerciseEntry._ID,
                ExerciseEntry.COLUMN_EXERCISE_NAME,
                ExerciseEntry.COLUMN_EXERCISE_BODY_AREA
        };
        MatrixCursor mx = new MatrixCursor(Columns);
        fillMx(data, mx);
        ((ExerciseRecyclerAdapter)mRecyclerView.getAdapter()).swapCursor(mx);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {   }

    //Initialize the components of the layout
    public void initializeScreen() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mExerciseImage = (ImageView) findViewById(R.id.exercise_image);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
    /**
     * Enter a garbage item to the table
     */
    private void insertExercise() {
        ContentValues exerciseValues = new ContentValues();
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_NAME, "Curls");
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA, ExerciseEntry.BODY_AREA_ABS);
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION, "Focus the peak");
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_RANK, 1);
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE, ExerciseEntry.WEIGHT_TYPE_DUMBBELL);
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE, "stuff");
        exerciseValues.put(ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE, "stuff");

        getContentResolver().insert(ExerciseEntry.CONTENT_URI, exerciseValues);
    }

    private void fillMx(Cursor data, MatrixCursor mx){
        if(data == null)
            return;
        data.moveToPosition(-1);
        while (data.moveToNext()){
            mx.addRow(new Object[]{
                    data.getString(data.getColumnIndex(ExerciseEntry._ID)),
                    data.getString(data.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_NAME)),
                    data.getString(data.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA))
            });
        }
    }
}

