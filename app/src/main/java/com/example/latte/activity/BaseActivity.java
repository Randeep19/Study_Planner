package com.example.latte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.latte.R;
import com.example.latte.adapter.MenuItemAdapter;
import com.example.latte.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements MenuItemAdapter.MenuItemClickListener {

    private DrawerLayout drawerLayout;
    private ImageButton btnOpenDrawer;
    private RecyclerView menuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base); // Using the base layout

        // Initialize the DrawerLayout and the Open Drawer Button
        drawerLayout = findViewById(R.id.drawer_layout);
        btnOpenDrawer = findViewById(R.id.btn_open_drawer);
        menuRecyclerView = findViewById(R.id.menu_recycler_view);

        // Open Drawer when the menu button is clicked
        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Create menu items
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Task Manager", R.drawable.taskmanager));
        menuItems.add(new MenuItem("Timetable", R.drawable.timetable));
        menuItems.add(new MenuItem("Report", R.drawable.report));

        // Set up the RecyclerView for menu items
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems, this);
        menuRecyclerView.setAdapter(menuItemAdapter);
    }

    @Override
    public void onMenuItemClick(MenuItem menuItem) {
        // Handle menu item clicks (add logic for your pages)
        if (menuItem.getTitle().equals("Task Manager")) {
            Intent intent = new Intent(BaseActivity.this, TaskManagerActivity.class);
            startActivity(intent);
        } else if (menuItem.getTitle().equals("Timetable")) {
            Intent intent = new Intent(BaseActivity.this, TimetableActivity.class);
            startActivity(intent);
        } else if (menuItem.getTitle().equals("Report")) {
            Intent intent = new Intent(BaseActivity.this, ReportActivity.class);
            startActivity(intent);
        }

        // Close the drawer after navigation
        drawerLayout.closeDrawer(GravityCompat.START);
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
