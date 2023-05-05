package com.example.fitnessapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.db.workout;
import com.example.fitnessapp.db.workoutDatabase;


public class AddActivity extends AppCompatActivity {
    private int workout_id;
    private boolean added;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_workout);
        setSupportActionBar(findViewById(R.id.toolbar));
        ConstraintLayout constraintLayout = findViewById(R.id.my_constraint_layout);

        Spinner spinner = findViewById(R.id.muscleGroupSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.muscleGroup_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String colorScheme = prefs.getString("colorScheme", "");
        if (colorScheme.equals("dark")) {
            constraintLayout.setBackgroundColor(Color.DKGRAY);
            TextView workoutNameTextView = findViewById(R.id.workoutNameTextView);
            TextView descripTextView = findViewById(R.id.descripTextView);
            TextView MuscleGroupTextView = findViewById(R.id.MuscleGroupTextView);
            workoutNameTextView.setTextColor(Color.WHITE);
            descripTextView.setTextColor(Color.WHITE);
            MuscleGroupTextView.setTextColor(Color.WHITE);
        } else {
            constraintLayout.setBackgroundColor(Color.WHITE);
        }

        workout_id = getIntent().getIntExtra("workout_id", -1);

        // Note: that we do not want to lose the state if the activity is being
        // recreated
        if (savedInstanceState == null) {
            if (workout_id != -1) {
                workoutDatabase.getWorkout(workout_id, workout -> {
                    ((EditText) findViewById(R.id.workoutNameEditText)).setText(workout.name);
                    //((EditText) findViewById(R.id.txtEditSetup)).setText(workout.muscleGroup);
                    ((EditText) findViewById(R.id.descripEditText)).setText(workout.description);
                    added = workout.isAdded;
                });
            }
        }
        else {
            added = savedInstanceState.getBoolean("added");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        menu.getItem(2).setTitle("Cancel");

        menu.getItem(2).setVisible(true);
        //setTitle("Add Workout");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                updateDatabase();
                return true;

            case R.id.cancel_adding:
                // finish the AddActivity
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDatabase() {

        Spinner priority = findViewById(R.id.muscleGroupSpinner);
        String contentOfMuscleGroup = priority.getSelectedItem().toString();


        workout workout = new workout(workout_id == -1?0: workout_id,
                ((EditText) findViewById(R.id.workoutNameEditText)).getText().toString(),
                ((EditText) findViewById(R.id.descripEditText)).getText().toString(),
                //((EditText) findViewById(R.id.txtEditPunchline)).getText().toString(),
                contentOfMuscleGroup,
                added);
        if (workout_id == -1) {
            workoutDatabase.insert(workout);
        } else {
            workoutDatabase.update(workout);
        }
        finish(); // Quit activity

    }

    public void deleteRecord() {
        workoutDatabase.delete(workout_id);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("added", added);
    }



}