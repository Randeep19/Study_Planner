package com.example.latte.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.example.latte.R;
import com.example.latte.database.NotesDatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.Manifest;
import android.content.pm.PackageManager;

public class ReportActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    private ImageView capturedImageView;
    private Button btnCaptureNote;
    private Button btnGoBack;
    private DrawerLayout drawerLayout;
    private NotesDatabaseHelper databaseHelper;

    private int noteId; // Variable to store the noteId generated after inserting into the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        capturedImageView = findViewById(R.id.captured_image_view);
        btnCaptureNote = findViewById(R.id.btn_capture_note);
        btnGoBack = findViewById(R.id.btnGoBack);

        // Initialize SQLite database helper
        databaseHelper = new NotesDatabaseHelper(this);

        // Open Drawer when the menu button is clicked
        findViewById(R.id.btn_open_drawer).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Capture Note Button Click Listener
        btnCaptureNote.setOnClickListener(v -> checkCameraPermission());

        // Go Back Button Click Listener
        btnGoBack.setOnClickListener(v -> {
            Intent intent = new Intent(ReportActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Check if the app has camera permission
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            // If permission is granted, launch the camera
            dispatchTakePictureIntent();
        }
    }

    // Handle the result of permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Always call the super method first to ensure proper system handling
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch the camera
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }



    // Launch the camera app to capture the image
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Handle the result from the camera capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                // Get the image from the intent
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                // Set the captured image to the ImageView
                capturedImageView.setImageBitmap(imageBitmap);
                capturedImageView.setVisibility(View.VISIBLE); // Make ImageView visible

                // Save image locally and store the path in the database
                saveImage(imageBitmap);
            }
        }
    }

    // Save the image locally in internal storage
    private void saveImage(Bitmap bitmap) {
        try {
            // Generate a unique file name for each image
            String fileName = "captured_note_" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(getFilesDir(), fileName);

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Save image path to the database
            String imagePath = file.getAbsolutePath();
            noteId = saveImagePathToDatabase(imagePath);

            Toast.makeText(this, "Image captured and saved successfully!", Toast.LENGTH_SHORT).show();

            // After saving, pass the noteId to CollectionActivity
            openCollectionActivity(noteId);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ReportActivity", "Error saving image", e);
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    // Save the image path to the SQLite database and return the generated noteId
    private int saveImagePathToDatabase(String imagePath) {
        // Insert the image path into the database and get the noteId
        return databaseHelper.insertImagePath(imagePath);
    }

    // Open the CollectionActivity and pass the noteId
    private void openCollectionActivity(int noteId) {
        Intent intent = new Intent(ReportActivity.this, CollectionActivity.class);
        intent.putExtra("noteId", noteId); // Pass the noteId to CollectionActivity
        startActivity(intent);
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
