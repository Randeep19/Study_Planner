package com.example.latte.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.latte.R;
import com.example.latte.database.NotesDatabaseHelper;

import java.io.File;

public class CollectionActivity extends AppCompatActivity {

    private ImageView imageViewCollection;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        imageViewCollection = findViewById(R.id.imageViewCollection);

        // Retrieve the noteId passed from ReportActivity
        int noteId = getIntent().getIntExtra("noteId", -1); // Get the note ID passed through Intent

        if (noteId != -1) {
            // Retrieve the image path from the database using the noteId
            NotesDatabaseHelper databaseHelper = new NotesDatabaseHelper(this);
            String imagePath = databaseHelper.getImagePath(noteId);

            if (imagePath != null) {
                // Load the image from the path and display it in ImageView
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    imageViewCollection.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
                } else {
                    Toast.makeText(this, "Image file does not exist.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Image not found for this note", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid note ID", Toast.LENGTH_SHORT).show();
        }
    }
}
