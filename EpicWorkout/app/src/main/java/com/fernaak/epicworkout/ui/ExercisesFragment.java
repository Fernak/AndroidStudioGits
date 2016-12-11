package com.fernaak.epicworkout.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fernaak.epicworkout.R;
import com.fernaak.epicworkout.data.EpicWorkoutDBHelper;
import com.fernaak.epicworkout.data.ExerciseContract;

import static com.fernaak.epicworkout.data.ExerciseContract.ExerciseEntry;

public class ExercisesFragment extends Fragment {
    private ListView mExerciseListView;
    private TextView mExerciseTextView;
    private EpicWorkoutDBHelper mDbHelper;

    public static ExercisesFragment newInstance(){
        ExercisesFragment frag = new ExercisesFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }
    public ExercisesFragment(){}//Required empty constructor

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_exercises, container, false);

        initialzeScreen(rootView);
        mDbHelper = new EpicWorkoutDBHelper(getContext());

        mExerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){}
        });
        return rootView;
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    private void initialzeScreen(View rootView){
        mExerciseListView = (ListView) rootView.findViewById(R.id.list_view_exercise_list);
        //mExerciseTextView = (TextView) rootView.findViewById(R.id.text_view_exercise);
    }
}
