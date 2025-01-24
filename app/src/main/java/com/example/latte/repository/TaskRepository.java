package com.example.latte.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.latte.dao.TaskDao;
import com.example.latte.database.TaskDatabase;
import com.example.latte.model.TaskModel;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<TaskModel>> allTasks;

    public TaskRepository(Context context) {
        // Initialize the database and DAO
        TaskDatabase database = TaskDatabase.getInstance(context);
        taskDao = database.taskDao();  // Get TaskDao instance
        allTasks = taskDao.getAllTasks();  // Get all tasks as LiveData
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;  // Return LiveData that will be observed in the ViewModel
    }

    // Insert a task into the database in a background thread
    public void insert(@NonNull TaskModel task) {
        TaskDatabase.getDatabaseWriteExecutor().execute(() -> {
            try {
                taskDao.insertTask(task);
                Log.d("TaskRepository", "Task inserted successfully");
            } catch (Exception e) {
                Log.e("TaskRepository", "Error inserting task", e);
            }
        });
    }

    // Update an existing task in the database in a background thread
    public void update(@NonNull TaskModel task) {
        Log.d("TaskRepository", "Updating task in repository: " + task.toString());
        TaskDatabase.getDatabaseWriteExecutor().execute(() -> {
            try {
                taskDao.updateTask(task);
                Log.d("TaskRepository", "Task updated successfully");
            } catch (Exception e) {
                Log.e("TaskRepository", "Error updating task", e);
            }
        });
    }

    // Delete a task from the database in a background thread
    public void delete(@NonNull TaskModel task) {
        TaskDatabase.getDatabaseWriteExecutor().execute(() -> {
            try {
                taskDao.deleteTask(task);
                Log.d("TaskRepository", "Task deleted successfully");
            } catch (Exception e) {
                Log.e("TaskRepository", "Error deleting task", e);
            }
        });
    }
}
