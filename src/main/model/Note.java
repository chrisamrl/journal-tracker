package model;


// Represents a single note having a content
public class Note {
    private String content;

    // EFFECTS: constructs a note with the given content
    public Note(String content) {
        this.content = content;
    }

    // EFFECTS: returns the content of this note
    public String getContent() {
        return content;
    }

    // MODIFIES: this
    // EFFECTS: sets the content of this note to the given string
    public void setContent(String newContent) {
        this.content = newContent;
    }
}
