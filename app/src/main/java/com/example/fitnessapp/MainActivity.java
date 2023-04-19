package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.fitnessapp.ViewHolder.WorkoutListAdapter;
import com.example.fitnessapp.db.workoutDatabase;
import com.example.fitnessapp.db.WorkoutViewModel;
public class MainActivity extends AppCompatActivity {
    private int workout_id;
    private WorkoutViewModel WorkoutViewModel;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        WorkoutListAdapter adapter = new WorkoutListAdapter(this);
        recyclerView.setAdapter(adapter);

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
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}