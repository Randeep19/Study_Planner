package com.example.latte.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.latte.R;
import com.example.latte.activity.EditTaskActivity;
import com.example.latte.model.TaskModel;
import com.example.latte.repository.TaskRepository;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<TaskModel> taskList;
    private TaskRepository taskRepository;

    // Constructor to initialize context and task list
    public TaskAdapter(Context context, List<TaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.taskRepository = new TaskRepository(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return taskList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_item, parent, false);
        }

        // Get references to the views in the layout
        TextView taskTitle = convertView.findViewById(R.id.taskTitle);
        TextView taskDescription = convertView.findViewById(R.id.taskDescription);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        // Get the task data for the current position
        TaskModel task = taskList.get(position);

        // Set data to the views
        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());

        // Edit button logic
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTaskActivity.class);
            // Pass task details to EditTaskActivity
            intent.putExtra("taskId", task.getId());
            intent.putExtra("taskTitle", task.getTitle());
            intent.putExtra("taskDescription", task.getDescription());
            context.startActivity(intent);
        });

        // Delete button logic
        deleteButton.setOnClickListener(v -> {
            // Delete the task from the repository and the list
            taskRepository.delete(task);  // Delete task from the database
            taskList.remove(position);    // Remove from the local list
            notifyDataSetChanged();      // Notify the adapter that the data has changed
        });

        return convertView;
    }

    // Update the task list and notify the adapter about data changes
    public void updateTaskList(List<TaskModel> newTaskList) {
        this.taskList = newTaskList;
        notifyDataSetChanged();
    }
}
