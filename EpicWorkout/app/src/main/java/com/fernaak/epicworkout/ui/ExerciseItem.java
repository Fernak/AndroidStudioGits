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
import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;
import com.fernaak.epicworkout.data.WorkoutContract;

public class ExerciseItem extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_EXERCISE_LOADER = 0;
    private Uri mCurrentExerciseUri;
    private ImageView mExerciseImage;
    private TextView mExerciseBodyArea;
    private TextView mExerciseDescription;
    private VideoView mVideo;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_item);

        // Grabbing Resources From Layout
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        mExerciseImage = (ImageView) findViewById(R.id.exercise_image);
        mExerciseBodyArea = (TextView) findViewById(R.id.text_view_body_areas);
        mExerciseDescription = (TextView) findViewById(R.id.text_view_description);
        mVideo = (VideoView) findViewById(R.id.video_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Set up the toolbar and display buttons
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mCurrentExerciseUri = intent.getData();

        if(mCurrentExerciseUri == null){
            invalidateOptionsMenu();
        }
        else{
            getSupportLoaderManager().initLoader(EXISTING_EXERCISE_LOADER, null, this);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_NAME);
            int bodyareaColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA);
            int descriptionColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION);
            int rankColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_RANK);
            int weightTypeColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_WEIGHT_TYPE);
            int imageColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_IMAGE_REFERENCE);
            int videoColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_VIDEO_REFERENCE);

            String exerciseName = cursor.getString(nameColumnIndex);
            String exerciseDescription = cursor.getString(descriptionColumnIndex);
            int exerciseBodyarea = cursor.getInt(bodyareaColumnIndex);
            int exerciseRank = cursor.getInt(rankColumnIndex);
            int exerciseWeightType = cursor.getInt(weightTypeColumnIndex);
            String exerciseImage = cursor.getString(imageColumnIndex);
            String exerciseVideo = cursor.getString(videoColumnIndex);

            collapsingToolbar.setTitle(exerciseName);
            mExerciseDescription.setText(exerciseDescription);
            switch (exerciseBodyarea) {
                case ExerciseEntry.BODY_AREA_ABS:
                    mExerciseBodyArea.append("Abs");
                    break;
                case ExerciseEntry.BODY_AREA_ARMS:
                    mExerciseBodyArea.append("Arms");
                    break;
                case ExerciseEntry.BODY_AREA_BACK:
                    mExerciseBodyArea.append("Back");
                    break;
                case ExerciseEntry.BODY_AREA_CHEST:
                    mExerciseBodyArea.append("Chest");
                    break;
                case ExerciseEntry.BODY_AREA_LEGS:
                    mExerciseBodyArea.append("Legs");
                    break;
                case ExerciseEntry.BODY_AREA_SHOULDERS:
                    mExerciseBodyArea.append("Shoulders");
                    break;
                default:
                    break;
            }
            mExerciseImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gym_pic));
        }
    }

    @Override
    public void onLoaderReset (Loader < Cursor > loader) {
        return;
    }
}
