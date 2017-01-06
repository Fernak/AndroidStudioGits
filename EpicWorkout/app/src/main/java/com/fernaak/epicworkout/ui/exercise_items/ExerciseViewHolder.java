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

    public CardView card;
    public TextView name;
    public ImageView thumbnail;
    public ImageView imgMenu;

    public ExerciseViewHolder(View itemView) {
        super(itemView);
        card = (CardView) itemView.findViewById(R.id.card_view);
        name = (TextView) itemView.findViewById(R.id.name);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        imgMenu = (ImageView) itemView.findViewById(R.id.overflow);
    }

    public void setData(Cursor c){
        name.setText(c.getString(c.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_NAME)));


        int exerciseBodyAreaColumn = c.getColumnIndex(ExerciseEntry.COLUMN_EXERCISE_BODY_AREA);
        int exerciseBodyArea = c.getInt(exerciseBodyAreaColumn);

        switch (exerciseBodyArea){
            case 1:
                thumbnail.setImageResource(R.drawable.list_view_image_a);
                //Teal background colour
                //card.setCardBackgroundColor(Color.parseColor("#00F8FF"));
                break;
            case 2:
                thumbnail.setImageResource(R.drawable.list_view_image_a);
                //Green background colour
                //card.setCardBackgroundColor(Color.parseColor("#48ff00"));

                break;
            case 3:
                thumbnail.setImageResource(R.drawable.list_view_image_b);
                //Maroon
                //card.setCardBackgroundColor(Color.parseColor("#ff0090"));

                break;
            case 4:
                thumbnail.setImageResource(R.drawable.list_view_image_c);
                //Red
                //card.setCardBackgroundColor(Color.parseColor("#ff0043"));
                break;
            case 5:
                thumbnail.setImageResource(R.drawable.list_view_image_l);
                //Yellow
                //card.setCardBackgroundColor(Color.parseColor("#fbff00"));
                break;
            case 6:
                thumbnail.setImageResource(R.drawable.list_view_image_s);
                //card.setCardBackgroundColor(Color.parseColor("#a14242"));
                break;
            default:
                thumbnail.setImageResource(R.drawable.list_view_image_c);
                //card.setCardBackgroundColor(Color.parseColor("#a14242"));
                break;
        }
    }
}
