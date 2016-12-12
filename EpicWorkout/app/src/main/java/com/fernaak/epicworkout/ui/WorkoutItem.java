package com.fernaak.epicworkout.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fernaak.epicworkout.R;
import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;

public class WorkoutItem extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_EXERCISE_LOADER = 0;
    private ImageView mWorkoutImage;
    private Uri mCurrentWorkoutUri;
    private TextView mWorkoutBodyArea;
    private TextView mWorkoutDescription;
    private VideoView mVideo;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_item);

        // Grabbing Resources From Layout
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        mWorkoutImage = (ImageView) findViewById(R.id.exercise_image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Set up the toolbar and display buttons
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mCurrentWorkoutUri = intent.getData();

        if(mCurrentWorkoutUri == null){
            invalidateOptionsMenu();
        }
        else{
            getSupportLoaderManager().initLoader(EXISTING_EXERCISE_LOADER, null, this);
        }
        mWorkoutImage = (ImageView) findViewById(R.id.exercise_image);
        mWorkoutBodyArea = (TextView) findViewById(R.id.text_view_body_areas);
        mWorkoutDescription = (TextView) findViewById(R.id.text_view_description);
        mVideo = (VideoView) findViewById(R.id.video_view);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        return new CursorLoader(this, WorkoutEntry.CONTENT_URI, workoutProjection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_NAME);
            int bodyareaColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_BODY_AREA);
            int descriptionColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION);
            int rankColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_RANK);
            int infoColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_INFO);
            int imageColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_IMAGE_REFERENCE);

            String workoutName = cursor.getString(nameColumnIndex);
            int workoutBodyArea = cursor.getInt(bodyareaColumnIndex);
            String workoutDescription = cursor.getString(descriptionColumnIndex);
            int workoutRank = cursor.getInt(rankColumnIndex);
            int workoutInfo = cursor.getInt(infoColumnIndex);
            String exerciseImage = cursor.getString(imageColumnIndex);

            collapsingToolbar.setTitle(workoutName);
            mWorkoutDescription.setText(workoutDescription);
            switch (workoutBodyArea) {
                case WorkoutEntry.BODY_AREA_ABS:
                    mWorkoutBodyArea.append("Abs");
                    break;
                case WorkoutEntry.BODY_AREA_ARMS:
                    mWorkoutBodyArea.append("Arms");
                    break;
                case WorkoutEntry.BODY_AREA_BACK:
                    mWorkoutBodyArea.append("Back");
                    break;
                case WorkoutEntry.BODY_AREA_CHEST:
                    mWorkoutBodyArea.append("Chest");
                    break;
                case WorkoutEntry.BODY_AREA_LEGS:
                    mWorkoutBodyArea.append("Legs");
                    break;
                case WorkoutEntry.BODY_AREA_SHOULDERS:
                    mWorkoutBodyArea.append("Shoulders");
                    break;
                default:
                    break;
            }
            mWorkoutImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gym_pic));
        }
    }

    @Override
    public void onLoaderReset (Loader < Cursor > loader) {
        return;
    }
}
