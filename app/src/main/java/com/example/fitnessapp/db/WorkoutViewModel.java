package com.example.fitnessapp.db;

import android.app.Application;

import java.util.List;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WorkoutViewModel extends AndroidViewModel{
    private LiveData<List<workout>> workouts;

    public WorkoutViewModel (Application application) {
        super(application);
        workouts = workoutDatabase.getDatabase(getApplication()).workoutDAO().getAll();
    }

    public void filterWorkouts(boolean onlyAdded) {
        if (onlyAdded)
            workouts = workoutDatabase.getDatabase(getApplication()).workoutDAO().getAdded(true);
        else
            workouts = workoutDatabase.getDatabase(getApplication()).workoutDAO().getAll();
    }

    public LiveData<List<workout>> getAllWorkouts() {
        return workouts;
    }
}
