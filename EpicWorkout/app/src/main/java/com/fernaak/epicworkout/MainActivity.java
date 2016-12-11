package com.fernaak.epicworkout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fernaak.epicworkout.data.EpicWorkoutDBHelper;
import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;
import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;
import com.fernaak.epicworkout.ui.ExercisesFragment;
import com.fernaak.epicworkout.ui.WorkoutsFragment;

public class MainActivity extends AppCompatActivity{

    private EpicWorkoutDBHelper mDbHelper;
    private TextView exerciseTextView;
    private TextView workoutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                displayDatabaseInfo();
            }
        });
        mDbHelper = new EpicWorkoutDBHelper(this);
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
        //ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
        //SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        //viewPager.setOffscreenPageLimit(2);
        //viewPager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(viewPager);
        exerciseTextView = (TextView) findViewById(R.id.text_view_exercises);
        workoutTextView = (TextView) findViewById(R.id.text_view_workouts);
    }
    /**
     *  The class that handels the pager
     */
    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Use positions (0 and 1) to find and instantiate fragments with newInstance()
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
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

        /**
         * Set string resources as titles for each fragment by it's position
        */
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

    private void insertExercise() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

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

        ContentValues workoutValues = new ContentValues();
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_NAME, "Get Shredded");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_BODY_AREA, ExerciseEntry.BODY_AREA_ABS);
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION, "Get them guns");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_RANK, 1);
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE, "stuff");
        workoutValues.put(WorkoutEntry.COLUMN_WORKOUT_INFO, "stuff");

        db.insert(ExerciseEntry.TABLE_NAME, null, exerciseValues);
        db.insert(WorkoutEntry.TABLE_NAME, null, workoutValues);
    }
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] exerciseProjection = {
                ExerciseEntry._ID,
                ExerciseEntry.COLUMN_EXERCISE_NAME,
                ExerciseEntry.COLUMN_EXERCISE_BODY_AREA,
                ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION,
                ExerciseEntry.COLUMN_EXERCISE_RANK,
                ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE,
                ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE,
                ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE};

        String[] workoutProjection = {
                WorkoutEntry._ID,
                WorkoutEntry.COLUMN_WORKOUT_NAME,
                WorkoutEntry.COLUMN_WORKOUT_BODY_AREA,
                WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION,
                WorkoutEntry.COLUMN_WORKOUT_RANK,
                WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE,
                WorkoutEntry.COLUMN_WORKOUT_INFO};

        // Perform a query on the pets table
        Cursor exerciseCursor = db.query(
                ExerciseEntry.TABLE_NAME,   // The table to query
                exerciseProjection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        Cursor workoutCursor = db.query(
                WorkoutEntry.TABLE_NAME,   // The table to query
                workoutProjection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        try {
            exerciseTextView.setText("The table contains " + exerciseCursor.getCount() + " exercises.\n\n");
            exerciseTextView.append(ExerciseEntry._ID + " - " +
                    ExerciseEntry.COLUMN_EXERCISE_NAME + " - " +
                    ExerciseEntry.COLUMN_EXERCISE_BODY_AREA + " - " +
                    ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION + "\n");
            workoutTextView.setText("The table contains " + workoutCursor.getCount() + " exercises.\n\n");
            workoutTextView.append(WorkoutEntry._ID + " - " +
                    WorkoutEntry.COLUMN_WORKOUT_NAME + " - " +
                    WorkoutEntry.COLUMN_WORKOUT_BODY_AREA + " - " +
                    WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION + "\n");


            // Figure out the index of each column
            int idExerciseColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry._ID);
            int nameExerciseColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_NAME);
            int bodyAreaExerciseColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA);
            int descriptionExerciseColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION);
            int rankColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_RANK);
            int weightTypeColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE);
            int imgRefColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE);
            int vidRefColumnIndex = exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE);

            int idWorkoutColumnIndex = workoutCursor.getColumnIndex(WorkoutEntry._ID);
            int nameWorkoutColumnIndex = workoutCursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_NAME);
            int bodyAreaWorkoutColumnIndex = workoutCursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_BODY_AREA);
            int descriptionWorkoutColumnIndex = workoutCursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION);

            // Iterate through all the returned rows in the cursor
            while (exerciseCursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = exerciseCursor.getInt(idExerciseColumnIndex);
                String currentName = exerciseCursor.getString(nameExerciseColumnIndex);
                int currentBodyArea = exerciseCursor.getInt(bodyAreaExerciseColumnIndex);
                String currentDescription = exerciseCursor.getString(descriptionExerciseColumnIndex);
                int currentRank = exerciseCursor.getInt(rankColumnIndex);
                int currentWeightType = exerciseCursor.getInt(weightTypeColumnIndex);
                String currentImageRef = exerciseCursor.getString(imgRefColumnIndex);
                String currentVidRef = exerciseCursor.getString(vidRefColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                exerciseTextView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentBodyArea + " - " +
                        currentDescription + " - " +
                        currentRank + " - " +
                        currentWeightType + " - " +
                        currentImageRef + " - " +
                        currentVidRef));
            }

            while (workoutCursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = workoutCursor.getInt(idWorkoutColumnIndex);
                String currentName = workoutCursor.getString(nameWorkoutColumnIndex);
                int currentBodyArea = workoutCursor.getInt(bodyAreaWorkoutColumnIndex);
                String currentDescription = workoutCursor.getString(descriptionWorkoutColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                workoutTextView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentBodyArea + " - " +
                        currentDescription));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            exerciseCursor.close();
            workoutCursor.close();
        }

    }
}

