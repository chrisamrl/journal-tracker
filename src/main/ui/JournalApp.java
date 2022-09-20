// The structure of this code is based on the 210's Teller App and JsonSerializationDemo App
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


package ui;

import model.Event;
import model.EventLog;
import model.Note;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


// Yearly journal Application
public class JournalApp {
    private static final String JSON_STORAGE = "./data/journal.json";
    Journal journal;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the journal application
    public JournalApp() {
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runJournal() {
        boolean keepRunning = true;
        String command;

        init();

        while (keepRunning) {
            displayMenu();
            System.out.print("\nType in your option here: ");

            command = input.nextLine();
            createHorizontalRule();

            if (command.equals("q")) {
                keepRunning = false;
                printLog(EventLog.getInstance());
            } else {
                processCommand(command);
            }
        }
        System.out.println();
    }


    private void printLog(EventLog eventLog) {
        for (Event event : eventLog) {
            System.out.println(event + "\n");

        }
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            onCommandAddNote();
        } else if (command.equals("m")) {
            onCommandSetMood();
        } else if (command.equals("d")) {
            onCommandViewNotes();
        } else if (command.equals("v")) {
            onCommandViewSpecificNote();
        } else if (command.equals("r")) {
            onCommandRemoveNote();
        } else if (command.equals("s")) {
            saveJournal();
        } else if (command.equals("l")) {
            loadJournal();
        } else {
            System.out.println("Invalid Input, please refer to the menu");
        }
    }

    // MODIFIES: this
    // initializes: initializes the journal
    private void init() {
        journal = new Journal();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);
    }

    // EFFECTS: displays functionality options to user
    private void displayMenu() {
        System.out.println("Please select one of the following options");
        System.out.println("\t a: add note to your journal");
        System.out.println("\t m: set mood on a specific day");
        System.out.println("\t d: view all notes on a day");
        System.out.println("\t v: view a specific note");
        System.out.println("\t r: delete a specific note");
        System.out.println("\t s: save journal data to file");
        System.out.println("\t l: load journal data from file");
        System.out.println("\t q: QUIT from the application");
    }

    // MODIFIES: this
    // EFFECTS: add a note to journal on date by user
    private void onCommandAddNote() {
        System.out.println("Adding a note to your journal...");
        System.out.println("\nWhere in your journal would you want to add your new note?");

        int[] date = askForDate();
        int day = date[0];
        int month = date[1];
        System.out.print("\tWhat's your note content: ");

        String content = input.nextLine();
        Note newNote = new Note(content);
        journal.addNoteOnPage(day, month, newNote);

        System.out.println("\nNote added!");
        createHorizontalRule();
    }

    // MODIFIES: this
    // EFFECTS: sets mood on date given by user
    private void onCommandSetMood() {
        System.out.println("Setting your mood on your journal...");
        System.out.println("\nPlease enter a date...");
        int[] date = askForDate();
        int day = date[0];
        int month = date[1];

        System.out.print("\tHow are you feeling?: ");
        String mood = input.nextLine();

        journal.setMoodOnPage(day, month, mood);
        System.out.println("\nMood set!");
        createHorizontalRule();
    }

    // EFFECTS: displays all notes on date given by user
    private void onCommandViewNotes() {
        System.out.println("Viewing your notes, please enter a date...");
        int[] date = askForDate();
        int day = date[0];
        int month = date[1];

        ArrayList<Note> notes = journal.getNotesOnPage(day, month);
        System.out.println("\nThese are your notes on " + day + "-" + month + "-" + "2022:\n");

        if (!journal.getMoodOnPage(day, month).isEmpty()) {
            System.out.println("You felt " +  journal.getMoodOnPage(day, month) + " on this day\n");
        }

        System.out.printf("%-10s %80s%n%n", "Index", "Content");
        displayFormattedNotes(notes);

        System.out.println("\n");
        createHorizontalRule();
    }

    // EFFECTS: formats a list of notes and prints them
    //          (This is a helper function for onCommandViewNotes()
    private void displayFormattedNotes(ArrayList<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            String content = notes.get(i).getContent();

            // Break line on every 60 characters
            int numberOfLines = content.length() / 60;

            for (int j = 0; j <= numberOfLines; j++) {
                int startIndex = j * 60;
                int endIndex = Math.min(60 * (j + 1), content.length());

                try {
                    if (startIndex == 0) {
                        System.out.printf("%-10d %80s%n", i, content.substring(startIndex, endIndex));
                    } else {
                        System.out.printf("%91s%n", content.substring(startIndex, endIndex));
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }
            }
        }
    }

    // EFFECTS: displays note corresponding to the index and date given by user
    private void onCommandViewSpecificNote() {
        System.out.println("Viewing a note...");
        int[] date = askForDate();
        int day = date[0];
        int month = date[1];

        System.out.print("\tEnter the note index (You can see this when viewing all notes on a day): ");
        int index = Integer.parseInt(input.nextLine());

        System.out.println("\nYou wrote this...\n");
        System.out.println(journal.getSpecificNoteOnPage(day, month, index).getContent());
        System.out.println("\n");
        createHorizontalRule();
    }

    // MODIFIES: this
    // EFFECTS: removes note corresponding to the index and date given by user
    private void onCommandRemoveNote() {
        System.out.println("Deleting a note...");
        int[] date = askForDate();
        int day = date[0];
        int month = date[1];

        System.out.print("\tEnter the note index (You can see this when viewing all notes on a day): ");
        int index = Integer.parseInt(input.nextLine());

        if (!journal.deleteNoteOnPage(day, month, index)) {
            System.out.println("\nSorry, there is no Note associated with the given input");
        } else {
            System.out.println("\nNote deleted!");
        }
        createHorizontalRule();
    }

    // EFFECTS: saves the journal to file at JSON_STORAGE
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Journal saved to " + JSON_STORAGE);
            createHorizontalRule();

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write journal data to file: " + JSON_STORAGE);
            createHorizontalRule();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the journal stored at file JSON_STORAGE
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded journal from: " + JSON_STORAGE);
            createHorizontalRule();
        } catch (IOException e) {
            System.out.println("Unable to read journal data from file: " + JSON_STORAGE);
            createHorizontalRule();
        }
    }

    // EFFECTS: displays a horizontal rule and a newline
    private void createHorizontalRule() {
        System.out.println("************************************************\n");
    }

    // EFFECTS: asks user for date, and then returns it as an array of integers with 2 elements
    //          (This is a helper function)
    private int[] askForDate() {
        System.out.print("\tEnter day number: ");
        int day = Integer.parseInt(input.nextLine());

        System.out.print("\tEnter month number: ");
        int month = Integer.parseInt(input.nextLine());

        return new int[] {day, month};
    }
}
