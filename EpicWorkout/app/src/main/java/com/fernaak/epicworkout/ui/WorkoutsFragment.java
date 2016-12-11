package com.fernaak.epicworkout.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fernaak.epicworkout.R;


public class WorkoutsFragment extends Fragment {
        private ListView mWorkoutsListView;

        public static WorkoutsFragment newInstance(){
            WorkoutsFragment frag = new WorkoutsFragment();
            Bundle args = new Bundle();
            frag.setArguments(args);
            return frag;
        }
        public WorkoutsFragment(){}//Required empty constructor

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
            View rootView = inflater.inflate(R.layout.fragment_workout, container, false);

            initializeScreen(rootView);

            mWorkoutsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
        private void initializeScreen(View rootView){
            mWorkoutsListView = (ListView) rootView.findViewById(R.id.list_view_workouts_list);
        }

    }
