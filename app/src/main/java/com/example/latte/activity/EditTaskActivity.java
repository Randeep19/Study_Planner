package com.example.latte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latte.R;
import com.example.latte.adapter.MenuItemAdapter;
import com.example.latte.model.MenuItem;
import com.example.latte.model.TaskModel;
import com.example.latte.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.GravityCompat;

public class EditTaskActivity extends AppCompatActivity implements MenuItemAdapter.MenuItemClickListener {

    private EditText etTaskTitle, etTaskDescription;
    private Button btnSaveTask, btnGoBack;
    private TaskViewModel taskViewModel;
    private DrawerLayout drawerLayout;
    private ImageButton btnOpenDrawer;
    private RecyclerView menuRecyclerView;

    private int taskId; // Task ID to identify the task being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Initialize Views
        etTaskTitle = findViewById(R.id.et_task_title);
        etTaskDescription = findViewById(R.id.et_task_description);
        btnSaveTask = findViewById(R.id.btn_save_task);
        btnGoBack = findViewById(R.id.btn_go_back);

        // Initialize ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Initialize DrawerLayout and Menu Button
        drawerLayout = findViewById(R.id.drawer_layout);
        btnOpenDrawer = findViewById(R.id.btn_open_drawer);

        // Open Drawer when the menu button is clicked
        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Set up the menu items for the drawer
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Task Manager", R.drawable.taskmanager));
        menuItems.add(new MenuItem("Timetable", R.drawable.timetable));
        menuItems.add(new MenuItem("Report", R.drawable.report));

        // RecyclerView for the navigation menu
        menuRecyclerView = findViewById(R.id.menu_recycler_view);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems, this);
        menuRecyclerView.setAdapter(menuItemAdapter);

        // Get task data passed from TaskManagerActivity or TaskAdapter
        taskId = getIntent().getIntExtra("taskId", -1);
        String taskTitle = getIntent().getStringExtra("taskTitle");
        String taskDescription = getIntent().getStringExtra("taskDescription");

        // Pre-fill EditTexts with task data
        etTaskTitle.setText(taskTitle);
        etTaskDescription.setText(taskDescription);

        // Save updated task on button click
        btnSaveTask.setOnClickListener(v -> {
            String updatedTitle = etTaskTitle.getText().toString();
            String updatedDescription = etTaskDescription.getText().toString();

            if (!updatedTitle.isEmpty() && !updatedDescription.isEmpty()) {
                // Create the updated TaskModel object
                TaskModel updatedTask = new TaskModel(updatedTitle, updatedDescription, "2025-01-01", 1, false, taskId);
                taskViewModel.update(updatedTask);  // Call the ViewModel to update the task

                // Show a confirmation toast
                Toast.makeText(EditTaskActivity.this, "Task updated successfully", Toast.LENGTH_SHORT).show();

                // Navigate back to TaskManagerActivity and ensure the task list is refreshed
                Intent intent = new Intent(EditTaskActivity.this, TaskManagerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Clear previous activities to avoid duplicates
                startActivity(intent);  // Start TaskManagerActivity
                finish();  // Finish EditTaskActivity
            } else {
                // Show error if fields are empty
                Toast.makeText(EditTaskActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Set the Go Back functionality to return to TaskManagerActivity or AddTaskActivity
        btnGoBack.setOnClickListener(v -> {
            // Navigate back to TaskManagerActivity
            Intent intent = new Intent(EditTaskActivity.this, TaskManagerActivity.class);
            startActivity(intent);  // Start TaskManagerActivity
            finish();  // Optionally finish EditTaskActivity (optional)
        });
    }

    // Menu item click handler
    @Override
    public void onMenuItemClick(MenuItem menuItem) {
        // Handle menu item click for navigation
        if (menuItem.getTitle().equals("Task Manager")) {
            // Navigate to TaskManagerActivity
            Intent intent = new Intent(EditTaskActivity.this, TaskManagerActivity.class);
            startActivity(intent);
            finish();  // Close current activity to avoid duplicate Task Manager activity
        } else if (menuItem.getTitle().equals("Timetable")) {
            // Navigate to Timetable activity
            Intent intent = new Intent(EditTaskActivity.this, TimetableActivity.class);
            startActivity(intent);
            finish();  // Close current activity
        } else if (menuItem.getTitle().equals("Report")) {
            // Navigate to Report activity
            Intent intent = new Intent(EditTaskActivity.this, ReportActivity.class);
            startActivity(intent);
            finish();  // Close current activity
        }
    }

    @Override
    public void onBackPressed() {
        // If the drawer is open, close it; otherwise, do the default behavior
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
