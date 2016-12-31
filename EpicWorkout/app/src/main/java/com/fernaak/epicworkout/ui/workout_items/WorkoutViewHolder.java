package com.fernaak.epicworkout.ui.workout_items;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernaak.epicworkout.R;
import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;

public class WorkoutViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public ImageView img;

    public WorkoutViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        img = (ImageView) itemView.findViewById(R.id.thumbnail);
    }
    public void setData(Cursor c){
        name.setText(c.getString(c.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_NAME)));
        int WorkoutBodyAreaColumn = c.getColumnIndex(WorkoutEntry.COLUMN_WORKOUT_BODY_AREA);
        int workoutBodyArea = c.getInt(WorkoutBodyAreaColumn);

        switch (workoutBodyArea){
            case 1:
                img.setImageResource(R.drawable.list_view_image_a);
                break;
            case 2:
                img.setImageResource(R.drawable.list_view_image_a);
                break;
            case 3:
                img.setImageResource(R.drawable.list_view_image_b);
                break;
            case 4:
                img.setImageResource(R.drawable.list_view_image_c);
                break;
            case 5:
                img.setImageResource(R.drawable.list_view_image_l);
                break;
            case 6:
                img.setImageResource(R.drawable.list_view_image_s);
                break;
            default:
                img.setImageResource(R.drawable.list_view_image_c);
                break;
        }
    }
}
