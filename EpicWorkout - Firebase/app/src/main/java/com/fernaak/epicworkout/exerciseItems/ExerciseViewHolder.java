package com.fernaak.epicworkout.exerciseItems;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernaak.epicworkout.R;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {
    private ImageView exerciseImageView;
    private TextView descriptionTextView;
    private TextView nameTextView;
    public View itemView;

    public ExerciseViewHolder(View itemView) {
        super(itemView);
        exerciseImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
        descriptionTextView = (TextView) itemView.findViewById(R.id.description);
        nameTextView = (TextView) itemView.findViewById(R.id.name);
        this.itemView = itemView;
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }
    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }
}
