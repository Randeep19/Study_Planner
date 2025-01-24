package com.example.latte.model;

public class Note {
    private int id; // Unique ID for the note (e.g., in a local database)
    private String imageUrl;

    // Empty constructor needed for Firebase or deserialization
    public Note() {
    }

    // Constructor for easier object creation
    public Note(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Setter for ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter for Image URL
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter for Image URL
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Override toString() for debugging or logging
    @Override
    public String toString() {
        return "Note{id=" + id + ", imageUrl='" + imageUrl + "'}";
    }
}
