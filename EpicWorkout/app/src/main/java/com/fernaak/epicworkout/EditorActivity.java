package com.fernaak.epicworkout;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;
import com.fernaak.epicworkout.data.WorkoutDbHelper;

import java.lang.reflect.Array;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_EXERCISE_LOADER = 0;

    private Uri mCurrentExerciseUri;

    /** EditText field to enter the exercise name */
    private EditText mNameEditText;

    /** EditText field to enter the exercise description*/
    private EditText mDescriptionEditText;

    /** Spinner field to enter the exercise sets */
    private Spinner mSetsSpinner;

    /** Spinner field to enter the exercise reps */
    private Spinner mRepsSpinner;

    /** Spinner field to enter the exercise body area */
    private Spinner mBodyAreaSpinner;

    private boolean mExerciseHasChanged = false;

    private int mSets = 0;
    private int mReps = 0;
    private int mBodyArea = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentExerciseUri = intent.getData();

        if(mCurrentExerciseUri == null){
            setTitle(getString(R.string.editor_activity_title));
            invalidateOptionsMenu();
        }
        else{
            setTitle(getString(R.string.editor_activity_edit_title));
            getSupportLoaderManager().initLoader(EXISTING_EXERCISE_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.etExerciseName);
        mDescriptionEditText = (EditText) findViewById(R.id.etExercieDescription);
        mBodyAreaSpinner = (Spinner) findViewById(R.id.spinner_body_area);
        mSetsSpinner = (Spinner) findViewById(R.id.spinner_sets);
        mRepsSpinner = (Spinner) findViewById(R.id.spinner_reps);

        mNameEditText.setOnTouchListener(mTouchListener);
        mDescriptionEditText.setOnTouchListener(mTouchListener);
        mBodyAreaSpinner.setOnTouchListener(mTouchListener);
        mSetsSpinner.setOnTouchListener(mTouchListener);
        mRepsSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter setsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_set_options, android.R.layout.simple_spinner_item);
        ArrayAdapter repsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_rep_options, android.R.layout.simple_spinner_item);
        ArrayAdapter bodyAreaSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_body_area_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        setsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        repsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        bodyAreaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSetsSpinner.setAdapter(setsSpinnerAdapter);
        mRepsSpinner.setAdapter(repsSpinnerAdapter);
        mBodyAreaSpinner.setAdapter(bodyAreaSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSetsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                mSets = Integer.parseInt(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){ }
        });
        mRepsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                mReps = Integer.parseInt(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Set the integer mSelected to the constant values
        mBodyAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.area_abs)))
                    {
                        mBodyArea = WorkoutEntry.BODY_AREA_ABS; // abs
                    }
                    else if (selection.equals(getString(R.string.area_arms)))
                    {
                        mBodyArea = WorkoutEntry.BODY_AREA_ARMS; // arms
                    }
                    else if (selection.equals(getString(R.string.area_back)))
                    {
                        mBodyArea = WorkoutEntry.BODY_AREA_BACK; // back
                    }
                    else if (selection.equals(getString(R.string.area_chest)))
                    {
                        mBodyArea = WorkoutEntry.BODY_AREA_CHEST; // chest
                    }
                    else if (selection.equals(getString(R.string.area_legs)))
                    {
                        mBodyArea = WorkoutEntry.BODY_AREA_LEGS; // legs
                    }
                    else if (selection.equals(getString(R.string.area_shoulders)))
                    {
                        mBodyArea = WorkoutEntry.BODY_AREA_SHOULDERS; // shoulders
                    }
                    else {
                        mBodyArea = WorkoutEntry.BODY_AREA_UNKNOWN;  // Unknown
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                mBodyArea = WorkoutEntry.BODY_AREA_UNKNOWN;  // Unknown
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mExerciseHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                };
        showUnsavedChangedDialog(discardButtonClickListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        if(mCurrentExerciseUri == null){
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save exercise to database
                saveExercise();
                //Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                if(!mExerciseHasChanged) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };
                showUnsavedChangedDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mExerciseHasChanged = true;
            return false;
        }
    };
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection ={
                WorkoutEntry._ID,
                WorkoutEntry.COLUMN_EXERCISE_NAME,
                WorkoutEntry.COLUMN_EXERCISE_DESCRIPTION,
                WorkoutEntry.COLUMN_EXERCISE_BODY_AREA,
                WorkoutEntry.COLUMN_EXERCISE_SETS,
                WorkoutEntry.COLUMN_EXERCISE_REPS };

        return new CursorLoader(this, mCurrentExerciseUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1){
            return;
        }
        if(cursor.moveToFirst()){
            int nameColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_EXERCISE_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_EXERCISE_DESCRIPTION);
            int bodyareaColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_EXERCISE_BODY_AREA);
            int setsColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_EXERCISE_SETS);
            int repsColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_EXERCISE_REPS);

            String exerciseName = cursor.getString(nameColumnIndex);
            String exerciseDescription = cursor.getString(descriptionColumnIndex);
            int exerciseBodyarea = cursor.getInt(bodyareaColumnIndex);
            int exerciseSets = cursor.getInt(setsColumnIndex);
            int exerciseReps = cursor.getInt(repsColumnIndex);

            mNameEditText.setText(exerciseName);
            mDescriptionEditText.setText(exerciseDescription);
            switch(exerciseBodyarea){
                case WorkoutEntry.BODY_AREA_ABS:
                    mBodyAreaSpinner.setSelection(1);
                    break;
                case WorkoutEntry.BODY_AREA_ARMS:
                    mBodyAreaSpinner.setSelection(2);
                    break;
                case WorkoutEntry.BODY_AREA_BACK:
                    mBodyAreaSpinner.setSelection(3);
                    break;
                case WorkoutEntry.BODY_AREA_CHEST:
                    mBodyAreaSpinner.setSelection(4);
                    break;
                case WorkoutEntry.BODY_AREA_LEGS:
                    mBodyAreaSpinner.setSelection(5);
                    break;
                case WorkoutEntry.BODY_AREA_SHOULDERS:
                    mBodyAreaSpinner.setSelection(6);
                    break;
                default:
                    mBodyAreaSpinner.setSelection(0);
                    break;
            }
            /**
            String[] setsArray;
            String[] repsArray;
            setsArray = getResources().getStringArray(R.array.array_set_options);
            repsArray = getResources().getStringArray(R.array.array_rep_options);
            for(int i = 0; i < setsArray.length; i++){
                if (i == exerciseSets){
                    mSetsSpinner.setSelection(i);
                }
            }
            for(int i = 0; i < repsArray.length; i++){
                if (i == exerciseReps){
                    mRepsSpinner.setSelection(i);
                }
            }
             */
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mDescriptionEditText.setText("");
        mBodyAreaSpinner.setSelection(0);
    }
    private void saveExercise(){
        String nameString = mNameEditText.getText().toString().trim();
        String descriptionString = mDescriptionEditText.getText().toString().trim();

        if (mCurrentExerciseUri == null && TextUtils.isEmpty(nameString)
                && TextUtils.isEmpty(nameString) && TextUtils.isEmpty(descriptionString)
                && mBodyArea == WorkoutEntry.BODY_AREA_UNKNOWN ) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(WorkoutEntry.COLUMN_EXERCISE_NAME, nameString);
        values.put(WorkoutEntry.COLUMN_EXERCISE_DESCRIPTION, descriptionString);
        values.put(WorkoutEntry.COLUMN_EXERCISE_BODY_AREA, mBodyArea);
        values.put(WorkoutEntry.COLUMN_EXERCISE_SETS, mSets);
        values.put(WorkoutEntry.COLUMN_EXERCISE_REPS, mReps);

        //Add new entry
        if (mCurrentExerciseUri == null) {
            getContentResolver().insert(WorkoutEntry.CONTENT_URI, values);
        }
        //Updates existing entry
        else{
            getContentResolver().update(mCurrentExerciseUri, values, null, null);
        }
    }
    private void showUnsavedChangedDialog(DialogInterface.OnClickListener discardButtonClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deletePet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deletePet() {
        if(mCurrentExerciseUri != null){
            getContentResolver().delete(mCurrentExerciseUri, null, null);
        }
        finish();
    }
}