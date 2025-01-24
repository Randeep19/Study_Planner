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

    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;
    }

    public void insert(TaskModel task) {
        repository.insert(task);
    }

    public void update(TaskModel task) {
        Log.d("TaskViewModel", "Updating task: " + task.toString());  // Log task details
        repository.update(task);  // Corrected: Use instance of repository to update the task
    }

    public void delete(TaskModel task) {
        repository.delete(task);
    }
}
