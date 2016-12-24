package com.fernaak.epicworkout.ui.exercise_items;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernaak.epicworkout.R;
import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;

/**
 * This class populates the listview with the respective values from the table column entries
 */
public class ExerciseCursorAdapter extends CursorAdapter {

    public ExerciseCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item , parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView)view.findViewById(R.id.name);
        TextView descriptionTextView = (TextView)view.findViewById(R.id.description);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);

        //This just finds the column we want to look at
        int nameColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION);
        int bodyAreaColumnIndex = cursor.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA);

        //This finds the value of the thing in the column that we found
        String exerciseName = cursor.getString(nameColumnIndex);
        String exerciseDescription = cursor.getString(descriptionColumnIndex);
        int exerciseBodyArea = cursor.getInt(bodyAreaColumnIndex);

        //Empty description protection
        if(TextUtils.isEmpty(exerciseDescription)){
            exerciseDescription = context.getString(R.string.unknown_exercise);
        }
        //Set the texts to the values of those found at column index
        nameTextView.setText(exerciseName);
        descriptionTextView.setText(exerciseDescription);
        //Figure out which body area the number refers to and set the respective image in listview
        switch(exerciseBodyArea){
            case 1:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_view_image_a));
                break;
            case 2:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_view_image_a));
                break;
            case 3:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_view_image_b));
                break;
            case 4:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_view_image_c));
                break;
            case 5:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_view_image_l));
                break;
            case 6:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_view_image_s));
                break;
            default:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_view_image_c));
                break;
        }
    }
}