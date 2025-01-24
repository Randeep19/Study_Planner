package com.example.latte.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 2; // Increment version if schema changed

    public NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating the table to store notes with image paths
        String CREATE_TABLE = "CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, image_path TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Dropping the old table if it exists and creating a new one
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);  // Recreate the table with the updated schema
    }

    // Method to insert an image path into the database and return the generated noteId
    public int insertImagePath(String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image_path", imagePath);  // Storing the image path

        // Inserting the image path and returning the generated noteId
        long id = db.insert("notes", null, values);
        db.close();  // Close the database connection after use
        return (int) id; // Return the inserted row's ID (noteId)
    }

    // Method to retrieve the image path based on the note ID
    @SuppressLint("Range")
    public String getImagePath(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String imagePath = null;

        try {
            cursor = db.query("notes", new String[]{"image_path"}, "id=?", new String[]{String.valueOf(noteId)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                imagePath = cursor.getString(cursor.getColumnIndex("image_path"));
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
        } finally {
            if (cursor != null) {
                cursor.close(); // Always close the cursor to prevent memory leaks
            }
        }

        return imagePath; // Return the image path or null if not found
    }
}
