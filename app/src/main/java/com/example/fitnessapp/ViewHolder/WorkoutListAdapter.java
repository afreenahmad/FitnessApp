package com.example.fitnessapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
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
import java.util.Objects;

public class WorkoutListAdapter extends RecyclerView.Adapter <WorkoutListHolder> {
    public final LayoutInflater layoutInflater;
    private Context mContext;

    private List<workout> workouts; // Cached copy of jokes
    private int colorResId;
    private String mCardScheme;

    public WorkoutListAdapter(Context context){
        mContext = context;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WorkoutListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new WorkoutListHolder(itemView);
    }
    public void setLightOrDark(String cardScheme){
        mCardScheme = cardScheme;}

    @Override
    public void onBindViewHolder( WorkoutListHolder holder, int position) {
        if(workouts != null){
            workout current = workouts.get(position);
            holder.workouts = current;
            holder.nameView.setText(current.name);
            holder.muscleGroup.setText(current.muscleGroup);
            holder.description.setText(current.description);

            if (Objects.equals(mCardScheme, "Light")) {
                holder.nameView.setTextColor(Color.DKGRAY );
                holder.muscleGroup.setTextColor(Color.DKGRAY );
                holder.description.setTextColor(Color.DKGRAY);
            } else if (Objects.equals(mCardScheme, "Dark")) {
                holder.nameView.setTextColor(Color.LTGRAY);
                holder.muscleGroup.setTextColor(Color.LTGRAY);
                holder.description.setTextColor(Color.LTGRAY);
            }

            colorResId = Objects.equals(mCardScheme, "Light") ? "Biceps".equals(current.muscleGroup)
                    ? R.color.colorLP
                    : "Triceps".equals(current.muscleGroup) ? R.color.colorMP
                    : "Glutes".equals(current.muscleGroup) ? R.color.colorHP : R.color.colorDefault
                    : Objects.equals(mCardScheme, "Dark")
                    ? "Triceps".equals(current.muscleGroup) ? R.color.colorLPDark
                    : "Biceps".equals(current.muscleGroup) ? R.color.colorMPDark
                    : "Glutes".equals(current.muscleGroup) ? R.color.colorHPDark : R.color.colorDefault
                    : "Biceps".equals(current.muscleGroup) ? R.color.colorLPDarkInverted
                    : "Triceps".equals(current.muscleGroup) ? R.color.colorMPDarkInverted
                    : "Glutes".equals(current.muscleGroup) ? R.color.colorHPDarkInverted : R.color.colorDefault;


            int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
            holder.cardView.setCardBackgroundColor(color);




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
}
