package ru.chistov.notes.repository;

public class NoteData {
    private String title;
    private String description;
    private  int picture;
    private String creationDate;
    private boolean completed;

    public NoteData(String title, String description, int picture, boolean completed, String creationDate) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.completed = completed;
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public boolean isCompleted() {
        return completed;
    }
}
