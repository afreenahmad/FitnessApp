package com.example.fitnessapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.db.workout;
import com.example.fitnessapp.db.workoutDatabase;


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

        isAdded.setOnClickListener(view -> {
            //Toast.makeText(itemView.getContext(), "you added a workout to your routine!", Toast.LENGTH_SHORT).show();
            if (workouts.isAdded) {
                Toast.makeText(itemView.getContext(), "you unadded the workout!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(itemView.getContext(), "you added a workout to your routine!", Toast.LENGTH_SHORT).show();
            }
            workouts.isAdded = !workouts.isAdded;
            workoutDatabase.update(workouts);
        });
    }

}

