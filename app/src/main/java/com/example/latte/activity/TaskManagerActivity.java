package com.example.latte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latte.R;
import com.example.latte.adapter.TaskAdapter;
import com.example.latte.adapter.MenuItemAdapter;
import com.example.latte.model.MenuItem;
import com.example.latte.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.GravityCompat;

public class TaskManagerActivity extends AppCompatActivity implements MenuItemAdapter.MenuItemClickListener {

    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private ListView listViewTasks;
    private RecyclerView menuRecyclerView;
    private DrawerLayout drawerLayout;
    private ImageButton btnOpenDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        // Initialize the DrawerLayout and the Open Drawer Button
        drawerLayout = findViewById(R.id.drawer_layout);
        btnOpenDrawer = findViewById(R.id.btn_open_drawer);

        // Open Drawer when the menu button is clicked
        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Create menu items
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Task Manager", R.drawable.taskmanager));
        menuItems.add(new MenuItem("Timetable", R.drawable.timetable));
        menuItems.add(new MenuItem("Click Notes", R.drawable.report));

        // Find the Menu RecyclerView
        menuRecyclerView = findViewById(R.id.menu_recycler_view);

        // Set the LayoutManager for the RecyclerView
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter for the menu items with the click listener
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems, this);
        menuRecyclerView.setAdapter(menuItemAdapter);

        // Find the Go Back button in your layout
        Button btnGoBack = findViewById(R.id.btn_go_back);
        btnGoBack.setOnClickListener(v -> {
            Intent intent = new Intent(TaskManagerActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Find the Add Task button in your layout
        Button btnAddTask = findViewById(R.id.btn_add_task);
        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskManagerActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        listViewTasks = findViewById(R.id.listViewTasks);

        // Initialize ViewModel and observe task list changes
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
            if (taskAdapter == null) {
                taskAdapter = new TaskAdapter(TaskManagerActivity.this, tasks);
                listViewTasks.setAdapter(taskAdapter);
            } else {
                taskAdapter.updateTaskList(tasks);
            }
        });
    }

    // Menu item click handler
    @Override
    public void onMenuItemClick(MenuItem menuItem) {
        // Handle menu item click, e.g., start an activity or show a fragment
        if (menuItem.getTitle().equals("Task Manager")) {
            // Navigate to the Task Manager activity (this will restart the activity)
            Intent intent = new Intent(TaskManagerActivity.this, TaskManagerActivity.class);
            startActivity(intent);
            finish();  // Close current activity to avoid duplicate Task Manager activity
        } else if (menuItem.getTitle().equals("Timetable")) {
            // Navigate to Timetable activity
            Intent intent = new Intent(TaskManagerActivity.this, TimetableActivity.class);
            startActivity(intent);
            finish();  // Close current activity
        } else if (menuItem.getTitle().equals("Report")) {
            // Navigate to Report activity
            Intent intent = new Intent(TaskManagerActivity.this, ReportActivity.class);
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
