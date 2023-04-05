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
    @Query("SELECT * FROM workouts WHERE liked = :onlyLiked " +
            "ORDER BY name COLLATE NOCASE, rowid")
    LiveData<List<workout>> getLiked(boolean onlyLiked);

    @Query("SELECT * FROM workouts ORDER BY name COLLATE NOCASE, rowid")
    LiveData<List<workout>> getAll();

    @Query("SELECT * FROM workouts WHERE rowid = :id")
    LiveData<workout> getById(int id);

    @Insert
    void insert(workout... workouts);

    @Update
    void update(workout... workouts);

    @Delete
    void delete(workout... workouts);

    @Query("DELETE FROM workouts WHERE rowid = :id")
    void deleteById(int id);
}
