package com.example.fitnessapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface workoutDAO {
    @Query("SELECT * FROM workouts WHERE isAdded = :onlyAdded " +
            "ORDER BY name COLLATE NOCASE, rowid")
    LiveData<List<workout>> getAdded(boolean onlyAdded);

    @Query("SELECT * FROM workouts ORDER BY name COLLATE NOCASE, rowid")
    LiveData<List<workout>> getAll();

    @Query("SELECT * FROM workouts WHERE rowid = :workout_id")
    LiveData<workout> getById(int workout_id);

    @Insert
    void insert(workout... workouts);

    @Update
    void update(workout... workouts);

    @Delete
    void delete(workout...user);

    @Query("DELETE FROM workouts WHERE rowid = :workout_id")
    void delete(int workout_id);
}
