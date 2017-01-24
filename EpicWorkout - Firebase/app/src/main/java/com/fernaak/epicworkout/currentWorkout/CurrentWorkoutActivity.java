package com.fernaak.epicworkout.currentWorkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.fernaak.epicworkout.R;
import com.fernaak.epicworkout.exerciseItems.ExerciseObject;
import com.fernaak.epicworkout.workoutBuilder.WorkoutBuilderViewHolder;
import com.fernaak.epicworkout.workoutBuilder.WorkoutExerciseObject;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentWorkoutActivity  extends AppCompatActivity {

    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mExerciseImage;
    private List<String> exercisesList;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    public static final String ANONYMOUS = "anonymous";

    private String mUsername, mWorkoutName;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mExercisesDatabaseReference, mWorkoutsDatabaseReference;
    private FirebaseRecyclerAdapter<WorkoutExerciseObject, WorkoutBuilderViewHolder> mRecyclerViewAdapter;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_workout_main);

        Intent intent = getIntent();
        mWorkoutName = (String) intent.getSerializableExtra("Workout Name");

        setupFirebase();
        initializeScreen();
        attachRecyclerViewAdapter();
    }

    private void setupFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        //If you don't know the users name
        if (mUsername == null)
            getCurrentUser();
        mWorkoutsDatabaseReference = mFirebaseDatabase.getReference().child("workouts");
    }
    public void initializeScreen() {
        //Toolbars
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //Main image
        mExerciseImage = (ImageView) findViewById(R.id.exercise_image);
        mExerciseImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gym_pic));

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set up the toolbar and display buttons
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbar.setTitle(mWorkoutName);
    }
    private void getCurrentUser(){
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null)
            mUsername = user.getDisplayName();
        else
            mUsername = ANONYMOUS;
    }
    private void attachRecyclerViewAdapter(){
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<WorkoutExerciseObject, WorkoutBuilderViewHolder>
                (WorkoutExerciseObject.class, R.layout.list_item, WorkoutBuilderViewHolder.class, mWorkoutsDatabaseReference) {
            @Override
            protected void populateViewHolder(WorkoutBuilderViewHolder viewHolder, final WorkoutExerciseObject model, int position) {
                viewHolder.setName(model.getExerciseName());
                viewHolder.setSets(model.getSets());
                viewHolder.setReps(model.getReps());
                //viewHolder.setImage(model.getArea());


                /**
                 viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExercisePage.class);
                intent.putExtra("Exercise Object", model);
                v.getContext().startActivity(intent);
                }
                });
                 */
                //TODO: Launch the appropriate exercise page for the item clicked
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}