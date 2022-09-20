package persistence;

import model.Journal;
import model.Note;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterFileNotFound() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("Should throw IOException");
        } catch (IOException e) {
            // test passed;
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            Journal journal = new Journal();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            journal = reader.read();

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
    void testWriterOnePageJournal() {
        try {
            Journal journal = new Journal();
            journal.addNoteOnPage(1,1, new Note("haha"));
            journal.addNoteOnPage(1,1, new Note("lol"));

            journal.setMoodOnPage(1,1,"bad");

            JsonWriter writer = new JsonWriter("./data/testWriterOnePageJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOnePageJournal.json");
            journal = reader.read();

            assertEquals(2, journal.getNotesOnPage(1, 1).size());
            assertEquals("bad", journal.getMoodOnPage(1,1));
            assertEquals("haha", journal.getSpecificNoteOnPage(1,1,0).getContent());
            assertEquals("lol", journal.getSpecificNoteOnPage(1,1,1).getContent());
        } catch (IOException e) {
            fail("Shouldn't have thrown IOException");
        }
    }

    @Test
    void testWriterMultiplePages() {
        try {
            Journal journal = new Journal();

            journal.addNoteOnPage(1,1, new Note("january"));
            journal.setMoodOnPage(1,1,"bad");

            journal.addNoteOnPage(2,2, new Note("february"));

            journal.setMoodOnPage(3,3,"good");


            JsonWriter writer = new JsonWriter("./data/testWriterMultiplePagesJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterMultiplePagesJournal.json");
            journal = reader.read();

            assertEquals(1, journal.getNotesOnPage(1, 1).size());
            assertEquals("bad", journal.getMoodOnPage(1,1));
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
