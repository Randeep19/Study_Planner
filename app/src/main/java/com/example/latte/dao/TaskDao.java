package com.example.latte.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.latte.model.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(TaskModel task);

    @Update
    void updateTask(TaskModel task);

    @Delete
    void deleteTask(TaskModel task);

    @Query("SELECT * FROM tasks ORDER BY priority ASC")
    LiveData<List<TaskModel>> getAllTasks();
}
