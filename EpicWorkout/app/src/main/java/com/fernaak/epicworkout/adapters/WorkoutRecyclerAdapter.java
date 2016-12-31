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
import com.fernaak.epicworkout.data.WorkoutContract.WorkoutEntry;
import com.fernaak.epicworkout.ui.workout_items.WorkoutPage;
import com.fernaak.epicworkout.ui.workout_items.WorkoutViewHolder;

public class WorkoutRecyclerAdapter extends CursorRecyclerViewAdapter {

    public WorkoutRecyclerAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.workouts_cards, parent, false);
        return new WorkoutViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final Cursor cursor) {
        WorkoutViewHolder holder = (WorkoutViewHolder) viewHolder;
        cursor.moveToPosition(cursor.getPosition());
        holder.setData(cursor);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkoutPage.class);
                Uri currentWorkoutUri = ContentUris.withAppendedId(WorkoutEntry.CONTENT_URI, cursor.getPosition());
                intent.setData(currentWorkoutUri);
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