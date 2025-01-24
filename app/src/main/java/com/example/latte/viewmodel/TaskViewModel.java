package com.example.latte.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.latte.model.TaskModel;
import com.example.latte.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<List<TaskModel>> allTasks;

    public TaskViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application.getApplicationContext());
        allTasks = repository.getAllTasks();
    }

    // Getter for LiveData (tasks list)
    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;
    }

    // Insert a task
    public void insert(TaskModel task) {
        repository.insert(task);
    }

    // Update a task and log the update action
    public void update(TaskModel task) {
        Log.d("TaskViewModel", "Updating task: " + task.toString());  // Log task details for debugging
        repository.update(task);  // Update task in the repository
    }

    // Delete a task
    public void delete(TaskModel task) {
        repository.delete(task);
    }
}
