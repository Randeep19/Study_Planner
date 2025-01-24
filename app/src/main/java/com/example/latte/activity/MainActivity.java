package com.example.latte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.latte.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTaskManager = findViewById(R.id.btn_task_manager);
        Button btnTimetable = findViewById(R.id.btn_timetable);
        Button btnReports = findViewById(R.id.btn_reports);
        Button btnExit = findViewById(R.id.btn_exit);

        // Navigate to Task Manager
        btnTaskManager.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, TaskManagerActivity.class))
        );

        // Navigate to Timetable
        btnTimetable.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, TimetableActivity.class))
        );

        // Navigate to Reports
        btnReports.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ReportActivity.class))
        );

        // Exit the app
        btnExit.setOnClickListener(view -> finish());
    }
}
