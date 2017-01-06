package com.fernaak.epicworkout;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<ExerciseObject> {
    public ExerciseAdapter(Context context, int resource, List<ExerciseObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.exercise_cards, parent, false);
        }

        ImageView exerciseImageView = (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.description);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);

        ExerciseObject item = getItem(position);

        nameTextView.setText(item.getName());
        descriptionTextView.setText(item.getDescription());


        switch (item.getArea()) {
            case "abs":
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_a));
                break;
            case "biceps":
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_a));
                break;
            case "tricepts":
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_a));
                break;
            case "back":
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_b));
                break;
            case "chest":
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_c));
                break;
            case "legs":
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_l));
                break;
            case "shoulders":
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_s));
                break;
            default:
                exerciseImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_view_image_c));
                break;
        }
        return convertView;
    }
}
