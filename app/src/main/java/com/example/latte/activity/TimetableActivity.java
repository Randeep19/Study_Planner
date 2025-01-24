package com.example.latte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latte.R;
import com.example.latte.adapter.MenuItemAdapter;
import com.example.latte.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.GravityCompat;

public class TimetableActivity extends AppCompatActivity implements MenuItemAdapter.MenuItemClickListener {

    private DrawerLayout drawerLayout;
    private ImageButton btnOpenDrawer;
    private RecyclerView menuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);  // Your layout for Timetable Activity

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

        // CalendarView functionality
        CalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(TimetableActivity.this, "Date Pinned: " + selectedDate, Toast.LENGTH_SHORT).show();
        });

        // Go Back Button functionality
        Button goBackButton = findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(v -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(TimetableActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close this activity to prevent user from coming back to it via the back button
        });
    }

    // Menu item click handler
    @Override
    public void onMenuItemClick(MenuItem menuItem) {
        // Handle menu item click for navigation
        if (menuItem.getTitle().equals("Task Manager")) {
            // Navigate to TaskManagerActivity
            Intent intent = new Intent(TimetableActivity.this, TaskManagerActivity.class);
            startActivity(intent);
            finish();  // Close current activity to avoid duplicate Task Manager activity
        } else if (menuItem.getTitle().equals("Timetable")) {
            // Navigate to Timetable activity (this will restart the activity)
            Intent intent = new Intent(TimetableActivity.this, TimetableActivity.class);
            startActivity(intent);
            finish();  // Close current activity
        } else if (menuItem.getTitle().equals("Report")) {
            // Navigate to Report activity
            Intent intent = new Intent(TimetableActivity.this, ReportActivity.class);
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
