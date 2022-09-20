package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;


// Represents a Journal having yearlyPages (a collection of pages in a year)
public class Journal implements Writable {
    private ArrayList<MonthlyPages> yearlyPages;

    // EFFECTS: constructs a journal with empty pages
    public Journal() {
        yearlyPages = new ArrayList<MonthlyPages>();

        // Create 12 monthly pages (i.e. collection of page for each month)
        for (int monthNumber = 1; monthNumber <= 12; monthNumber++) {
            MonthlyPages monthlyPages = new MonthlyPages(monthNumber, numberOfDaysInMonth(monthNumber));
            yearlyPages.add(monthlyPages);
        }
    }

    // REQUIRES: 1 <= monthNumber <= 12
    // EFFECTS: returns the number of days on the given month
    //          (this is a helper function)
    public int numberOfDaysInMonth(int monthNumber) {
        if (monthNumber == 2) {
            return 28;
        } else if (monthNumber == 4 || monthNumber == 6 || monthNumber == 9 || monthNumber == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    // REQUIRES: 1 <= month <= 12,  1 <= day <= numberOfDaysOnMonth(month)
    // EFFECTS: returns the Page corresponding to the given day and month
    //          (this is a helper function)
    private Page getPage(int day, int month) {
        return yearlyPages.get(month - 1).getPage(day);
    }

    // REQUIRES: 1 <= month <= 12,  1 <= day <= numberOfDaysOnMonth(month)
    // EFFECTS: returns the list of notes corresponding to the given day and month
    public ArrayList<Note> getNotesOnPage(int day, int month) {
        return getPage(day, month).getNotes();
    }

    // REQUIRES: 1 <= month <= 12,  1 <= day <= numberOfDaysOnMonth(month)
    // EFFECTS: returns mood on the given day and month
    public String getMoodOnPage(int day, int month) {
        return getPage(day, month).getMood();
    }

    // REQUIRES: 1 <= month <= 12,  1 <= day <= numberOfDaysOnMonth(month)
    // MODIFIES: this
    // EFFECTS: adds the given note on the page corresponding to the given day and month
    public void addNoteOnPage(int day, int month, Note note) {
        EventLog.getInstance().logEvent(new Event("Added a note to page on " + day + "-" + month
                + "-2022"));
        getPage(day, month).addNote(note);
    }

    // REQUIRES: 1 <= month <= 12,  1 <= day <= numberOfDaysOnMonth(month)
    // MODIFIES: this
    // EFFECTS: sets the mood on the page corresponding to the given day and month
    public void setMoodOnPage(int day, int month, String mood) {
        getPage(day, month).setMood(mood);
    }

    // REQUIRES: 1 <= month <= 12,  1 <= day <= numberOfDaysOnMonth(month)
    // MODIFIES: this
    // EFFECTS: deletes the note at given index on page corresponding to (day, month)
    //          returns true if note is deleted, else returns false if note is not found
    public boolean deleteNoteOnPage(int day, int month, int index) {
        try {
            getNotesOnPage(day, month).remove(index);
            EventLog.getInstance().logEvent(new Event("Removed note on page " + day + "-" + month
                    + "-2022" + " with index " + index));
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    // REQUIRES: 1 <= month <= 12,  1 <= day <= numberOfDaysOnMonth(month)
    // EFFECTS: returns the note at given index on the page corresponding to the given day, month
    public Note getSpecificNoteOnPage(int day, int month, int index) {
        return getPage(day, month).getNotes().get(index);
    }

    // EFFECTS: returns yearlyPages
    public ArrayList<MonthlyPages> getYearlyPages() {
        return yearlyPages;
    }

    // EFFECTS: returns this journal as an equivalent JSON representation
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray pagesArray = new JSONArray();
        for (MonthlyPages month : yearlyPages) {
            ArrayList<Page> pages = month.getPages();
            for (Page page : pages) {
                if (!page.getNotes().isEmpty() || !page.getMood().equals("")) {
                    pagesArray.put(page.toJson());
                }
            }
        }
        json.put("pages", pagesArray);
        return json;
    }
}
