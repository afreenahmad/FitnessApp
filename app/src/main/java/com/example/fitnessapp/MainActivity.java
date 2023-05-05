package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import com.example.fitnessapp.ViewHolder.WorkoutListAdapter;
import com.example.fitnessapp.ViewHolder.WorkoutListHolder;
import com.example.fitnessapp.db.workoutDatabase;
import com.example.fitnessapp.db.WorkoutViewModel;
public class MainActivity extends AppCompatActivity {
    private int workout_id;
    private boolean filtered = false;
    private WorkoutViewModel WorkoutViewModel;
    private RecyclerView recyclerView;
    private WorkoutListAdapter adapter;
    private SharedPreferences prefs;
    private Toolbar toolbar;





    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));



        recyclerView = findViewById(R.id.recyclerView);
        adapter = new WorkoutListAdapter(this);

        recyclerView.setAdapter(adapter);
        //recyclerView.setBackgroundColor(Color.DKGRAY);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        WorkoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        WorkoutViewModel.getAllWorkouts().observe(this, adapter::setWorkouts);

        if (savedInstanceState != null) {
            filtered = savedInstanceState.getBoolean("filtered");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("filtered", filtered);
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String colorScheme = prefs.getString("colorScheme", "");
        int textColor;

        int color;

        if (colorScheme.equals("dark")) {
            color = Color.DKGRAY;

            textColor = Color.WHITE;
        } else {
            color = Color.WHITE;
            textColor = Color.BLACK;
        }
        toolbar.setBackgroundColor(color);

        // Set the text color of the toolbar
        toolbar.setTitleTextColor(textColor);




        recyclerView.setBackgroundColor(color);

        // Update the color scheme of the adapter
        adapter.updateColorScheme(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_options,menu);

        menu.getItem(2).setVisible(false);
        //menu.getItem(1).setTitle("My Workouts");

        // menu.getItem(1).setIcon(R.drawable.)

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.menu_save:
                startActivity(new Intent(this, AddActivity.class));
                return true;

            case R.id.added_workouts:
                filtered = !filtered;
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                WorkoutListAdapter adapter = new WorkoutListAdapter(this);
                recyclerView.setAdapter(adapter);
                WorkoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
                WorkoutViewModel.filterWorkouts(filtered);
                WorkoutViewModel.getAllWorkouts().observe(this, adapter::setWorkouts);
                if(item.getTitle().equals("My Workouts")){
                    item.setTitle("All Workouts");
                } else {
                    item.setTitle("My Workouts");
                }



                return true;

            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }

}