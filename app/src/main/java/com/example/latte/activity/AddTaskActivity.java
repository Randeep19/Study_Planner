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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.core.view.GravityCompat;

public class AddTaskActivity extends AppCompatActivity implements MenuItemAdapter.MenuItemClickListener {

    private EditText etTaskTitle, etTaskDescription;
    private Button btnSaveTask;
    private TaskViewModel taskViewModel;
    private DrawerLayout drawerLayout;
    private ImageButton btnOpenDrawer;
    private RecyclerView menuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize Views
        etTaskTitle = findViewById(R.id.et_task_title);
        etTaskDescription = findViewById(R.id.et_task_description);
        btnSaveTask = findViewById(R.id.btn_save_task);

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

        // Save Task on Button Click
        btnSaveTask.setOnClickListener(v -> {
            String taskTitle = etTaskTitle.getText().toString();
            String taskDescription = etTaskDescription.getText().toString();

            if (!taskTitle.isEmpty() && !taskDescription.isEmpty()) {
                // Get current date
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Create a new TaskModel object
                TaskModel newTask = new TaskModel(taskTitle, taskDescription, currentDate, 1, false, 0);

                // Insert the task into the database via the ViewModel
                taskViewModel.insert(newTask);

                // Display success message
                Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();

                // Close AddTaskActivity
                finish();  // Optionally, you can navigate back to TaskManagerActivity or other logic
            } else {
                // Show a warning if fields are empty
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Menu item click handler
    @Override
    public void onMenuItemClick(MenuItem menuItem) {
        // Handle menu item click for navigation
        if (menuItem.getTitle().equals("Task Manager")) {
            // Navigate to TaskManagerActivity
            Intent intent = new Intent(AddTaskActivity.this, TaskManagerActivity.class);
            startActivity(intent);
            finish();  // Close current activity to avoid duplicate Task Manager activity
        } else if (menuItem.getTitle().equals("Timetable")) {
            // Navigate to Timetable activity
            Intent intent = new Intent(AddTaskActivity.this, TimetableActivity.class);
            startActivity(intent);
            finish();  // Close current activity
        } else if (menuItem.getTitle().equals("Report")) {
            // Navigate to Report activity
            Intent intent = new Intent(AddTaskActivity.this, ReportActivity.class);
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
