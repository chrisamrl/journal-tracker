package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;


// Represents a Page on a journal having a day, month, a list of notes, and a mood
public class Page implements Writable {
    private int day;
    private int month;
    private ArrayList<Note> notes;
    private String mood;

    // REQUIRES: 1 <= month <= 12, 1 <= day <= number of days in the given month
    // EFFECTS: constructs a new page with given day and month, empty notes, and no mood
    protected Page(int day, int month) {
        this.day = day;
        this.month = month;
        notes = new ArrayList<Note>();
        mood = "";
    }

    // MODIFIES: this
    // EFFECTS: add a new note on this page
    protected void addNote(Note note) {
        notes.add(note);
    }

    // MODIFIES: this
    // EFFECTS: sets the mood on this page
    protected void setMood(String mood) {
        this.mood = mood;
    }

    // EFFECTS: returns the list of notes on this page
    protected ArrayList<Note> getNotes() {
        return notes;
    }

    // EFFECTS: returns the day corresponding to this page
    protected int getDay() {
        return day;
    }

    // EFFECTS: returns the month corresponding to this page
    protected int getMonth() {
        return month;
    }

    // EFFECTS: returns the mood corresponding to this page
    protected String getMood() {
        return mood;
    }

    // EFFECTS: returns this page as an equivalent JSON representation
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("day", day);
        json.put("month", month);
        json.put("mood", mood);

        JSONArray notesContents = new JSONArray();

        for (Note note : notes) {
            notesContents.put(note.getContent());
        }

        json.put("notes", notesContents);

        return json;
    }
}
