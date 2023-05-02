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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fitnessapp.ViewHolder.WorkoutListAdapter;
import com.example.fitnessapp.ViewHolder.WorkoutListHolder;
import com.example.fitnessapp.db.workoutDatabase;
import com.example.fitnessapp.db.WorkoutViewModel;
public class MainActivity extends AppCompatActivity {
    private int workout_id;
    private boolean filtered = false;
    private WorkoutViewModel WorkoutViewModel;
    private String mCardScheme;

    private WorkoutListAdapter adapter;
    private RecyclerView recyclerView;




    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mCardScheme = sharedPref.getString("cardScheme","");


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
       // WorkoutListAdapter adapter = new WorkoutListAdapter(this);
        adapter = new WorkoutListAdapter(this);
        recyclerView.setAdapter(adapter);
        //adapter.setLightOrDark(mCardScheme);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        WorkoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        WorkoutViewModel.getAllWorkouts().observe(this, adapter::setWorkouts);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_options,menu);
        // menu.getItem(1).setIcon(R.drawable.)

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save:
                startActivity(new Intent(this, AddActivity.class));
                return true;

            case R.id.added_workouts:
                filtered = !filtered;



                //RecyclerView recyclerView = findViewById(R.id.recyclerView);
               // WorkoutListAdapter adapter = new WorkoutListAdapter(this);
                //recyclerView.setAdapter(adapter);
                //WorkoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
                WorkoutViewModel.filterWorkouts(filtered);
               // WorkoutViewModel.getAllWorkouts().observe(this, adapter::setWorkouts);

            default:
                return super.onOptionsItemSelected(item);
        }




    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the value of the boolean from shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int color = (prefs.getString("colorScheme", "").equals("light")? Color.WHITE:Color.DKGRAY);

        String cardScheme = null;
        if(prefs.getString("cardScheme","").equals("Light")){
            cardScheme = "Light";
        }else if(prefs.getString("cardScheme","").equals("Dark")){
            cardScheme = "Dark";
        }else{
            cardScheme = "Inverted";

        }



        recyclerView.setBackgroundColor(color);

        // If the value has changed, update the adapter
        //if(!cardScheme.equals(mCardScheme)){
            mCardScheme = cardScheme;
            adapter.setLightOrDark(cardScheme);
            adapter.notifyDataSetChanged();
       // }

    }
}