package com.example.fitnessapp.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.db.workout;


import java.util.List;

public class WorkoutListAdapter extends RecyclerView.Adapter <WorkoutListHolder> {
    public final LayoutInflater layoutInflater;

    private List<workout> workouts; // Cached copy of jokes
    private Context context;
    private boolean isDarkMode = false;

    public WorkoutListAdapter(Context context){

        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String colorScheme = sharedPreferences.getString("colorScheme", "");
        if (colorScheme.equals("dark")) {
            isDarkMode = true;
        }
    }

    @NonNull
    @Override
    public WorkoutListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new WorkoutListHolder(itemView);
    }

    @Override
    public void onBindViewHolder( WorkoutListHolder holder, int position) {
        if(workouts != null){
            workout current = workouts.get(position);
            holder.workouts = current;
            holder.nameView.setText(current.name);
            holder.muscleGroup.setText(current.muscleGroup);
            holder.description.setText(current.description);

            Log.d("isDarkMode", String.valueOf(isDarkMode));

            // Set the card color based on the current color scheme
            if (isDarkMode) {
                holder.cardView.setCardBackgroundColor(Color.DKGRAY);
                holder.nameView.setTextColor(Color.WHITE);
                holder.muscleGroup.setTextColor(Color.WHITE);
                holder.description.setTextColor(Color.WHITE);
            } else {
                holder.cardView.setCardBackgroundColor(Color.WHITE);
                holder.nameView.setTextColor(Color.BLACK);
                holder.muscleGroup.setTextColor(Color.BLACK);
                holder.description.setTextColor(Color.BLACK);
            }




            if(current.isAdded)
                holder.isAdded.setImageResource(R.drawable.baseline_check_circle_24);
            else
                holder.isAdded.setImageResource(R.drawable.baseline_cancel_24);
        }else{
            holder.nameView.setText(R.string.init);
            holder.isAdded.setImageResource(R.drawable.baseline_cancel_24);
            holder.muscleGroup.setText(R.string.init);
            holder.description.setText(R.string.init);
        }


    }

    @Override
    public int getItemCount() {
        if (workouts != null)
            return workouts.size();
        else return 0;
    }
    public void setWorkouts(List<workout> workouts){
        this.workouts = workouts;
        notifyDataSetChanged();
    }
    public void updateColorScheme(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String colorScheme = prefs.getString("colorScheme", "");
        boolean isDarkModeEnabled = colorScheme.equals("dark");

        int textColor = isDarkModeEnabled ? Color.WHITE : Color.BLACK;
        int backgroundColor = isDarkModeEnabled ? Color.DKGRAY : Color.WHITE;

        isDarkMode = isDarkModeEnabled;

        // Notify the adapter that the data has changed
        notifyDataSetChanged();
    }
}
