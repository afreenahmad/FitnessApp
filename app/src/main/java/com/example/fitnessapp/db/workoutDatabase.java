package com.example.fitnessapp.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {workout.class}, version = 1, exportSchema = false)
public abstract class workoutDatabase extends RoomDatabase{
    public interface workoutListener {
        void onWorkoutReturned(workout workouts);
    }

    public abstract workoutDAO workoutDAO();

    private static workoutDatabase INSTANCE;

    public static workoutDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (workoutDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    workoutDatabase.class, "workout_database")
                            .addCallback(createWorkoutDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createWorkoutDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            createWorkoutTable();
        }
    };
    private static void createWorkoutTable(){
        for(int i=0; i < DefaultContent.NAME.length;i++){
            insert(new workout(0, DefaultContent.NAME[i], DefaultContent.MUSCLEGROUP[i]));

        }
    }
    public static void getJoke(int id, workoutListener listener) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listener.onWorkoutReturned((workout) msg.obj);
            }
        };

        (new Thread(() -> {
            Message msg = handler.obtainMessage();
            msg.obj = INSTANCE.workoutDAO().getById(id);
            handler.sendMessage(msg);
        })).start();
    }

    private static void insert(workout workout) {
        (new Thread(()-> INSTANCE.workoutDAO().insert(workout))).start();

    }
    private static void delete(workout workout) {
        (new Thread(()-> INSTANCE.workoutDAO().delete(workout))).start();

    }
    private static void update(workout workout) {
        (new Thread(()-> INSTANCE.workoutDAO().update(workout))).start();

    }


}
