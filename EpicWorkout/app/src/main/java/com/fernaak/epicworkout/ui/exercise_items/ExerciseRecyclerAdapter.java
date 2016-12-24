package com.fernaak.epicworkout.ui.exercise_items;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernaak.epicworkout.R;

import java.util.ArrayList;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<String> items = new ArrayList<>();

    public ExerciseRecyclerAdapter(Context context, ArrayList<String> mItems) {
        items = mItems;
    }

    @Override
    public ExerciseRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(parent.getContext()).inflate(R.layout.exercise_cards, parent, false);
        ExerciseRecyclerAdapter.MyViewHolder holder = new ExerciseRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ExerciseRecyclerAdapter.MyViewHolder holder, int position) {
        holder.name.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb;
        TextView name;
        TextView description;
        ImageView overflow;

        public MyViewHolder(View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.thumbnail);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
        }
    }
}