// The structure of this code is based on 210's JsonSerializationDemo App
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Journal;
import model.Note;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads journal from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal from file and returns it;
    //          throws IOException if an error occurs while reading data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses JSON Object and returns it as an equivalent Journal object
    private Journal parseJournal(JSONObject jsonObject) {
        Journal journal = new Journal();
        addPages(journal, jsonObject);
        return journal;
    }

    // MODIFIES: journal
    // EFFECTS: adds equivalent pages from JSON Object to journal
    private void addPages(Journal journal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pages");
        for (Object json : jsonArray) {
            JSONObject nextPage = (JSONObject) json;
            addPage(journal, nextPage);
        }
    }

    // MODIFIES: journal
    // EFFECTS: add equivalent page from JSON Object to journal
    private void addPage(Journal journal, JSONObject jsonObject) {
        int day = jsonObject.getInt("day");
        int month = jsonObject.getInt("month");
        String mood = jsonObject.getString("mood");
        JSONArray notes = jsonObject.getJSONArray("notes");

        for (Object note : notes) {
            String nextNote = (String) note;
            journal.addNoteOnPage(day, month, new Note(nextNote));
        }

        journal.setMoodOnPage(day, month, mood);
    }
}
