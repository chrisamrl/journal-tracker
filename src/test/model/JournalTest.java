package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JournalTest {
    Journal testJournal;
    Note testNote1;
    Note testNote2;
    Note testNote3;


    @BeforeEach
    void runBefore() {
        testJournal = new Journal();
        testNote1 = new Note("Journal knows");
        testNote2 = new Note("It ran away");
        testNote3 = new Note("I went to school");
    }


    @Test
    void testConstructor() {
        assertEquals(12, testJournal.getYearlyPages().size());

        int count = 0;
        for (MonthlyPages monthlyPages: testJournal.getYearlyPages()) {
            count += monthlyPages.getPages().size();
        }
        assertEquals(365, count);
    }

    @Test
    void testAddNoteOnPageOnce() {
        // Also indirectly test GetNotesOnPage
        testJournal.addNoteOnPage(1, 3, testNote1);

        assertEquals(1, testJournal.getNotesOnPage(1, 3).size());

        assertTrue(testJournal.getNotesOnPage(1, 3).contains(testNote1));
    }

    @Test
    void testAddNoteOnPageMultipleTimes() {
        // Also indirectly test getNotesOnPage, getSpecificNoteOnPage
        testJournal.addNoteOnPage(31, 12, testNote1);
        testJournal.addNoteOnPage(31, 12, testNote2);
        testJournal.addNoteOnPage(31, 12, testNote3);


        assertEquals(3, testJournal.getNotesOnPage(31, 12).size());
        assertEquals(testNote1, testJournal.getSpecificNoteOnPage(31, 12, 0));
        assertEquals(testNote2, testJournal.getSpecificNoteOnPage(31, 12, 1));
        assertEquals(testNote3, testJournal.getSpecificNoteOnPage(31, 12, 2));
    }


    @Test
    void testSetMoodOnPage() {
        testJournal.setMoodOnPage(28, 2, "HAPPY");
        assertEquals("HAPPY", testJournal.getMoodOnPage(28, 2));
    }


    @Test
    void testDeleteNoteOnPage() {
        testJournal.addNoteOnPage(1, 1, testNote1);
        testJournal.addNoteOnPage(1, 1, testNote2);
        testJournal.addNoteOnPage(1, 1, testNote3);

        assertEquals(3, testJournal.getNotesOnPage(1, 1).size());


        assertFalse(testJournal.deleteNoteOnPage(1,1,5));
        assertTrue(testJournal.deleteNoteOnPage(1,1,0));
        assertTrue(testJournal.deleteNoteOnPage(1,1,0));
        assertTrue(testJournal.deleteNoteOnPage(1,1,0));
        assertFalse(testJournal.deleteNoteOnPage(1,1,0));

        assertEquals(0, testJournal.getNotesOnPage(1, 1).size());

    }
}
