package com.example.latte.repository;

import android.content.Context;
import android.util.Log;

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
    public void insert(TaskModel task) {
        // Use executor to avoid blocking the main thread
        TaskDatabase.getDatabaseWriteExecutor().execute(() -> taskDao.insertTask(task));
    }

    // Update an existing task in the database in a background thread
    public void update(TaskModel task) {
        Log.d("TaskRepository", "Updating task in repository: " + task.toString());  // Log task being updated
        TaskDatabase.getDatabaseWriteExecutor().execute(() -> taskDao.updateTask(task)); // Update task in the background
    }


    // Delete a task from the database in a background thread
    public void delete(TaskModel task) {
        // Use executor to avoid blocking the main thread
        TaskDatabase.getDatabaseWriteExecutor().execute(() -> taskDao.deleteTask(task));
    }
}
