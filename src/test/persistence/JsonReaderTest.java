package persistence;

import model.Journal;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderFileNotFound() {
        JsonReader reader = new JsonReader("./data/lolololol.json");
        try {
            Journal journal = reader.read();
            fail("Should have thrown IOException");
        } catch (IOException e) {
            // Test passed
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            Journal journal = reader.read();

            for (int month = 1; month < 12; month++) {
                for (int day = 1; day < journal.numberOfDaysInMonth(month); day++) {
                    assertEquals(0, journal.getNotesOnPage(day, month).size());
                    assertEquals("", journal.getMoodOnPage(day, month));
                }
            }
        } catch (IOException e) {
            fail("Shouldn't have thrown IOException");
        }
    }

    @Test
    void testReaderOnePageJournal() {
        JsonReader reader = new JsonReader("./data/testReaderOnePageJournal.json");
        try {
            Journal journal = reader.read();

            assertEquals(2, journal.getNotesOnPage(1, 1).size());
            assertEquals("Bad", journal.getMoodOnPage(1,1));
            assertEquals("Good morning", journal.getSpecificNoteOnPage(1,1,0).getContent());
            assertEquals("Good night", journal.getSpecificNoteOnPage(1,1,1).getContent());
        } catch (IOException e) {
            fail("Shouldn't have thrown IOException");
        }
    }

    @Test
    void testReaderMultiplePages() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderMultiplePagesJournal.json");
            Journal journal = reader.read();

            assertEquals(1, journal.getNotesOnPage(1, 1).size());
            assertEquals("meh", journal.getMoodOnPage(1,1));
            assertEquals("january", journal.getSpecificNoteOnPage(1,1,0).getContent());

            assertEquals(1, journal.getNotesOnPage(2, 2).size());
            assertEquals("", journal.getMoodOnPage(2,2));
            assertEquals("february", journal.getSpecificNoteOnPage(2,2,0).getContent());

            assertEquals(0, journal.getNotesOnPage(3, 3).size());
            assertEquals("good", journal.getMoodOnPage(3,3));
        } catch (IOException e) {
            fail("Shouldn't have thrown IOException");
        }
    }
}
