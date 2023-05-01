package com.example.fitnessapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="Workouts")
public class workout {

    // Note that if id == 0, and this is inserted, an id will be autogenerated
    public workout(int id, String name, String muscleGroup, String description, boolean isAdded) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscleGroup = muscleGroup;
        this.isAdded = isAdded;

    }

    // Assign 0 to id to have it be auto generated
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

//    @ColumnInfo(name = "category")
//    public String category;


    @ColumnInfo(name = "muscleGroup")
    public String muscleGroup;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "isAdded")
    public boolean isAdded;


}
