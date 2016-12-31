package com.fernaak.epicworkout.ui.exercise_items;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernaak.epicworkout.R;
import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;

public class ExerciseViewHolder extends RecyclerView.ViewHolder{

    //public CardView card;
    public TextView name;
    public ImageView img;

    public ExerciseViewHolder(View itemView) {
        super(itemView);
        //card = (CardView) itemView.findViewById(R.id.card_view);
        name = (TextView) itemView.findViewById(R.id.name);
        img = (ImageView) itemView.findViewById(R.id.thumbnail);
    }

    public void setData(Cursor c){
        name.setText(c.getString(c.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_NAME)));
        //card.setCardBackgroundColor(Color.parseColor("#ddffdd"));

        int exerciseBodyAreaColumn = c.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA);
        int exerciseBodyArea = c.getInt(exerciseBodyAreaColumn);

        switch (exerciseBodyArea){
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
