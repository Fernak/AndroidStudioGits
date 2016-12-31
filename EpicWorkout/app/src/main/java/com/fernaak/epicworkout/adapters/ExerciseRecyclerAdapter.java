package com.fernaak.epicworkout.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernaak.epicworkout.R;
import com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;
import com.fernaak.epicworkout.ui.exercise_items.ExercisePage;
import com.fernaak.epicworkout.ui.exercise_items.ExerciseViewHolder;

public class ExerciseRecyclerAdapter extends CursorRecyclerViewAdapter {

    public ExerciseRecyclerAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public long getItemId(int position){
       return super.getItemId(position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.exercise_cards, parent, false);

        return new ExerciseViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final Cursor cursor) {
        ExerciseViewHolder holder = (ExerciseViewHolder) viewHolder;
        cursor.moveToPosition(cursor.getPosition());
        holder.setData(cursor);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExercisePage.class);
                Uri currentExerciseUri = ContentUris.withAppendedId(ExerciseEntry.CONTENT_URI, cursor.getPosition());
                intent.setData(currentExerciseUri);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}