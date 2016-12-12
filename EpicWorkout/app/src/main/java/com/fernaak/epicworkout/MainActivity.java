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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView mExerciseListView;
    private ExerciseCursorAdapter exercisCursorAdapter;
    private static final int LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exercises);

        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, TestingActivity.class);
                startActivity(intent);
            }
        });
        exercisCursorAdapter = new ExerciseCursorAdapter(this, null);
        mExerciseListView.setAdapter(exercisCursorAdapter);

        mExerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(MainActivity.this, ExerciseItem.class);

                Uri currentExerciseUri = ContentUris.withAppendedId(ExerciseEntry.CONTENT_URI, id);

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
        /**
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
         */
        mExerciseListView = (ListView) findViewById(R.id.list_view_exercise_list);
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
        exercisCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        exercisCursorAdapter.swapCursor(null);
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
}

