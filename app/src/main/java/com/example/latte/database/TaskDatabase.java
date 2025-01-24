package com.example.latte.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.latte.dao.TaskDao;
import com.example.latte.model.TaskModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase instance;

    public abstract TaskDao taskDao();

    // Create a static Executor for background tasks
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);  // You can change the thread pool size if needed

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()  // Helps when schema changes
                    .build();
        }
        return instance;
    }

    // Provide access to the Executor
    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }
}
