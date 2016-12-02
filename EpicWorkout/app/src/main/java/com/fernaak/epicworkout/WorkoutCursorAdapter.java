package com.fernaak.epicworkout;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;

public class WorkoutCursorAdapter extends CursorAdapter {

    public WorkoutCursorAdapter(Context context, Cursor c) {
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

        int nameColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_EXERCISE_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_EXERCISE_DESCRIPTION);

        String exerciseName = cursor.getString(nameColumnIndex);
        String exerciseDescription = cursor.getString(descriptionColumnIndex);

        if(TextUtils.isEmpty(exerciseDescription)){
            exerciseDescription = context.getString(R.string.unknown_exercise);
        }

        nameTextView.setText(exerciseName);
        descriptionTextView.setText(exerciseDescription);
    }
}
