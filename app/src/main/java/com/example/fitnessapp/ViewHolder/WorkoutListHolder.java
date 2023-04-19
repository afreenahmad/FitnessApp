package com.example.fitnessapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.db.workout;


public class WorkoutListHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView muscleGroup;
    public TextView description;
    public final ImageView isAdded;
    public CardView cardView;
    public workout workouts;

    public WorkoutListHolder( View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.NameTextView);
        muscleGroup = itemView.findViewById(R.id.muscleGroupTextView);
        description = itemView.findViewById(R.id.descriptionTextView);
        isAdded = itemView.findViewById(R.id.isCompletedImageView);
        cardView = itemView.findViewById(R.id.cardView);
    }

}

