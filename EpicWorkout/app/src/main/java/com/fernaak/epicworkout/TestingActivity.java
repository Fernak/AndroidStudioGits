package com.fernaak.epicworkout;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;
import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;
import com.fernaak.epicworkout.ui.ExerciseCursorAdapter;
import com.fernaak.epicworkout.ui.ExerciseItem;
import com.fernaak.epicworkout.ui.WorkoutItem;

public class TestingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView mWorkoutListView;
    private ExerciseCursorAdapter workoutCursorAdapter;
    private static final int LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workout);

        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(TestingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        workoutCursorAdapter = new ExerciseCursorAdapter(this, null);
        mWorkoutListView.setAdapter(workoutCursorAdapter);

        mWorkoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(TestingActivity.this, WorkoutItem.class);

                Uri currentExerciseUri = ContentUris.withAppendedId(WorkoutEntry.CONTENT_URI, id);

                intent.setData(currentExerciseUri);

                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(LOADER, null, this);
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
        mWorkoutListView = (ListView) findViewById(R.id.list_view_workouts_list);
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
        workoutCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        workoutCursorAdapter.swapCursor(null);
    }

    /**
     *  The class that handels the pager
     */
    /**
     public class SectionPagerAdapter extends FragmentStatePagerAdapter {

     public SectionPagerAdapter(FragmentManager fm) {
     super(fm);
     }

     @Override
     public Fragment getItem(int position) {

     Fragment fragment = null;

     switch (position) {
     case 0:
     fragment = ExercisesFragment.newInstance();
     break;
     case 1:
     fragment = WorkoutsFragment.newInstance();
     break;
     default:
     fragment = ExercisesFragment.newInstance();
     break;
     }

     return fragment;
     }


     @Override
     public int getCount() {
     return 2;
     }

     @Override
     public CharSequence getPageTitle(int position) {
     switch (position) {
     case 0:
     return getString(R.string.pager_title_Exercises);
     case 1:
     default:
     return getString(R.string.pager_title_Workouts);
     }
     }
     }
     */

    private void insertWorkout() {

        ContentValues workoutValues = new ContentValues();
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_NAME, "Get Shredded");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_BODY_AREA, ExerciseEntry.BODY_AREA_ABS);
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION, "Get them guns");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_RANK, 1);
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_INFO, "stuff");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE, "stuff");

        getContentResolver().insert(WorkoutEntry.CONTENT_URI, workoutValues);
    }

}

